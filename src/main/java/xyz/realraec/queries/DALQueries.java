package xyz.realraec.queries;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import xyz.realraec.adaptors.Adaptor;

/**
 * Independent DAL class with all the getters, initializations, and queries
 */
public class DALQueries {
    /**
     * Configuration object needed to create the SessionFactory object
     */
    private static final Configuration configuration = new Configuration().configure();
    /**
     * SessionFactory object needed to create the Session object
     */
    private static final SessionFactory sessionFactory = configuration.buildSessionFactory();
    /**
     * Session object needed to create the Transaction object
     */
    private static Session session = null;
    /**
     * Transaction object needed to perform queries to the database
     */
    private static Transaction transaction = null;

    /**
     * Getter method for the {@link DALQueries#configuration} attribute
     *
     * @return the current Configuration object
     */
    public static Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Getter method for the {@link DALQueries#sessionFactory} attribute
     *
     * @return the current SessionFactory object
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Getter method for the {@link DALQueries#session} attribute
     *
     * @return the current Session object
     */
    public static Session getSession() {
        return session;
    }

    /**
     * Getter method for the {@link DALQueries#transaction} attribute
     *
     * @return the current Transaction object
     */
    public static Transaction getTransaction() {
        return transaction;
    }


    /**
     * Internal method to create a new session or get an already-existing one
     * before the 'try' block of every method that queries the database
     *
     * @return Session object that can be used in an external class
     */
    public static Session beforeTryBlock() {
        try {
            session = getSessionFactory().getCurrentSession();
        } catch (HibernateException e) {
            session = getSessionFactory().openSession();
        }
        return session;
    }

    /**
     * Internal method to roll back the transaction in the 'catch' block
     * of every method that queries the database
     *
     * @param e HibernateException that is thrown
     *          if the try block in the query method fails
     * @return Transaction object for testing purposes
     */
    public static Transaction catchBlock(HibernateException e) {
        System.err.println(e.getMessage());
        if (transaction != null) {
            transaction.rollback();
        }
        return transaction;
    }

    /**
     * Internal method to close the session in the 'finally' block
     * of every method that queries the database
     *
     * @return Session object for testing purposes
     */
    public static Session finallyBlock() {
        if (session.isOpen()) {
            session.close();
        }
        return session;
    }

    /**
     * Query to get/fetch/retrieve an object from the database
     *
     * @param classWanted of the object to be fetched
     * @param id          of the object to be fetched
     * @return the object being fetched, to be cast non-dynamically
     * @throws Exception "Could not get the object from the database."
     */
    public static Object get(Class<?> classWanted, long id) throws Exception {
        beforeTryBlock();

        try {
            if (Adaptor.isValidClass(classWanted)
                    && Adaptor.isValidId(classWanted, id)) {
                try {
                    transaction = session.beginTransaction();
                    Object objectToReturn = session.get(classWanted, id);
                    transaction.commit();
                    return objectToReturn;
                } catch (HibernateException e) {
                    catchBlock(e);
                } finally {
                    finallyBlock();
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        throw new Exception("Could not get the object from the database.");
    }

    /**
     * Query to save/store/persist an object in the database
     *
     * @param object to be stored
     * @return true if successful
     * @throws Exception "Could not save the object to the database."
     */
    public static boolean save(Object object) throws Exception {
        beforeTryBlock();

        try {
            if (Adaptor.isValidObject(object)) {
                try {
                    transaction = session.beginTransaction();
                    session.save(object);
                    transaction.commit();
                    return true;
                } catch (HibernateException e) {
                    catchBlock(e);
                } finally {
                    finallyBlock();
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        throw new Exception("Could not save the object to the database.");
    }

    /**
     * Query to update/merge an object in the database
     *
     * @param object to be updated
     * @return true if successful
     * @throws Exception "Could not update the object in the database."
     */
    public static boolean update(Object object) throws Exception {
        beforeTryBlock();

        try {
            if (Adaptor.isValidObject(object)) {
                try {
                    transaction = session.beginTransaction();
                    session.update(object);
                    transaction.commit();
                    return true;
                } catch (HibernateException e) {
                    catchBlock(e);
                } finally {
                    finallyBlock();
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        throw new Exception("Could not update the object in the database.");
    }

    /**
     * Query to delete/remove an object from the database
     *
     * @param object to be deleted
     * @return true if successful
     * @throws Exception "Could not delete the object from the database."
     */
    public static boolean delete(Object object) throws Exception {
        beforeTryBlock();

        try {
            if (Adaptor.isValidObject(object)) {
                try {
                    transaction = session.beginTransaction();
                    session.delete(object);
                    transaction.commit();
                    return true;
                } catch (HibernateException e) {
                    catchBlock(e);
                } finally {
                    finallyBlock();
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        throw new Exception("Could not delete the object from the database.");
    }

}
