package xyz.realraec.entities;

import org.junit.Assert;
import org.junit.Test;
import xyz.realraec.queries.DALQueries;

import java.util.HashSet;

/**
 * Tests for the Degree object
 *
 * @see Degree
 */
public class DegreeTest {

    /**
     * Test to check that the default Degree constructor works,
     * together with other setters and getters
     *
     * @see Degree#Degree()
     */
    @Test
    public void degreeDefaultConstructorTest() {
        int id = 1;
        String name = "Scientific";
        HashSet<Course> coursesSet = new HashSet<>();

        Degree degree = new Degree();
        degree.setId(id);
        degree.setName(name);
        degree.setCourses(coursesSet);

        Assert.assertEquals(degree.getId(), id);
        Assert.assertEquals(degree.getName(), name);
        Assert.assertEquals(degree.getCourses(), coursesSet);
    }

    /**
     * Test to check that the non-default Degree constructor works
     *
     * @see Degree#Degree(String)
     */
    @Test
    public void degreeNonDefaultConstructorTest() {
        String name = "Scientific";

        Degree degree = new Degree(name);

        Assert.assertEquals(degree.getName(), name);
    }

    /**
     * Test to check that the toString(), equals(), and hashCode() methods
     * have been overridden properly and can be used to compare Degree objects
     *
     * @throws Exception if getting from database fails
     * @see Degree#toString()
     * @see Degree#equals(Object)
     * @see Degree#hashCode()
     */
    @Test
    public void toStringEqualsHashCodeCourseTest() throws Exception {
        Degree course1 = (Degree) DALQueries.get(Degree.class, 1);
        Degree course2 = (Degree) DALQueries.get(Degree.class, 2);

        Assert.assertNotEquals(course1, course2);
        Assert.assertNotEquals(course1.hashCode(), course2.hashCode());
        Assert.assertNotEquals(course1.toString(), course2.toString());
    }

}
