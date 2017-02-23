package persistent.xml;

import models.User;

import java.util.Collection;

public abstract class AbstractWrapper<T> {
    public abstract Collection<T> getItems();

    public abstract void setItems(Collection<T> items);
}
