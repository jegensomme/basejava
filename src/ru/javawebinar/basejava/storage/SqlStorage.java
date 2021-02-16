package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ObjectMapper;
import ru.javawebinar.basejava.sql.SqlExecutor;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    private final ObjectMapper<Resume> mapper = rs -> {
        List<Resume> result = new ArrayList<>();
        if (!rs.next()) {
            return result;
        }
        boolean hasNext;
        do {
            String uuid = rs.getString("uuid");
            Resume r = new Resume(uuid, rs.getString("full_name"));
            result.add(r);
            do {
                String value = rs.getString("value");
                String type = rs.getString("type");
                if (value != null && type != null) {
                    r.addContact(ContactType.valueOf(type), value);
                }
                hasNext = rs.next();
            } while (hasNext && rs.getString("uuid").equals(uuid));
        } while (hasNext);
        return result;
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
        return sqlHelper.execute("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    List<Resume> result = mapper.get(ps.executeQuery());
                    if (result.size() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    if (result.size() > 1) {
                        throw new StorageException("More than 1 result");
                    }
                    return result.get(0);
                });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                        ps.setString(1, r.getFullName());
                        ps.setString(2, r.getUuid());
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(r.getUuid());
                        }
                    }
                    sqlHelper.executeBatch(conn, "UPDATE contact SET value = ? WHERE resume_uuid = ? AND type = ?",
                            r.getContacts().entrySet().stream().
                                    map(e -> List.of((Object) e.getValue(), r.getUuid(), e.getKey().name())).
                                    collect(Collectors.toList()));
                    return null;
                }
        );
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (full_name, uuid) VALUES (?, ?)")) {
                        ps.setString(1, r.getFullName());
                        ps.setString(2, r.getUuid());
                        ps.execute();
                    }
                    sqlHelper.executeBatch(conn, "INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)",
                            r.getContacts().entrySet().stream().
                                    map(e -> List.of((Object) r.getUuid(), e.getKey().name(), e.getValue())).
                                    collect(Collectors.toList()));
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid=?")) {
                ps.setString(1, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid ORDER BY full_name,uuid", ps -> mapper.get(ps.executeQuery()));
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", st -> {
            ResultSet rs = st.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}