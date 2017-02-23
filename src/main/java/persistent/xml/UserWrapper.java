package persistent.xml;

import models.User;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "Users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWrapper extends AbstractWrapper<User> {

    @XmlElement(name = "User")
    private Collection<User> items = null;

    public UserWrapper() {

    }

    public UserWrapper(Collection<User> items) {
        this.items = items;
    }

    @Override
    public Collection<User> getItems() {
        return items;
    }

    @Override
    public void setItems(Collection<User> items) {
        this.items = items;
    }

}
