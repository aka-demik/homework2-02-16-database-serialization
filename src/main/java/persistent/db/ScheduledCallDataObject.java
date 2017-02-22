package persistent.db;

import models.ScheduledCall;

import java.sql.*;

public class ScheduledCallDataObject extends AbstractDataObject<ScheduledCall> {

    private CallDataObject callDataObject;

    public ScheduledCallDataObject(Connection con) {
        super(con);
        callDataObject = new CallDataObject(con);
    }

    @Override
    protected String getSelectAllSQL() {
        return "SELECT id, call_id, scheduled_at FROM scheduled_call";
    }

    @Override
    protected String getSelectOneSQL() {
        return "SELECT id, call_id, scheduled_at FROM scheduled_call WHERE id = ?";
    }

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO scheduled_call " +
                "(id, call_id, scheduled_at) " +
                "VALUES (?, ?, ?)";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE scheduled_call SET " +
                "call_id = ?, scheduled_at = ? " +
                "WHERE id = ?";
    }

    @Override
    protected String getDeleteSQL() {
        return "DELETE FROM scheduled_call WHERE id = ?";
    }

    @Override
    protected String getDeleteAllSQL() {
        return "DELETE FROM scheduled_call";
    }

    @Override
    protected void prepareInsert(ScheduledCall obj, PreparedStatement statement) throws SQLException {
        statement.setLong(1, obj.getId());
        if (obj.getCall() != null)
            statement.setLong(2, obj.getCall().getId());
        statement.setTimestamp(3, new Timestamp(obj.getScheduledAt().getTime()));
    }

    @Override
    protected void prepareUpdate(ScheduledCall obj, PreparedStatement statement) throws SQLException {
        if (obj.getCall() != null)
            statement.setLong(1, obj.getCall().getId());
        statement.setTimestamp(2, new Timestamp(obj.getScheduledAt().getTime()));
        statement.setLong(3, obj.getId());
    }

    @Override
    protected ScheduledCall createObj() {
        return new ScheduledCall();
    }

    @Override
    protected ScheduledCall readObj(ScheduledCall obj, ResultSet source) throws SQLException {
        obj.setId(source.getLong(1));
        try {
            obj.setCall(callDataObject.getByID(source.getLong(2)));
        } catch (PersistentException e) {
            obj.setCall(null);
        }
        obj.setScheduledAt(source.getTimestamp(3));

        return obj;
    }
}
