package persistent.xml;

import models.*;
import org.apache.log4j.Logger;
import persistent.db.*;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.*;

public class SerializationManager {
    private static final String CALL_REASONS_XML = "CallReasons.xml";
    private static final String SCHEDULED_CALLS_XML = "ScheduledCalls.xml";
    private static final String CALLS_XML = "Calls.xml";
    private static final String SUPER_USERS_XML = "SuperUsers.xml";
    private static final String USERS_XML = "Users.xml";

    private static Logger logger = Logger.getLogger(SerializationManager.class);
    private Connection con;
    private boolean stop = false;

    public SerializationManager(Connection con) {
        this.con = con;
    }

    public void exportAll() {
        final ExecutorService pool = Executors.newCachedThreadPool();

        pool.submit(() -> saveXML(new CallWrapper(), new CallDataObject(con),
                CALLS_XML, CallWrapper.class, Call.class));

        pool.submit(() -> saveXML(new CallReasonWrapper(), new CallReasonDataObject(con),
                CALL_REASONS_XML, CallReasonWrapper.class, CallReason.class));

        pool.submit(() -> saveXML(new ScheduledCallWrapper(), new ScheduledCallDataObject(con),
                SCHEDULED_CALLS_XML, ScheduledCallWrapper.class, ScheduledCall.class));

        pool.submit(() -> saveXML(new SuperUserWrapper(), new SuperUserDataObject(con),
                SUPER_USERS_XML, SuperUserWrapper.class, SuperUser.class));

        pool.submit(() -> saveXML(new UserWrapper(), new UserDataObject(con),
                USERS_XML, UserWrapper.class, User.class));

        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            stop = true;
            logger.error(e.getMessage(), e);
        }
    }

    public void importAll() {
        final ExecutorService pool = Executors.newCachedThreadPool();

        Future<CallWrapper> callFuture = pool.submit(() -> {
            return (CallWrapper) Serializer.load(new File(CALLS_XML),
                    CallWrapper.class);
        });

        Future<CallReasonWrapper> crFuture = pool.submit(() -> {
            return (CallReasonWrapper) Serializer.load(new File(CALL_REASONS_XML),
                    CallReasonWrapper.class);
        });

        Future<ScheduledCallWrapper> shCallFuture = pool.submit(() -> {
            return (ScheduledCallWrapper) Serializer.load(new File(SCHEDULED_CALLS_XML),
                    ScheduledCallWrapper.class);
        });

        Future<SuperUserWrapper> superUsersFuture = pool.submit(() -> {
            return (SuperUserWrapper) Serializer.load(new File(SUPER_USERS_XML),
                    SuperUserWrapper.class);
        });

        Future<UserWrapper> usersFuture = pool.submit(() -> {
            return (UserWrapper) Serializer.load(new File(USERS_XML),
                    UserWrapper.class);
        });

        try {
            Collection<SuperUser> users = superUsersFuture.get().getItems();
            pool.submit(() -> importCollection(users, new SuperUserDataObject(con)));
        } catch (InterruptedException | ExecutionException e) {
            stop = true;
            logger.error("Error reading super users from xml", e);
            return;
        }

        try {
            Collection<CallReason> items = crFuture.get().getItems();
            pool.submit(() -> importCollection(items, new CallReasonDataObject(con)));
        } catch (InterruptedException | ExecutionException e) {
            stop = true;
            logger.error("Error reading CallReason from xml", e);
            return;
        }

        try {
            Collection<User> users = usersFuture.get().getItems();
            pool.submit(() -> importCollection(users, new UserDataObject(con)));
        } catch (InterruptedException | ExecutionException e) {
            stop = true;
            logger.error("Error reading users from xml", e);
            return;
        }

        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            stop = true;
            logger.error(e.getMessage(), e);
            return;
        }

        if (stop)
            return;

        try {
            Collection<Call> items = callFuture.get().getItems();
            importCollection(items, new CallDataObject(con));
        } catch (InterruptedException | ExecutionException e) {
            stop = true;
            logger.error("Error reading Call from xml", e);
            return;
        }

        try {
            Collection<ScheduledCall> items = shCallFuture.get().getItems();
            importCollection(items, new ScheduledCallDataObject(con));
        } catch (InterruptedException | ExecutionException e) {
            stop = true;
            logger.error("Error reading ScheduledCall from xml", e);
        }
    }

    private <T> void importCollection(Collection<T> collection, AbstractDataObject<T> ado) {
        if (!stop) {
            try {
                ado.insert(collection);
            } catch (SQLException e) {
                logger.error("Error writing to DB", e);
                stop = true;
            }
        }
    }

    private <T> void saveXML(AbstractWrapper<T> wrapper, AbstractDataObject<T> dao, String fileName,
                             Class... classesToBeBound) {
        if (!stop) {
            try {
                wrapper.setItems(dao.getAll());
                Serializer.save(wrapper, new File(fileName), classesToBeBound);
            } catch (JAXBException | IOException | SQLException e) {
                stop = true;
                logger.error(wrapper.getClass().getName() + " export error", e);
            }
        }
    }
}
