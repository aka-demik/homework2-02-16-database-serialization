package models;

public class Call {

    private long id;
    private long callReasonId;
    private long userId;
    private long superuserId;
    private java.sql.Timestamp createdAt;
    private short status;

    public Call() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCallReasonId() {
        return callReasonId;
    }

    public void setCallReasonId(long callReasonId) {
        this.callReasonId = callReasonId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getSuperuserId() {
        return superuserId;
    }

    public void setSuperuserId(long superuserId) {
        this.superuserId = superuserId;
    }

    public java.sql.Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

}
