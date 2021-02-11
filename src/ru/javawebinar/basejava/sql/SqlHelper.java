package ru.javawebinar.basejava.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    private <R> R processConnection(String sql, StatementExecutor<R> statementProcessor, Object... params) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            setParams(ps, params);
            return statementProcessor.execute(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public boolean execute(String sql, Object... params) {
        return processConnection(sql, PreparedStatement::execute, params);
    }

    public int executeUpdate(String sql, Object... params) {
        return processConnection(sql, PreparedStatement::executeUpdate, params);
    }

    public <T> List<T> executeQuery(String sql, ObjectMapper<T> objectMapper, Object... params) {
        return processConnection(sql, ps -> objectMapper.get(ps.executeQuery()), params);
    }

    private void setParams(PreparedStatement ps, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
    }

    @FunctionalInterface
    public interface ObjectMapper<T> {
        List<T> get(ResultSet resultSet) throws SQLException;
    }

    @FunctionalInterface
    private interface StatementExecutor<R> {
        R execute(PreparedStatement ps) throws SQLException;
    }
}
