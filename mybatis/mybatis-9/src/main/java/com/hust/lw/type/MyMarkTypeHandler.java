package com.hust.lw.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class MyMarkTypeHandler extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter+"0");
    }

    /**
     * 把时间戳类型的字符串取出转换为Date
     */
    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return new String("1"+rs.getString(columnName));
    }

    /**
     * 把时间戳类型的字符串取出转换为Date
     */
    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return new String("1"+rs.getString(columnIndex));
    }

    /**
     * 把时间戳类型的字符串取出转换为Date
     */
    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return "1"+cs.getString(columnIndex);
    }
}
