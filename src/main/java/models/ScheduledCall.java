package models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name = "ScheduledCall")
//@XmlAccessorType(XmlAccessType.FIELD)
public class ScheduledCall {

    private long id;
    private Call call;
    private java.util.Date scheduledAt;

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

    public java.util.Date getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(java.util.Date scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

}
