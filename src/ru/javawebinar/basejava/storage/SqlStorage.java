package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ObjectMapper;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    private final ObjectMapper<Resume> mapper = rs -> {
        Map<String, Resume> resumes = new LinkedHashMap<>();
        while (rs.next()) {
            String uuid = rs.getString("uuid");
            if (!resumes.containsKey(uuid)) {
                resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
            }
            String value = rs.getString("value");
            String type = rs.getString("type");
            if (value != null && type != null) {
                resumes.get(uuid).addContact(ContactType.valueOf(type), value);
            }
        }
        return new ArrayList<>(resumes.values());
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

    public void saveContacts(Resume r, Connection conn) throws SQLException {
        sqlHelper.executeBatch(conn, "INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)",
                r.getContacts().entrySet().stream().
                        map(e -> List.of((Object) r.getUuid(), e.getKey().name(), e.getValue())).
                        collect(Collectors.toList()));
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                sqlHelper.<Void>execute(conn, "UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(r.getUuid());
                    }
                    return null;
                }, r.getFullName(), r.getUuid());
                sqlHelper.execute(conn, "DELETE FROM contact c WHERE c.resume_uuid = ?", ps -> ps.executeUpdate(), r.getUuid());
                saveContacts(r, conn);
                return null;
            }
        );
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                sqlHelper.execute(conn, "INSERT INTO resume (full_name, uuid) VALUES (?, ?)",
                        ps -> ps.execute(), r.getFullName(), r.getUuid());
                saveContacts(r, conn);
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