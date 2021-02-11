package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
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
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        List<Resume> result = sqlHelper.executeQuery("SELECT * FROM resume r WHERE r.uuid=?", resumeMapper, uuid);
        if (result.size() == 0) {
            throw new NotExistStorageException(uuid);
        }
        return result.get(0);
    }

    @Override
    public void update(Resume r) {
        if (sqlHelper.executeUpdate("UPDATE resume r SET full_name=? WHERE r.uuid=?", r.getFullName(), r.getUuid()) == 0) {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    @Override
    public void save(Resume r) {
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?,?)", r.getUuid(), r.getFullName());
    }

    @Override
    public void delete(String uuid) {
        if (sqlHelper.executeUpdate("DELETE FROM resume r WHERE r.uuid=?", uuid) == 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.executeQuery("SELECT * FROM resume r ORDER BY r.uuid, r.full_name", resumeMapper);
    }

    @Override
    public int size() {
        return sqlHelper.executeQuery("SELECT count(*) FROM resume", rs -> {
            rs.next();
            return List.of(rs.getInt(1));
        }).get(0);
    }
}
