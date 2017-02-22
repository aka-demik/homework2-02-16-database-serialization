package models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

public class Call {

    private long id;
    private long callReasonId;
    private long userId;
    private long superuserId;
    private java.util.Date createdAt;
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

    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

}
