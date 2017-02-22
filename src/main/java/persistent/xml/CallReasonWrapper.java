package persistent.xml;

import models.CallReason;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "CallReasons")
@XmlAccessorType(XmlAccessType.FIELD)
public class CallReasonWrapper {

    @XmlElement(name = "CallReason")
    public Collection<CallReason> items = null;

    public CallReasonWrapper() {

    }

    public CallReasonWrapper(Collection<CallReason> items) {
        this.items = items;
    }

}
