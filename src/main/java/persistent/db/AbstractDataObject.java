package persistent.db;

import java.sql.*;
import java.util.Collection;

public abstract class AbstractDataObject<T> {
    private Connection con;

    public AbstractDataObject(Connection con) {
        this.con = con;
    }

    protected abstract String getSelectAllSQL();

    protected abstract String getSelectOneSQL();

    protected abstract String getInsertSQL();

    protected abstract String getUpdateSQL();

    protected abstract String getDeleteSQL();

    protected abstract String getDeleteAllSQL();

    protected abstract void prepareInsert(T obj, PreparedStatement statement) throws SQLException;

    protected abstract void prepareUpdate(T obj, PreparedStatement statement) throws SQLException;

    protected abstract T createObj();

    protected abstract T readObj(T obj, ResultSet source) throws SQLException;

    public T getByID(long id) throws SQLException, PersistentException {
        try (PreparedStatement statement = con.prepareStatement(getSelectOneSQL())) {
            statement.setLong(1, id);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return readObj(createObj(), result);
                } else {
                    throw new IDNotFoundException();
                }
            }
        }
    }

    public void getAll(Collection<T> dest) throws SQLException {
        try (Statement statement = con.createStatement()) {
            try (ResultSet result = statement.executeQuery(getSelectAllSQL())) {
                while (result.next()) {
                    dest.add(readObj(createObj(), result));
                }
            }
        }
    }

    public boolean save(T obj) throws SQLException {
        try (PreparedStatement statement = con.prepareStatement(getUpdateSQL())) {
            prepareUpdate(obj, statement);
            return statement.execute();
        }
    }

    public void save(Collection<? extends T> objs) throws SQLException {
        try (PreparedStatement statement = con.prepareStatement(getUpdateSQL())) {
            for (T obj : objs) {
                prepareUpdate(obj, statement);
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    public boolean insert(T obj) throws SQLException {
        try (PreparedStatement statement = con.prepareStatement(getInsertSQL())) {
            prepareInsert(obj, statement);
            return statement.execute();
        }
    }

    public void insert(Collection<? extends T> objs) throws SQLException {
        try (PreparedStatement statement = con.prepareStatement(getInsertSQL())) {
            for (T obj : objs) {
                prepareInsert(obj, statement);
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    public boolean deleteByID(long id) throws SQLException {
        try (PreparedStatement statement = con.prepareStatement(getDeleteSQL())) {
            statement.setLong(1, id);
            return statement.execute();
        }
    }

    public boolean deleteAll() throws SQLException {
        try (Statement statement = con.createStatement()) {
            return statement.execute(getDeleteAllSQL());
        }
    }
}
