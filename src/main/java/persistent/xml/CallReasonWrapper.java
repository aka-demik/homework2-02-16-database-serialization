package persistent.xml;

import models.CallReason;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "CallReasons")
@XmlAccessorType(XmlAccessType.FIELD)
public class CallReasonWrapper extends AbstractWrapper<CallReason> {

    @XmlElement(name = "CallReason")
    private Collection<CallReason> items = null;

    public CallReasonWrapper() {

    }

    public CallReasonWrapper(Collection<CallReason> items) {
        this.items = items;
    }

    @Override
    public Collection<CallReason> getItems() {
        return items;
    }

    @Override
    public void setItems(Collection<CallReason> items) {
        this.items = items;
    }

}
