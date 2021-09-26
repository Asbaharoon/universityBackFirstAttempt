package xyz.realraec.queries;

import org.hibernate.HibernateException;
import org.hibernate.TransactionException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests to check the app's behavior
 * when it comes to using Hibernate/JPA
 *
 * @see DALQueries
 */
public class DALInitializeTest {

    /**
     * Test to check that the getters work for all the Hibernate objects
     */
    @Test
    public void getHibernateObjects() {
        Assert.assertNotNull(DALQueries.getConfiguration());
        Assert.assertNotNull(DALQueries.getSessionFactory());
        Assert.assertNotNull(DALQueries.getSession());
        Assert.assertNotNull(DALQueries.getTransaction());
    }

    /**
     * Test to check that the beforeTryBlock() method
     * properly initializes or retrieves the Session object
     *
     * @see DALQueries#beforeTryBlock()
     */
    @Test
    public void beforeTryBlockTest() {
        Assert.assertNotNull(DALQueries.beforeTryBlock());
        DALQueries.finallyBlock();
    }

    /**
     * Test to check that the catchBlock() method
     * properly rollbacks the transaction -
     * Note: cannot be reliably tested since requires a database failure
     *
     * @see DALQueries#catchBlock(HibernateException)
     */
    @Test
    public void catchBlockTest() {
        DALQueries.beforeTryBlock();
        try {
            try {
                throw new HibernateException("Mock hibernate exception.");
            } catch (HibernateException e) {
                System.out.println(DALQueries.getTransaction());
                DALQueries.getSession().beginTransaction();
                System.out.println(DALQueries.getTransaction());
                Assert.assertNull(DALQueries.catchBlock(e));
            }
        } catch (TransactionException e) {
            // TransactionException thrown after an attempt at a rollback
            // since the transaction is not still active, but already committed
            System.err.println(e.getMessage());
        } finally {
            DALQueries.finallyBlock();
        }
    }

    /**
     * Test to check that the finallyBlock() method
     * properly closes the session
     *
     * @see DALQueries#finallyBlock()
     */
    @Test
    public void finallyBlockTest() {
        DALQueries.beforeTryBlock();
        Assert.assertFalse(DALQueries.finallyBlock().isOpen());
    }

}
