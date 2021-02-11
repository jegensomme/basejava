package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public final SqlHelper.ObjectMapper<Resume> resumeMapper = rs -> {
        List<Resume> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
        }
        return list;
    };

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        try {
            sqlHelper.execute("DELETE FROM resume");
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Resume get(String uuid) {
        try {
            List<Resume> result = sqlHelper.executeQuery("SELECT * FROM resume r WHERE r.uuid=?", resumeMapper, uuid);
            if (result.size() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return result.get(0);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void update(Resume r) {
        try {
            if (sqlHelper.executeUpdate("UPDATE resume r SET full_name=? WHERE r.uuid=?", r.getFullName(), r.getUuid()) == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void save(Resume r) {
        try {
            sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?,?)", r.getUuid(), r.getFullName());
        } catch (SQLException e) {
            throw new ExistStorageException(r.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
        try {
            if (sqlHelper.executeUpdate("DELETE FROM resume r WHERE r.uuid=?", uuid) == 0) {
                throw new NotExistStorageException(uuid);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        try {
            return sqlHelper.executeQuery("SELECT * FROM resume r ORDER BY r.uuid, r.full_name", resumeMapper);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public int size() {
        try {
            return sqlHelper.executeQuery("SELECT count(*) FROM resume", rs -> {
                rs.next();
                return List.of(rs.getInt(1));
            }).get(0);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
