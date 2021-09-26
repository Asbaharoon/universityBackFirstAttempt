package xyz.realraec.queries;

import org.junit.Assert;
import org.junit.Test;
import xyz.realraec.entities.Course;
import xyz.realraec.entities.Degree;
import xyz.realraec.entities.Professor;
import xyz.realraec.entities.Student;

/**
 * Tests to check the app's behavior
 * when it comes to saving objects to a database
 *
 * @see DALQueries#save(Object)
 */
public class DALSaveTest {

    /**
     * Test to check if it is possible to save a Professor object
     * to the database: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void saveProfessorTest() throws Exception {
        Professor professor = new Professor();
        Assert.assertTrue(DALQueries.save(professor));
        DALQueries.delete(professor);
    }

    /**
     * Test to check if it is possible to save a Student object
     * to the database: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void saveStudentTest() throws Exception {
        Student student = new Student();
        Assert.assertTrue(DALQueries.save(student));
        DALQueries.delete(student);
    }

    /**
     * Test to check if it is possible to save a Course object
     * to the database: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void saveCourseTest() throws Exception {
        Course course = new Course();
        Assert.assertTrue(DALQueries.save(course));
        DALQueries.delete(course);
    }

    /**
     * Test to check if it is possible to save a Degree object
     * to the database: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void saveDegreeTest() throws Exception {
        Degree degree = new Degree();
        Assert.assertTrue(DALQueries.save(degree));
        DALQueries.delete(degree);
    }

    /**
     * Test to check if it is possible to save an invalid object
     * to the database: unsuccessful
     */
    @Test
    public void saveErrorConditionTest() {
        Student invalidObject = null;

        try {
            Assert.assertFalse(DALQueries.save(invalidObject));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "Could not save the object to the database.");
        }
    }

}
