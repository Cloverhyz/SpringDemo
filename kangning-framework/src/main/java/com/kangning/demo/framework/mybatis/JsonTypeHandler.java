package com.kangning.demo.framework.mybatis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kangning.demo.framework.json.JsonUtil;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * @author 加康宁 Date: 2019-07-01 Time: 18:57
 * @version $Id$
 */
public class JsonTypeHandler<T> implements TypeHandler<T> {

    private Type type;

    public JsonTypeHandler() {
        type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    @Override
    public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, toJson(parameter));
    }

    @Override
    public T getResult(ResultSet rs, String columnName) throws SQLException {
        return fromJson(rs.getString(columnName));
    }

    @Override
    public T getResult(ResultSet rs, int columnIndex) throws SQLException {
        return fromJson(rs.getString(columnIndex));
    }

    @Override
    public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return fromJson(cs.getString(columnIndex));
    }

    private T fromJson(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return (T) JsonUtil.getObjectMapperInstance().readValue(json, new GenericTypeReference(type));
        } catch (IOException e) {
            throw new RuntimeException("JsonTypeHandler read json error! json="+json+",type="+type, e);
        }
    }


    private String toJson(Object object) {
        if (object == null) {
            return StringUtils.EMPTY;
        }
        try {
            return JsonUtil.getObjectMapperInstance().writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException("JsonTypeHandler write json error!", e);
        }
    }

    public static class GenericTypeReference extends TypeReference<Void> {
        private Type typeValue;

        public GenericTypeReference(Type type) {
            this.typeValue = type;
        }

        @Override
        public Type getType() {
            return typeValue;
        }
    }
}
