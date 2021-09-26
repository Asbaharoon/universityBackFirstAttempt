package xyz.realraec.entities;

import org.junit.Assert;
import org.junit.Test;
import xyz.realraec.queries.DALQueries;

/**
 * Tests for the Course object
 *
 * @see Course
 */
public class CourseTest {

    /**
     * Test to check that the default Course constructor works,
     * together with other setters and getters
     *
     * @throws Exception if wrong level
     * @see Course#Course()
     */
    @Test
    public void newCourseDefaultConstructorTest() throws Exception {
        long id = 1;
        String name = "Biology";
        boolean examMade = false;
        boolean examTaken = false;
        int creditsBase = 5;
        Professor professor = new Professor();

        Course course = new Course();
        course.setId(id);
        course.setName(name);
        course.setExamMadeByProfessor(examMade);
        course.setExamTakenByStudent(examTaken);
        course.setCreditsBase(creditsBase);
        course.setTaughtBy(professor);

        Assert.assertEquals(course.getId(), id);
        Assert.assertEquals(course.getName(), name);
        Assert.assertEquals(course.isExamMadeByProfessor(), examMade);
        Assert.assertEquals(course.isExamTakenByStudent(), examTaken);
        Assert.assertEquals(course.getCreditsBase(), creditsBase);
        Assert.assertEquals(course.getTaughtBy(), professor);
    }

    /**
     * Test to check that the non-default Degree constructor works
     *
     * @see Course#Course(String)
     */
    @Test
    public void newCourseNonDefaultConstructorTest() {
        String name = "Biology";

        Course course = new Course(name);

        Assert.assertEquals(course.getName(), name);
    }

    /**
     * Test to check that the toString(), equals(), and hashCode() methods
     * have been overridden properly and can be used to compare Course objects
     *
     * @throws Exception if getting from database fails
     * @see Course#toString()
     * @see Course#equals(Object)
     * @see Course#hashCode()
     */
    @Test
    public void toStringEqualsHashCodeCourseTest() throws Exception {
        Course course1 = (Course) DALQueries.get(Course.class, 1);
        Course course2 = (Course) DALQueries.get(Course.class, 2);

        Assert.assertNotEquals(course1, course2);
        Assert.assertNotEquals(course1.hashCode(), course2.hashCode());
        Assert.assertNotEquals(course1.toString(), course2.toString());
    }

}
