package com.hust.lw.mybatis.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class MyDateTypeHandler extends BaseTypeHandler<Date> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, String.valueOf(parameter.getTime()));
    }

    /**
     * 把时间戳类型的字符串取出转换为Date
     */
    @Override
    public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return new Date(rs.getLong(columnName));
    }

    /**
     * 把时间戳类型的字符串取出转换为Date
     */
    @Override
    public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return new Date(rs.getLong(columnIndex));
    }

    /**
     * 把时间戳类型的字符串取出转换为Date
     */
    @Override
    public Date getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getDate(columnIndex);
    }
}
