package xyz.realraec.queries;

import org.junit.Assert;
import org.junit.Test;
import xyz.realraec.entities.*;

/**
 * Tests to check the app's behavior
 * when it comes to updating objects in a database
 *
 * @see DALQueries#update(Object)
 */
public class DALUpdateTest {

    private final double DELTA_CONSTANT = 1e-15;

    /**
     * Test to check if it is possible to update a Professor object
     * in the database: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void updateProfessorTest() throws Exception {
        Professor professor = (Professor) DALQueries.get(Professor.class, 1);
        String lastnameBefore = professor.getLastName();
        String lastnameAfter = "Other lastname";
        professor.setLastName(lastnameAfter);

        Assert.assertTrue(DALQueries.update(professor));
        professor = (Professor) DALQueries.get(Professor.class, 1);
        Assert.assertEquals(professor.getLastName(), lastnameAfter);

        professor.setLastName(lastnameBefore);
        DALQueries.update(professor);
    }

    /**
     * Test to check if it is possible to update a Student object
     * in the database: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void updateStudentTest() throws Exception {
        Student student = (Student) DALQueries.get(Student.class, 1);
        String lastnameBefore = student.getLastName();
        String lastnameAfter = "Other lastname";
        student.setLastName(lastnameAfter);

        Assert.assertTrue(DALQueries.update(student));
        student = (Student) DALQueries.get(Student.class, 1);
        Assert.assertEquals(student.getLastName(), lastnameAfter);

        student.setLastName(lastnameBefore);
        DALQueries.update(student);
    }

    /**
     * Test to check if it is possible to update a Course object
     * in the database: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void updateCourseTest() throws Exception {
        Course course = (Course) DALQueries.get(Course.class, 1);
        String nameBefore = course.getName();
        String nameAfter = "Other name";
        course.setName(nameAfter);

        Assert.assertTrue(DALQueries.update(course));
        Assert.assertEquals(course.getName(), nameAfter);

        course.setName(nameBefore);
        DALQueries.update(course);
    }

    /**
     * Test to check if it is possible to update a Degree object
     * in the database: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void updateDegreeTest() throws Exception {
        Degree degree = (Degree) DALQueries.get(Degree.class, 1);
        String nameBefore = degree.getName();
        String nameAfter = "Other name";
        degree.setName(nameAfter);

        Assert.assertTrue(DALQueries.update(degree));
        Assert.assertEquals(degree.getName(), nameAfter);

        degree.setName(nameBefore);
        DALQueries.update(degree);
    }

    /**
     * Test to check if it is possible to update an Administration object
     * in the database: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void updateAdministrationTest() throws Exception {
        Administration administration = (Administration) DALQueries.get(Administration.class, 1);
        double balanceBefore = administration.getAccountBalance();
        double balanceAfter = 0;
        administration.setAccountBalance(balanceAfter);

        Assert.assertTrue(DALQueries.update(administration));
        administration = (Administration) DALQueries.get(Administration.class, 1);
        Assert.assertEquals(administration.getAccountBalance(), balanceAfter, DELTA_CONSTANT);

        administration.setAccountBalance(balanceBefore);
        DALQueries.update(administration);
    }

    /**
     * Test to check if it is possible to update an invalid object
     * in the database: unsuccessful
     */
    @Test
    public void updateErrorConditionTest() {
        Course invalidObject = null;

        try {
            Assert.assertFalse(DALQueries.update(invalidObject));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "Could not update the object in the database.");
        }
    }

}
