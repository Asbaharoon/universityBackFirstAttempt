package xyz.realraec.entities;

import org.junit.Assert;
import org.junit.Test;
import xyz.realraec.queries.DALQueries;

/**
 * Tests for the Administration class: methods relative to a Person
 *
 * @see Administration
 */
public class AdministrationPersonTest {

    private final double DELTA_CONSTANT = 1e-15;

    /**
     * Test to check if it is possible to promote a Person: successful
     *
     * @throws Exception if getting from database fails
     * @see Administration#promote(Person)
     */
    @Test
    public void promoteTest() throws Exception {
        Professor professorMaxLevelNO = (Professor) DALQueries.get(Professor.class, 1);

        Assert.assertTrue(Administration.promote(professorMaxLevelNO));
        Assert.assertEquals(professorMaxLevelNO.getLevel(), 2);
        Administration.demote(professorMaxLevelNO);
    }

    /**
     * Test to check if it is possible to promote a Person: unsuccessful
     *
     * @throws Exception if getting from database fails
     * @see Administration#promote(Person)
     */
    @Test
    public void promoteErrorConditionTest() throws Exception {
        Professor professorMaxLevelYES = (Professor) DALQueries.get(Professor.class, 6);

        try {
            Assert.assertTrue(Administration.promote(professorMaxLevelYES));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "This person could not be promoted.");
        }
    }

    /**
     * Test to check if it is possible to demote a Person: successful
     *
     * @throws Exception if getting from database fails
     * @see Administration#demote(Person)
     */
    @Test
    public void demoteTest() throws Exception {
        Student studentMinLevelNO = (Student) DALQueries.get(Student.class, 7);

        Assert.assertTrue(Administration.demote(studentMinLevelNO));
        Assert.assertEquals(studentMinLevelNO.getLevel(), 6);

        studentMinLevelNO.setTotalTuition(0L);
        Administration.promote(studentMinLevelNO);
    }

    /**
     * Test to check if it is possible to demote a Person: unsuccessful
     *
     * @throws Exception if getting from database fails
     * @see Administration#demote(Person)
     */
    @Test
    public void demoteErrorConditionTest() throws Exception {
        Student studentInvalidLevel = (Student) DALQueries.get(Student.class, 1);

        try {
            Assert.assertFalse(Administration.demote(studentInvalidLevel));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "This person could not be demoted.");
        }

        studentInvalidLevel.level = 10;
        try {
            Assert.assertFalse(Administration.demote(studentInvalidLevel));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "This person could not be demoted.");
        }
    }

    /**
     * Test to check if it is possible to give a warning to a Person: successful
     *
     * @throws Exception if getting from database fails
     * @see Administration#giveWarning(Person)
     */
    @Test
    public void giveWarningTest() throws Exception {
        Professor professorKickedOutNO = (Professor) DALQueries.get(Professor.class, 3);

        Assert.assertTrue(Administration.giveWarning(professorKickedOutNO));
        Assert.assertEquals(professorKickedOutNO.getWarnings(), 2);

        professorKickedOutNO.setWarnings(1);
        DALQueries.update(professorKickedOutNO);
    }

    /**
     * Test to check if it is possible to give a third warning to a Person
     * resulting in kicking them out: successful
     *
     * @throws Exception if getting from database fails
     * @see Administration#giveWarning(Person)
     */
    @Test
    public void giveWarningAndKickOutTest() throws Exception {
        Professor professorKickedOutNO = (Professor) DALQueries.get(Professor.class, 7);

        Assert.assertTrue(Administration.giveWarning(professorKickedOutNO));
        Assert.assertEquals(professorKickedOutNO.getWarnings(), 3);
        Assert.assertEquals(professorKickedOutNO.getLevel(), 0);
        Assert.assertEquals(professorKickedOutNO.getSalary(), 0, DELTA_CONSTANT);

        professorKickedOutNO.setWarnings(2);
        professorKickedOutNO.setLevel(3);
        DALQueries.update(professorKickedOutNO);
    }

    /**
     * Test to check if it is possible to give a warning to a Person: unsuccessful
     *
     * @throws Exception if getting from database fails
     * @see Administration#giveWarning(Person)
     */
    @Test
    public void giveWarningErrorConditionTest() throws Exception {
        Professor professorKickedOutYES = (Professor) DALQueries.get(Professor.class, 8);

        try {
            Assert.assertFalse(Administration.giveWarning(professorKickedOutYES));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "This person could not be given a warning.");
        }
    }

    /**
     * Test to check if it is possible to kick a Person out: successful
     *
     * @throws Exception if getting from database fails
     * @see Administration#kickOut(Person)
     */
    @Test
    public void kickOutTest() throws Exception {
        Professor professorKickedOutNO = (Professor) DALQueries.get(Professor.class, 2);

        Assert.assertTrue(Administration.kickOut(professorKickedOutNO));
        Assert.assertEquals(professorKickedOutNO.getLevel(), 0);
        Assert.assertEquals(professorKickedOutNO.getSalary(), 0, DELTA_CONSTANT);

        professorKickedOutNO.setLevel(2);
        DALQueries.update(professorKickedOutNO);
    }

    /**
     * Test to check if it is possible to kick a Person out: unsuccessful
     *
     * @throws Exception if getting from database fails
     * @see Administration#kickOut(Person)
     */
    @Test
    public void kickOutErrorConditionTest() throws Exception {
        Professor professorKickedOutYES = (Professor) DALQueries.get(Professor.class, 8);

        try {
            Assert.assertFalse(Administration.kickOut(professorKickedOutYES));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "This person could not be kicked out.");
        }
    }

}
