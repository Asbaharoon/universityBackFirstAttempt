package xyz.realraec.entities;

import org.junit.Assert;
import org.junit.Test;
import xyz.realraec.queries.DALQueries;

/**
 * Tests for the Administration class: methods relative to credits
 *
 * @see Administration
 */
public class AdministrationCreditsTest {

    /**
     * Test to check if it is possible to get the right amount of credits
     * a Course is worth to a Student enrolled in this Course: successful
     *
     * @throws Exception if getting from database fails
     * @see Administration#getCreditsWorthAsCourse(Student, Course)
     */
    @Test
    public void getCreditsWorthAsCourseTest() throws Exception {
        Student student = (Student) DALQueries.get(Student.class, 1);
        Course courseMajorDegree = (Course) DALQueries.get(Course.class, 1);
        Course courseMinorDegree = (Course) DALQueries.get(Course.class, 4);

        Assert.assertEquals(Administration.getCreditsWorthAsCourse(student, courseMajorDegree), Course.creditsBase * 4);
        Assert.assertEquals(Administration.getCreditsWorthAsCourse(student, courseMinorDegree), Course.creditsBase * 2);
    }

    /**
     * Test to check if it is possible to get the right amount of credits
     * a Course is worth to a Student enrolled in this Course: unsuccessful
     *
     * @throws Exception if getting from database fails
     * @see Administration#getCreditsWorthAsCourse(Student, Course)
     */
    @Test
    public void getCreditsWorthAsCourseErrorConditionTest() throws Exception {
        Student student = (Student) DALQueries.get(Student.class, 1);
        Course courseNeitherDegree = (Course) DALQueries.get(Course.class, 3);

        try {
            Assert.assertNotEquals(Administration.getCreditsWorthAsCourse(student, courseNeitherDegree), Course.creditsBase * 4);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "The student is not enrolled in this course.");
        }

        try {
            Assert.assertNotEquals(Administration.getCreditsWorthAsCourse(student, courseNeitherDegree), Course.creditsBase * 2);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "The student is not enrolled in this course.");
        }
    }

    /**
     * Test to check if it is possible to get the right amount of credits
     * a Degree is worth to a Student enrolled in this Degree: successful
     *
     * @throws Exception if getting from database fails
     * @see Administration#getCreditsWorthAsDegree(Student, Degree)
     */
    @Test
    public void getCreditsWorthAsDegreeTest() throws Exception {
        Student student = (Student) DALQueries.get(Student.class, 1);
        Degree majorDegree = (Degree) DALQueries.get(Degree.class, 1);
        Degree minorDegree = (Degree) DALQueries.get(Degree.class, 2);

        Assert.assertEquals(Administration.getCreditsWorthAsDegree(student, majorDegree),
                Course.creditsBase * 4 * majorDegree.getCourses().size());
        Assert.assertEquals(Administration.getCreditsWorthAsDegree(student, minorDegree),
                Course.creditsBase * 2 * minorDegree.getCourses().size());
    }

    /**
     * Test to check if it is possible to get the right amount of credits
     * a Degree is worth to a Student enrolled in this Degree: unsuccessful
     *
     * @throws Exception if getting from database fails
     * @see Administration#getCreditsWorthAsDegree(Student, Degree)
     */
    @Test
    public void getCreditsWorthAsDegreeErrorConditionTest() throws Exception {
        Student student = (Student) DALQueries.get(Student.class, 1);
        Degree neitherDegree = (Degree) DALQueries.get(Degree.class, 3);

        try {
            Assert.assertNotEquals(Administration.getCreditsWorthAsDegree(student, neitherDegree), Course.creditsBase * 8);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "The student is not enrolled in this degree.");
        }
    }

    /**
     * Test to check if it is possible to give credits to a given Student for a given course: successful
     *
     * @throws Exception if getting from database fails
     * @see Administration#giveCredits(Student, Course)
     */
    @Test
    public void giveCreditsTest() throws Exception {
        Student student = (Student) DALQueries.get(Student.class, 3);
        Course courseTakenByStudentYESExamMadeYESExamTakenYES = (Course) DALQueries.get(Course.class, 8);

        int creditsBefore = student.getCreditsObtained();
        Assert.assertTrue(Administration.giveCredits(student, courseTakenByStudentYESExamMadeYESExamTakenYES));
        int creditsAfter = student.getCreditsObtained();
        Assert.assertEquals(creditsAfter, creditsBefore + (Course.creditsBase * 2));
    }

    /**
     * Test to check if it is possible to give credits to a given Student for a given course: unsuccessful
     *
     * @throws Exception if getting from database fails
     * @see Administration#giveCredits(Student, Course)
     */
    @Test
    public void giveCreditsErrorConditionTest() throws Exception {
        Student student = (Student) DALQueries.get(Student.class, 1);
        Course courseTakenByStudentNO = (Course) DALQueries.get(Course.class, 7);

        try {
            Assert.assertFalse(Administration.giveCredits(student, courseTakenByStudentNO));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "Credits could not be given to the Student for this Course.");
        }
    }

    /**
     * Test to check if it is possible to give a diploma to a given Student: successful
     *
     * @throws Exception if getting from database fails
     * @see Administration#giveDiploma(Student)
     */
    @Test
    public void giveDiplomaTest() throws Exception {
        Student studentAllCoursesCompletedYES = (Student) DALQueries.get(Student.class, 3);

        Assert.assertTrue(Administration.giveDiploma(studentAllCoursesCompletedYES));
        Assert.assertEquals(studentAllCoursesCompletedYES.getLastDiplomaObtained(), "Bachelor's");

        studentAllCoursesCompletedYES.setLevel(5);
        Assert.assertTrue(Administration.giveDiploma(studentAllCoursesCompletedYES));
        Assert.assertEquals(studentAllCoursesCompletedYES.getLastDiplomaObtained(), "Master's");

        studentAllCoursesCompletedYES.setLevel(7);
        Assert.assertTrue(Administration.giveDiploma(studentAllCoursesCompletedYES));
        Assert.assertEquals(studentAllCoursesCompletedYES.getLastDiplomaObtained(), "Doctorate");

        studentAllCoursesCompletedYES.setTotalTuition(0);
        studentAllCoursesCompletedYES.setLevel(3);
        studentAllCoursesCompletedYES.setLastDiplomaObtained("");
        DALQueries.update(studentAllCoursesCompletedYES);
    }

    /**
     * Test to check if it is possible to give a diploma to a given Student: unsuccessful
     *
     * @throws Exception if getting from database fails
     * @see Administration#giveDiploma(Student)
     */
    @Test
    public void giveDiplomaErrorConditionTest() throws Exception {
        Student studentAllCoursesCompletedNO = (Student) DALQueries.get(Student.class, 5);

        try {
            Assert.assertFalse(Administration.giveDiploma(studentAllCoursesCompletedNO));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "Could not give the Student a diploma.");
        }

        studentAllCoursesCompletedNO = (Student) DALQueries.get(Student.class, 3);
        studentAllCoursesCompletedNO.setLevel(1);
        try {
            Assert.assertFalse(Administration.giveDiploma(studentAllCoursesCompletedNO));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "Could not give the Student a diploma.");
            Assert.assertEquals(studentAllCoursesCompletedNO.getLastDiplomaObtained(), "");
        }

        studentAllCoursesCompletedNO.setLevel(3);
        DALQueries.update(studentAllCoursesCompletedNO);
    }

}
