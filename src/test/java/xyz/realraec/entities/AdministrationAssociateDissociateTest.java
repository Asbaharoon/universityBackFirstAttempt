package xyz.realraec.entities;

import org.junit.Assert;
import org.junit.Test;
import xyz.realraec.queries.DALQueries;

/**
 * Tests for the Administration class: methods relative to associating and dissociating
 *
 * @see Administration
 */
public class AdministrationAssociateDissociateTest {

    /**
     * Test to check that it is possible to associate a Course and a Professor:
     * successful
     *
     * @throws Exception if getting from database fails
     * @see Administration#associateCourseAndProfessor(Course, Professor)
     */
    @Test
    public void associateCourseAndProfessorTest() throws Exception {
        Course courseNotAssociated = (Course) DALQueries.get(Course.class, 1);
        Professor professorNotAssociated = (Professor) DALQueries.get(Professor.class, 1);

        Assert.assertTrue(Administration.associateCourseAndProfessor(courseNotAssociated, professorNotAssociated));
        Assert.assertEquals(courseNotAssociated.getTaughtBy(), professorNotAssociated);

        Administration.dissociateCourseAndProfessor(courseNotAssociated, professorNotAssociated);
    }

    /**
     * Test to check that it is possible to associate a Course and a Professor:
     * unsuccessful
     *
     * @throws Exception if getting from database fails
     * @see Administration#associateCourseAndProfessor(Course, Professor)
     */
    @Test
    public void associateCourseAndProfessorErrorConditionTest() throws Exception {
        Course courseAssociated = (Course) DALQueries.get(Course.class, 2);
        Professor professorAssociated = (Professor) DALQueries.get(Professor.class, 2);

        try {
            Assert.assertFalse(Administration.associateCourseAndProfessor(courseAssociated, professorAssociated));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "The professor and course have already been associated.");
        }
    }

    /**
     * Test to check that it is possible to dissociate a Course and a Professor:
     * successful
     *
     * @throws Exception if getting from database fails
     * @see Administration#dissociateCourseAndProfessor(Course, Professor)
     */
    @Test
    public void dissociateCourseAndProfessorTest() throws Exception {
        Course courseAssociated = (Course) DALQueries.get(Course.class, 3);
        Professor professorAssociated = (Professor) DALQueries.get(Professor.class, 3);

        Assert.assertTrue(Administration.dissociateCourseAndProfessor(courseAssociated, professorAssociated));
        Assert.assertNotEquals(courseAssociated.getTaughtBy(), professorAssociated);

        Administration.associateCourseAndProfessor(courseAssociated, professorAssociated);
    }

    /**
     * Test to check that it is possible to dissociate a Course and a Professor:
     * unsuccessful
     *
     * @throws Exception if getting from database fails
     * @see Administration#dissociateCourseAndProfessor(Course, Professor)
     */
    @Test
    public void dissociateCourseAndProfessorErrorConditionTest() throws Exception {
        Course courseNotAssociated = (Course) DALQueries.get(Course.class, 4);
        Professor professorNotAssociated = (Professor) DALQueries.get(Professor.class, 4);

        try {
            Assert.assertFalse(Administration.dissociateCourseAndProfessor(courseNotAssociated, professorNotAssociated));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(),
                    "The professor and course have not been associated so they can't be dissociated.");
        }
    }

    /**
     * Test to check that it is possible to associate a Degree and a Course:
     * successful
     *
     * @throws Exception if getting from database fails
     * @see Administration#associateDegreeAndCourse(Degree, Course)
     */
    @Test
    public void associateDegreeAndCourseTest() throws Exception {
        Course courseNotAssociated = (Course) DALQueries.get(Course.class, 3);
        Degree degreeNotAssociated = (Degree) DALQueries.get(Degree.class, 2);

        Assert.assertTrue(Administration.associateDegreeAndCourse(degreeNotAssociated, courseNotAssociated));
        Assert.assertTrue(degreeNotAssociated.getCourses().contains(courseNotAssociated));

        Administration.dissociateDegreeAndCourse(degreeNotAssociated, courseNotAssociated);
    }

    /**
     * Test to check that it is possible to associate a Degree and a Course:
     * unsuccessful
     *
     * @throws Exception if getting from database fails
     * @see Administration#associateDegreeAndCourse(Degree, Course)
     */
    @Test
    public void associateDegreeAndCourseErrorConditionTest() throws Exception {
        Course courseAssociated = (Course) DALQueries.get(Course.class, 6);
        Degree degreeAssociated = (Degree) DALQueries.get(Degree.class, 3);

        try {
            Assert.assertFalse(Administration.associateDegreeAndCourse(degreeAssociated, courseAssociated));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "The course and degree have already been associated.");
        } finally {
            degreeAssociated.getCourses().remove(courseAssociated);
        }
    }

    /**
     * Test to check that it is possible to dissociate a Degree and a Course:
     * successful
     *
     * @throws Exception if getting from database fails
     * @see Administration#dissociateDegreeAndCourse(Degree, Course)
     */
    @Test
    public void dissociateDegreeAndCourseTest() throws Exception {
        Course courseAssociated = (Course) DALQueries.get(Course.class, 6);
        Degree degreeAssociated = (Degree) DALQueries.get(Degree.class, 3);

        Assert.assertTrue(Administration.dissociateDegreeAndCourse(degreeAssociated, courseAssociated));
        Assert.assertFalse(degreeAssociated.getCourses().contains(courseAssociated));

        Administration.associateDegreeAndCourse(degreeAssociated, courseAssociated);
    }

    /**
     * Test to check that it is possible to dissociate a Degree and a Course:
     * unsuccessful
     *
     * @throws Exception if getting from database fails
     * @see Administration#dissociateDegreeAndCourse(Degree, Course)
     */
    @Test
    public void dissociateDegreeAndCourseErrorConditionTest() throws Exception {
        Course courseNotAssociated = (Course) DALQueries.get(Course.class, 1);
        Degree degreeNotAssociated = (Degree) DALQueries.get(Degree.class, 3);

        try {
            Assert.assertFalse(Administration.dissociateDegreeAndCourse(degreeNotAssociated, courseNotAssociated));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(),
                    "The course and degree have not been associated so they can't be dissociated.");
        }
    }

}
