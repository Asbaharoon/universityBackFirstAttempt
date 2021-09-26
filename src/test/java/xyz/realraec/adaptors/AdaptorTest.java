package xyz.realraec.adaptors;

import org.junit.Assert;
import org.junit.Test;
import xyz.realraec.entities.Course;
import xyz.realraec.entities.Degree;
import xyz.realraec.entities.Professor;
import xyz.realraec.entities.Student;

/**
 * Test class to make sure that the DAL and the objects are linked correctly
 */
public class AdaptorTest {

    /**
     * Test to check if an object is valid: successful
     *
     * @throws Exception if getting from database fails
     * @see Adaptor#isValidObject(Object)
     */
    @Test
    public void isValidObjectTest() throws Exception {
        Student validObject = new Student();
        Assert.assertTrue(Adaptor.isValidObject(validObject));
    }

    /**
     * Test to check if an object is valid: unsuccessful
     *
     * @see Adaptor#isValidObject(Object)
     */
    @Test
    public void isValidObjectErrorConditionTest() {
        Professor invalidObject = null;
        try {
            Assert.assertFalse(Adaptor.isValidObject(invalidObject));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "Invalid object.");
        }
    }

    /**
     * Test to check if a class is valid: successful
     *
     * @throws Exception if getting from database fails
     * @see Adaptor#isValidClass(Class)
     */
    @Test
    public void isValidClassTest() throws Exception {
        Class<?> validClass = Degree.class;
        Assert.assertTrue(Adaptor.isValidClass(validClass));
    }

    /**
     * Test to check if a class is valid: unsuccessful
     *
     * @see Adaptor#isValidClass(Class)
     */
    @Test
    public void isValidClassErrorConditionTest() {
        Class<?> invalidClass = Test.class;
        try {
            Assert.assertFalse(Adaptor.isValidClass(invalidClass));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "Invalid class.");
        }
    }

    /**
     * Test to check if an id is valid: successful
     *
     * @throws Exception if getting from database fails
     * @see Adaptor#isValidId(Class, long)
     */
    @Test
    public void isValidIdTest() throws Exception {
        Class<?> validClass = Course.class;
        long validId = 1L;
        Assert.assertTrue(Adaptor.isValidId(validClass, validId));
    }

    /**
     * Test to check if an id is valid: unsuccessful
     *
     * @see Adaptor#isValidId(Class, long)
     */
    @Test
    public void isValidIdErrorConditionTest() {
        Class<?> validClass = Student.class;
        long invalidId = -1L;
        try {
            Assert.assertFalse(Adaptor.isValidId(validClass, invalidId));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "Invalid id.");
        }
    }

    /**
     * Test to check if it is possible to get the biggest id in a table:
     * successful
     *
     * @see Adaptor#getMaxIdTable(Class)
     */
    @Test
    public void getMaxIdTableTest() {
        Class<?> validClass = Professor.class;
        Assert.assertNotEquals(Adaptor.getMaxIdTable(validClass), -1L);
    }

    /**
     * Test to check if it is possible to get the biggest id in a table:
     * unsuccessful
     *
     * @see Adaptor#getMaxIdTable(Class)
     */
    @Test
    public void getMaxIdTableErrorConditionTest() {
        Class<?> invalidClass = Test.class;
        Assert.assertEquals(Adaptor.getMaxIdTable(invalidClass), -1L);
    }

}
