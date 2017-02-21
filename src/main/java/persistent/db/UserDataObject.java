package persistent.db;

import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataObject extends AbstractDataObject<User> {

    public UserDataObject(Connection con) {
        super(con);
    }

    @Override
    protected String getSelectAllSQL() {
        return "SELECT " +
                "id, bitrix_id, firstname, middlename, lastname, email, phone, birthdate " +
                "FROM \"user\"";
    }

    @Override
    protected String getSelectOneSQL() {
        return "SELECT " +
                "id, bitrix_id, firstname, middlename, lastname, email, phone, birthdate " +
                "FROM \"user\"" +
                "WHERE id = ?";
    }

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO \"user\" " +
                "(id, bitrix_id, firstname, middlename, lastname, email, phone, birthdate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE \"user\" SET " +
                "bitrix_id = ?, firstname = ?, middlename = ?, lastname = ?, " +
                "email = ?, phone = ?, birthdate = ? " +
                "WHERE id = ?";
    }

    @Override
    protected String getDeleteSQL() {
        return "DELETE FROM \"user\" WHERE id = ?";
    }

    @Override
    protected String getDeleteAllSQL() {
        return "DELETE FROM \"user\"";
    }

    @Override
    protected void prepareInsert(User obj, PreparedStatement statement) throws SQLException {
        statement.setLong(1, obj.getId());
        statement.setLong(2, obj.getBitrixId());
        statement.setString(3, obj.getFirstName());
        statement.setString(4, obj.getMiddleName());
        statement.setString(5, obj.getLastName());
        statement.setString(6, obj.getEmail());
        statement.setString(7, obj.getPhone());
        statement.setDate(8, obj.getBirthDate());
    }

    @Override
    protected void prepareUpdate(User obj, PreparedStatement statement) throws SQLException {
        statement.setLong(1, obj.getBitrixId());
        statement.setString(2, obj.getFirstName());
        statement.setString(3, obj.getMiddleName());
        statement.setString(4, obj.getLastName());
        statement.setString(5, obj.getEmail());
        statement.setString(6, obj.getPhone());
        statement.setDate(7, obj.getBirthDate());
        statement.setLong(8, obj.getId());
    }

    @Override
    protected User createObj() {
        return new User();
    }

    @Override
    protected User readObj(User obj, ResultSet source) throws SQLException {
        obj.setId(source.getLong(1));
        obj.setBitrixId(source.getLong(2));
        obj.setFirstName(source.getString(3));
        obj.setMiddleName(source.getString(4));
        obj.setLastName(source.getString(5));
        obj.setEmail(source.getString(6));
        obj.setPhone(source.getString(7));
        obj.setBirthDate(source.getDate(8));

        return obj;
    }
}
