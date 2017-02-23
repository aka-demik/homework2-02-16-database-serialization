package persistent.xml;

import models.Call;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "Calls")
@XmlAccessorType(XmlAccessType.FIELD)
public class CallWrapper extends AbstractWrapper<Call> {

    @XmlElement(name = "Call")
    private Collection<Call> items = null;

    public CallWrapper() {
    }

    public CallWrapper(Collection<Call> items) {
        this.items = items;
    }

    @Override
    public Collection<Call> getItems() {
        return items;
    }

    @Override
    public void setItems(Collection<Call> items) {
        this.items = items;
    }

}
