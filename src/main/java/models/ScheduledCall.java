package models;

public class ScheduledCall {

    private long id;
    private Call call;
    private java.sql.Timestamp scheduledAt;

    public ScheduledCall() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public java.sql.Timestamp getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(java.sql.Timestamp scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

}
