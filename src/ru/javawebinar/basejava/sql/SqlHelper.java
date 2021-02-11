package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.StorageException;

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

    private <R> R processConnection(String sql, StatementProcessor<R> statementProcessor, Object... params) throws SQLException {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            setParams(ps, params);
            return statementProcessor.process(ps);
        }
    }

    public int count() {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM resume")) {
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public boolean execute(String sql, Object... params) throws SQLException {
        return processConnection(sql, PreparedStatement::execute, params);
    }

    public int executeUpdate(String sql, Object... params) throws SQLException {
        return processConnection(sql, PreparedStatement::executeUpdate, params);
    }

    public <T> List<T> executeQuery(String sql, ObjectMapper<T> objectMapper, Object... params) throws SQLException {
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
    private interface StatementProcessor<R> {
        R process(PreparedStatement ps) throws SQLException;
    }
}
