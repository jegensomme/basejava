package ru.javawebinar.basejava.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@FunctionalInterface
public interface ObjectMapper<T> {
    List<T> get(ResultSet resultSet) throws SQLException;
}
