package com.kangning.demo.framework.mybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;

/**
 * @author 加康宁 Date: 2019-04-23 Time: 19:24
 * @version $Id$
 */
@Intercepts({@Signature(
    type = StatementHandler.class,
    method = "prepare",
    args = {Connection.class,Integer.class}
)})
public class PagePlugin implements Interceptor {

    private Integer defaultPage = 1;

    private Integer defaultPageSize = 20;

    private Integer defaultCurrentSize = 0;

    private Integer defaultTotalCount = 0;

    private Integer defaultTotalPage = 0;

    private Boolean defaultUseFlag = true;

    private Boolean defaultCheckFlag = true;


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = getUnProxyObject(invocation);
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        if (!checkSelect(sql)) {
            return invocation.proceed();
        }
        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
        Object parameterObject = boundSql.getParameterObject();
        PageParam pageParam = getPageParams(parameterObject);
        if (pageParam == null) {
            return invocation.proceed();
        }

        Integer pageNum = pageParam.getPage() == null ? this.defaultPage : pageParam.getPage();
        Integer pageSize = pageParam.getPageSize() == null ? this.defaultPageSize : pageParam.getPageSize();
        Boolean useFlag = pageParam.getUseFlag() == null ? this.defaultUseFlag : pageParam.getUseFlag();
        Boolean checkFlag = pageParam.getCheckFlag() == null ? this.defaultCheckFlag : pageParam.getCheckFlag();
        if (!useFlag) {
            return invocation.proceed();
        }
        int tocalCount = getTotal(invocation, metaStatementHandler, boundSql);
        setTotalToPageParams(pageParam, tocalCount, pageSize);
        checkPage(checkFlag, pageNum, pageParam.getTotalPage());
        return changeSQL(invocation, metaStatementHandler, boundSql, pageNum, pageSize);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }


    private StatementHandler getUnProxyObject(Invocation invocation) {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);

        //分离代理对象链
        Object object = null;
        while (metaStatementHandler.hasGetter("h")) {
            object = metaStatementHandler.getValue("h");
        }
        if (object == null) {
            return statementHandler;
        }
        return (StatementHandler) object;
    }

    private boolean checkSelect(String sql) {
        String trimSql = sql.trim();
        return trimSql.toLowerCase().indexOf("select") == 0;
    }

    private PageParam getPageParams(Object parameterObject) {
        if (parameterObject == null) {
            return null;
        }
        if (parameterObject instanceof PageParam) {
            return (PageParam) parameterObject;
        }
        return null;
    }

    private int getTotal(Invocation invocation, MetaObject metaStatementHandler, BoundSql boundSql)
        throws SQLException {
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        Configuration configuration = mappedStatement.getConfiguration();
        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        String countSql = "select count(*) as total from (" + sql + ") $_paging";
        Connection connection = (Connection) invocation.getArgs()[0];
        PreparedStatement preparedStatement = null;
        int total = 0;
        try {
            preparedStatement = connection.prepareStatement(countSql);
            BoundSql countBoundSql = new BoundSql(configuration, countSql, boundSql.getParameterMappings(),
                                                  boundSql.getParameterObject());
            ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement,
                                                                            boundSql.getParameterObject(),
                                                                            countBoundSql);
            parameterHandler.setParameters(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                total = resultSet.getInt("total");
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return total;
    }

    private void checkPage(Boolean checkFlag, Integer pageNum, Integer pageTotal) throws Exception {
        if (checkFlag) {
            //检查page是否合法
            if (pageNum > pageTotal) {
                throw new Exception("查询失败，查询页码【" + pageNum + "】大于总页数【" + pageTotal + "】！！");
            }
        }
    }

    private void setTotalToPageParams(PageParam pageParams, int total, int pageSize) {
        pageParams.setTotalCount(total);
        int totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        pageParams.setTotalPage(totalPage);
    }

    private Object changeSQL(Invocation invocation, MetaObject metaStatementHandler, BoundSql boundSql, int page,
                             int pageSize) throws Exception {
        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        String newSql = "select * from (" + sql + ") $_paging_table limit ?,?;";
        //修改当前需要执行的SQL
        metaStatementHandler.setValue("delegate.boundSql.sql", newSql);
        PreparedStatement preparedStatement = (PreparedStatement) invocation.proceed();
        //计算SQL总参数个数
        int count = preparedStatement.getParameterMetaData().getParameterCount();
        preparedStatement.setInt(count - 1, (page - 1) * pageSize);
        preparedStatement.setInt(count, pageSize);
        return preparedStatement;
    }

}
