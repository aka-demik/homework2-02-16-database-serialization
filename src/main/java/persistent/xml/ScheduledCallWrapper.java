package persistent.xml;

import models.ScheduledCall;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "ScheduledCalls")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScheduledCallWrapper {

    @XmlElement(name = "ScheduledCall")
    public Collection<ScheduledCall> items = null;

    public ScheduledCallWrapper() {
    }

    public ScheduledCallWrapper(Collection<ScheduledCall> items) {
        this.items = items;
    }

}
