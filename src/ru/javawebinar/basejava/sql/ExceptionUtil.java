package ru.javawebinar.basejava.sql;

import org.postgresql.util.PSQLException;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {
            if ("23505".equals(e.getSQLState())) {
                return new ExistStorageException(null);
            }
        }
        return new StorageException(e);
    }
}
