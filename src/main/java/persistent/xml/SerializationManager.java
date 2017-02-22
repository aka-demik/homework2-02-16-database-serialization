package persistent.xml;

import models.*;
import org.apache.log4j.Logger;
import persistent.db.*;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    private void saveCalls() {
        try {
            if (!stop)
                Serializer.save(
                        new CallWrapper(new CallDataObject(con).getAll()),
                        new File(CALLS_XML),
                        CallWrapper.class, Call.class);
        } catch (JAXBException | IOException | SQLException e) {
            logger.error("Calls export error", e);
            stop = true;
        }
    }

    private void saveCallReasons() {
        try {
            if (!stop)
                Serializer.save(
                        new CallReasonWrapper(new CallReasonDataObject(con).getAll()),
                        new File(CALL_REASONS_XML),
                        CallReasonWrapper.class, CallReason.class);
        } catch (JAXBException | IOException | SQLException e) {
            logger.error("Call reasons export error", e);
            stop = true;
        }
    }

    private void saveScheduledCalls() {
        try {
            if (!stop)
                Serializer.save(
                        new ScheduledCallWrapper(new ScheduledCallDataObject(con).getAll()),
                        new File(SCHEDULED_CALLS_XML),
                        ScheduledCallWrapper.class, ScheduledCall.class);
        } catch (JAXBException | IOException | SQLException e) {
            logger.error("Scheduled calls export error", e);
            stop = true;
        }
    }

    private void saveSuperUsers() {
        try {
            if (!stop)
                Serializer.save(
                        new SuperUserWrapper(new SuperUserDataObject(con).getAll()),
                        new File(SUPER_USERS_XML),
                        SuperUserWrapper.class, SuperUser.class);
        } catch (JAXBException | IOException | SQLException e) {
            logger.error("SuperUser export error", e);
            stop = true;
        }
    }

    private void saveUsers() {
        try {
            if (!stop)
                Serializer.save(
                        new UserWrapper(new UserDataObject(con).getAll()),
                        new File(USERS_XML),
                        UserWrapper.class, User.class);
        } catch (JAXBException | IOException | SQLException e) {
            logger.error("User export error", e);
            stop = true;
        }
    }

    public void exportAll() {
        final ExecutorService pool = Executors.newCachedThreadPool();
        pool.submit(this::saveCalls);
        pool.submit(this::saveCallReasons);
        pool.submit(this::saveScheduledCalls);
        pool.submit(this::saveSuperUsers);
        pool.submit(this::saveUsers);
        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        } finally {
            stop = false;
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
            Collection<SuperUser> users = superUsersFuture.get().items;
            pool.submit(() -> importCollection(users, new SuperUserDataObject(con)));
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error reading super users from xml", e);
            stop = true;
            return;
        }

        try {
            Collection<CallReason> items = crFuture.get().items;
            pool.submit(() -> importCollection(items, new CallReasonDataObject(con)));
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error reading CallReason from xml", e);
            stop = true;
            return;
        }

        try {
            Collection<User> users = usersFuture.get().items;
            pool.submit(() -> importCollection(users, new UserDataObject(con)));
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error reading users from xml", e);
            stop = true;
            return;
        }

        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            stop = true;
            return;
        }

        if (stop)
            return;

        try {
            Collection<Call> items = callFuture.get().items;
            importCollection(items, new CallDataObject(con));
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error reading Call from xml", e);
            stop = true;
            return;
        }

        try {
            Collection<ScheduledCall> items = shCallFuture.get().items;
            importCollection(items, new ScheduledCallDataObject(con));
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error reading ScheduledCall from xml", e);
        }
    }

    private void importCollection(Collection collection, AbstractDataObject ado) {
        if (!stop) {
            try {
                ado.insert(collection);
            } catch (SQLException e) {
                logger.error("Error writing to DB", e);
                stop = true;
            }
        }
    }
}
