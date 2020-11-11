package com.hust.lw.mybatis.type;

import com.hust.lw.enums.Member;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyMemberTypeHandler implements TypeHandler<Member> {
    public void setParameter(PreparedStatement ps, int i, Member parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    public Member getResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return Member.getMember(code);
    }

    public Member getResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return Member.getMember(code);
    }

    public Member getResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return Member.getMember(code);
    }
}
