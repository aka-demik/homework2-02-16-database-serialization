package persistent.db;

import models.SuperUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SuperUserDataObject extends AbstractDataObject<SuperUser> {

    public SuperUserDataObject(Connection con) {
        super(con);
    }

    @Override
    protected String getSelectAllSQL() {
        return "SELECT " +
                "id, email, firstname, lastname, middlename " +
                "FROM superuser";
    }

    @Override
    protected String getSelectOneSQL() {
        return "SELECT " +
                "id, email, firstname, lastname, middlename " +
                "FROM superuser " +
                "WHERE id = ?";
    }

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO superuser " +
                "(id, email, firstname, lastname, middlename) " +
                "VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSQL() {
        return null;
    }

    @Override
    protected String getDeleteSQL() {
        return "DELETE FROM superuser WHERE id = ?";
    }

    @Override
    protected String getDeleteAllSQL() {
        return "DELETE FROM superuser";
    }

    @Override
    protected void prepareInsert(SuperUser obj, PreparedStatement statement) throws SQLException {
        statement.setLong(1, obj.getId());
        statement.setString(2, obj.getEmail());
        statement.setString(3, obj.getFirstName());
        statement.setString(4, obj.getMiddleName());
        statement.setString(5, obj.getLastName());
    }

    @Override
    protected void prepareUpdate(SuperUser obj, PreparedStatement statement) throws SQLException {
        statement.setString(1, obj.getEmail());
        statement.setString(2, obj.getFirstName());
        statement.setString(3, obj.getMiddleName());
        statement.setString(4, obj.getLastName());
        statement.setLong(5, obj.getId());
    }

    @Override
    protected SuperUser createObj() {
        return new SuperUser();
    }

    @Override
    protected SuperUser readObj(SuperUser obj, ResultSet source) throws SQLException {
        obj.setId(source.getLong(1));
        obj.setEmail(source.getString(2));
        obj.setFirstName(source.getString(3));
        obj.setMiddleName(source.getString(4));
        obj.setLastName(source.getString(5));

        return obj;
    }
}
