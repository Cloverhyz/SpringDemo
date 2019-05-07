package com.kangning.demo.framework.mybatis;

import com.alibaba.druid.filter.stat.MergeStatFilter;
import com.alibaba.druid.filter.stat.StatFilterContext;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.PreparedStatementProxy;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementExecuteType;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.stat.JdbcDataSourceStat;
import com.alibaba.druid.stat.JdbcSqlStat;
import com.alibaba.druid.support.json.JSONWriter;
import com.alibaba.druid.support.profile.Profiler;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

/**
 * @author 加康宁 Date: 2019-04-23 Time: 14:52
 * @version $Id$
 */
public class DbStatFilter extends MergeStatFilter {

    private static Logger logger;

    private boolean mergeSql = false;

    private boolean isLogAllSql = false;

    private String applicationName;

    public DbStatFilter(String applicationName) {
        super.setMergeSql(true);
        this.applicationName = StringUtils.defaultIfBlank(applicationName, StringUtils.EMPTY).toUpperCase();
        this.mergeSql = true;
        logger = LoggerFactory.getLogger(this.applicationName + "_slow_sql_logger");
    }


    @Override
    protected void statementExecuteAfter(StatementProxy statement, String sql, boolean firstResult) {
        internalAfterStatementExecute(statement, firstResult);
    }

    @Override
    protected void statementExecuteBatchAfter(StatementProxy statement, int[] result) {
        internalAfterStatementExecute(statement, false, result);

    }

    @Override
    protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
        internalAfterStatementExecute(statement, true);
    }

    @Override
    protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
        internalAfterStatementExecute(statement, false, updateCount);
    }

    private final void internalAfterStatementExecute(StatementProxy statement, boolean firstResult,
                                                     int... updateCountArray) {
        final long nowNano = System.nanoTime();
        final long nanos = nowNano - statement.getLastExecuteStartNano();

        JdbcDataSourceStat dataSourceStat = statement.getConnectionProxy().getDirectDataSource().getDataSourceStat();
        dataSourceStat.getStatementStat().afterExecute(nanos);

        final JdbcSqlStat sqlStat = statement.getSqlStat();

        if (sqlStat != null) {
            sqlStat.incrementExecuteSuccessCount();

            sqlStat.decrementRunningCount();
            sqlStat.addExecuteTime(statement.getLastExecuteType(), firstResult, nanos);
            statement.setLastExecuteTimeNano(nanos);
            if ((!statement.isFirstResultSet()) && statement.getLastExecuteType() == StatementExecuteType.Execute) {
                try {
                    int updateCount = statement.getUpdateCount();
                    sqlStat.addUpdateCount(updateCount);
                } catch (SQLException e) {
                    logger.error("getUpdateCount error", e);
                }
            } else {
                for (int updateCount : updateCountArray) {
                    sqlStat.addUpdateCount(updateCount);
                    sqlStat.addFetchRowCount(0);
                    StatFilterContext.getInstance().addUpdateCount(updateCount);
                }
            }

            boolean isLoged = false;
            long millis = nanos / (1000 * 1000);
            if (millis >= slowSqlMillis) {
                String slowParameters = buildSlowParameters(statement);
                sqlStat.setLastSlowParameters(slowParameters);

                if (logSlowSql) {
                    isLoged = true;
                    String slowSql = statement.getLastExecuteSql();
                    logger.error("slow sql " + millis + " millis. \n" + slowSql + "\n"
                                    + slowParameters);
                    if (millis > 10000) {
                        //大于5秒的查询邮件通知开发
                        //TODO 增加邮件接口
                        logger.error("TOO_SLOW_SQL:{}ms",millis);
                    }
                }
            }

            if (!isLoged && isLogAllSql) {
                String slowParameters = buildSlowParameters(statement);
                sqlStat.setLastSlowParameters(slowParameters);
                logger.info("all sql " + millis + " millis. " + statement.getLastExecuteSql().replaceAll("[\n\t ]+", " ") + " =====params:" + slowParameters);
            }
        }

        String sql = statement.getLastExecuteSql();
        StatFilterContext.getInstance().executeAfter(sql, nanos, null);

        Profiler.release(nanos);
    }

    private String buildSlowParameters(StatementProxy statement) {
        JSONWriter out = new JSONWriter();

        out.writeArrayStart();
        for (int i = 0, parametersSize = statement.getParametersSize(); i < parametersSize; ++i) {
            JdbcParameter parameter = statement.getParameter(i);
            if (i != 0) {
                out.writeComma();
            }
            if (parameter == null) {
                continue;
            }

            Object value = parameter.getValue();
            if (value == null) {
                out.writeNull();
            } else if (value instanceof String) {
                String text = (String) value;
                if (text.length() > 100) {
                    out.writeString(text.substring(0, 97) + "...");
                } else {
                    out.writeString(text);
                }
            } else if (value instanceof Number) {
                out.writeObject(value);
            } else if (value instanceof java.util.Date) {
                out.writeObject(value);
            } else if (value instanceof Boolean) {
                out.writeObject(value);
            } else if (value instanceof InputStream) {
                out.writeString("<InputStream>");
            } else if (value instanceof NClob) {
                out.writeString("<NClob>");
            } else if (value instanceof Clob) {
                out.writeString("<Clob>");
            } else if (value instanceof Blob) {
                out.writeString("<Blob>");
            } else {
                out.writeString('<' + value.getClass().getName() + '>');
            }
        }
        out.writeArrayEnd();

        return out.toString();
    }

    @Override
    protected void statement_executeErrorAfter(StatementProxy statement, String sql, Throwable error) {
        super.statement_executeErrorAfter(statement, sql, error);
        try {
            if (statement instanceof PreparedStatementProxy) {
                PreparedStatementProxy preparedStatementProxy = (PreparedStatementProxy) statement;
                preparedStatementProxy.getId();
            }
            StringBuilder paramsBuf = new StringBuilder("[ ");
            for (int i = 0; i < statement.getParametersSize(); i++) {
                paramsBuf.append(Objects.toString(statement.getParameter(i).getValue()));
                if (i != statement.getParametersSize() - 1) {
                    paramsBuf.append(",");
                }
            }
            Map<Integer, JdbcParameter> parameters = statement.getParameters();
            if (!CollectionUtils.isEmpty(parameters)) {
                for (Map.Entry<Integer, JdbcParameter> entry : parameters.entrySet()) {

                    paramsBuf = paramsBuf.append(ObjectUtils.toString(entry.getValue().getValue())).append(",");
                }
            }
            paramsBuf = paramsBuf.append(" ]");
            logger.error("execute sql Error:" + sql + " ; params:" + paramsBuf, error);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean isMergeSql() {
        return mergeSql;
    }

    @Override
    public void setMergeSql(boolean mergeSql) {
        this.mergeSql = mergeSql;
    }

    public boolean isLogAllSql() {
        return isLogAllSql;
    }

    public void setLogAllSql(boolean logAllSql) {
        isLogAllSql = logAllSql;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
