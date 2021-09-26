package xyz.realraec.queries;

import org.junit.Assert;
import org.junit.Test;
import xyz.realraec.entities.Course;
import xyz.realraec.entities.Degree;
import xyz.realraec.entities.Professor;
import xyz.realraec.entities.Student;

/**
 * Tests to check the app's behavior
 * when it comes to deleting objects from a database
 *
 * @see DALQueries#delete(Object)
 */
public class DALDeleteTest {

    /**
     * Test to check if it is possible to delete a Professor object
     * from the database: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void deleteProfessorTest() throws Exception {
        Professor professor = new Professor();
        DALQueries.save(professor);
        Assert.assertTrue(DALQueries.delete(professor));
    }

    /**
     * Test to check if it is possible to delete a Student object
     * from the database: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void deleteStudentTest() throws Exception {
        Student student = new Student();
        DALQueries.save(student);
        Assert.assertTrue(DALQueries.delete(student));
    }

    /**
     * Test to check if it is possible to delete a Course object
     * from the database: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void deleteCourseTest() throws Exception {
        Course course = new Course();
        DALQueries.save(course);
        Assert.assertTrue(DALQueries.delete(course));
    }

    /**
     * Test to check if it is possible to delete a Degree object
     * from the database: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void deleteDegreeTest() throws Exception {
        Degree degree = new Degree();
        DALQueries.save(degree);
        Assert.assertTrue(DALQueries.delete(degree));
    }

    /**
     * Test to check if it is possible to delete an invalid object
     * from the database: unsuccessful
     */
    @Test
    public void deleteErrorConditionTest() {
        Professor invalidObject = null;

        try {
            Assert.assertFalse(DALQueries.delete(invalidObject));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "Could not delete the object from the database.");
        }
    }

}
