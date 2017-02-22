package persistent.xml;

import models.SuperUser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "SuperUsers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SuperUserWrapper {

    @XmlElement(name = "SuperUser")
    public Collection<SuperUser> items = null;

    public SuperUserWrapper() {
    }

    public SuperUserWrapper(Collection<SuperUser> items) {
        this.items = items;
    }
}
