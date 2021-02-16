package ru.javawebinar.basejava.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ObjectMapper<T> {
    List<T> get(ResultSet rs) throws SQLException;
}
