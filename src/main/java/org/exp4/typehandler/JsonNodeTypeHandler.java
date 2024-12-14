package org.exp4.typehandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JsonNodeTypeHandler extends BaseTypeHandler<JsonNode> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JsonNode parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setString(i, parameter.toString());
        } catch (Exception e) {
            throw new SQLException("Error setting JsonNode parameter", e);
        }
    }

    @Override
    public JsonNode getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            String jsonStr = rs.getString(columnName);
            return jsonStr == null ? null : objectMapper.readTree(jsonStr);
        } catch (Exception e) {
            throw new SQLException("Error getting JsonNode result", e);
        }
    }

    @Override
    public JsonNode getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            String jsonStr = rs.getString(columnIndex);
            return jsonStr == null ? null : objectMapper.readTree(jsonStr);
        } catch (Exception e) {
            throw new SQLException("Error getting JsonNode result", e);
        }
    }

    @Override
    public JsonNode getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            String jsonStr = cs.getString(columnIndex);
            return jsonStr == null ? null : objectMapper.readTree(jsonStr);
        } catch (Exception e) {
            throw new SQLException("Error getting JsonNode result", e);
        }
    }
}
