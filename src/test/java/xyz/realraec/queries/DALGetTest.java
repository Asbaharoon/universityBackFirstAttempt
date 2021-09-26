package xyz.realraec.queries;

import org.junit.Assert;
import org.junit.Test;
import xyz.realraec.entities.*;

/**
 * Tests to check the app's behavior
 * when it comes to getting objects from a database
 *
 * @see DALQueries#get(Class, long)
 */
public class DALGetTest {

    /**
     * Test to check if it is possible to get a Student object
     * from the database: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void getStudentTest() throws Exception {
        Class<?> validClass = Student.class;
        long validId = 1;

        Assert.assertNotNull(DALQueries.get(validClass, validId));
    }

    /**
     * Test to check if it is possible to get a Professor object
     * from the database: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void getProfessorTest() throws Exception {
        Class<?> validClass = Professor.class;
        long validId = 1;

        Assert.assertNotNull(DALQueries.get(validClass, validId));
    }

    /**
     * Test to check if it is possible to get a Course object
     * from the database: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void getCourseTest() throws Exception {
        Class<?> validClass = Course.class;
        long validId = 1;

        Assert.assertNotNull(DALQueries.get(validClass, validId));
    }

    /**
     * Test to check if it is possible to get a Degree object
     * from the database: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void getDegreeTest() throws Exception {
        Class<?> validClass = Degree.class;
        long validId = 1;

        Assert.assertNotNull(DALQueries.get(validClass, validId));
    }

    /**
     * Test to check if it is possible to get an Administration object
     * from the database: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void getAdministrationTest() throws Exception {
        Class<?> validClass = Administration.class;
        long validId = 1;

        Assert.assertNotNull(DALQueries.get(validClass, validId));
    }

    /**
     * Test to check if it is possible to get an object
     * from the database with a valid class but an invalid id: unsuccessful
     */
    @Test
    public void getErrorConditionTestId() {
        Class<?> validClass = Student.class;
        long invalidId = -1;

        try {
            Assert.assertNull(DALQueries.get(validClass, invalidId));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "Could not get the object from the database.");
        }
    }

    /**
     * Test to check if it is possible to get an object
     * from the database with a valid id but an invalid class: unsuccessful
     */
    @Test
    public void getErrorConditionTestClass() {
        Class<?> invalidClass = Assert.class;
        long validId = 1;

        try {
            Assert.assertNull(DALQueries.get(invalidClass, validId));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "Could not get the object from the database.");
        }
    }

}
