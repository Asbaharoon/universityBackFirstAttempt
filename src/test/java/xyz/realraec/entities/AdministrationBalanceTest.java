package xyz.realraec.entities;

import org.junit.Assert;
import org.junit.Test;
import xyz.realraec.queries.DALQueries;

/**
 * Tests for the Administration class: methods relative to paying and getting paid
 *
 * @see Administration
 */
public class AdministrationBalanceTest {

    private final double DELTA_CONSTANT = 1e-15;

    /**
     * Method to check if it is possible to update the Administration object
     * and its balance value in the database: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void updateAdministrationBalanceInDatabaseTest() throws Exception {
        double validAmount = 50;
        Administration administration = (Administration) DALQueries.get(Administration.class, 1);
        double balanceBefore = administration.getAccountBalance();

        Assert.assertTrue(Administration.updateAdministrationBalanceInDatabase(validAmount));
        Assert.assertEquals(administration.getAccountBalance(), balanceBefore + validAmount, DELTA_CONSTANT);

        Administration.updateAdministrationBalanceInDatabase(0 - validAmount);
    }

    /**
     * Method to check if it is possible to update the Administration object
     * and its balance value in the database: unsuccessful
     */
    @Test
    public void updateAdministrationBalanceInDatabaseErrorConditionTest() {
        double invalidAmount = 0;

        try {
            Assert.assertFalse(Administration.updateAdministrationBalanceInDatabase(invalidAmount));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "Invalid amount of money to add or subtract.");
        }
    }

    /**
     * Method to check if it is possible for the Administration to pay a Professor's salary:
     * successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void paySalaryTest() throws Exception {
        Professor professor = (Professor) DALQueries.get(Professor.class, 2);
        double professorBalanceBefore = professor.getAccountBalance();
        Administration administration = (Administration) DALQueries.get(Administration.class, 1);
        double administrationBalanceBefore = administration.getAccountBalance();

        Assert.assertTrue(Administration.paySalary(professor));
        Assert.assertEquals(professor.getAccountBalance(), professorBalanceBefore + professor.getSalary(), DELTA_CONSTANT);
        Assert.assertEquals(administration.getAccountBalance(), administrationBalanceBefore - professor.getSalary(), DELTA_CONSTANT);

        professor.setAccountBalance(professorBalanceBefore);
        DALQueries.update(professor);
        Administration.updateAdministrationBalanceInDatabase(professor.getSalary());
    }

    /**
     * Method to check if it is possible for the Administration to pay a Professor's salary:
     * unsuccessful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void paySalaryErrorConditionTest() throws Exception {
        Professor professor = (Professor) DALQueries.get(Professor.class, 2);
        Administration administration = (Administration) DALQueries.get(Administration.class, 1);
        double balanceBefore = administration.getAccountBalance();
        administration.setAccountBalance(5);
        DALQueries.update(administration);

        try {
            Assert.assertFalse(Administration.paySalary(professor));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "Could not pay the Professor's salary.");
        } finally {
            administration.setAccountBalance(balanceBefore);
            DALQueries.update(administration);
        }
    }

    /**
     * Method to check if it is possible for the Administration to take money from a Student's
     * account for them to pay for their tuition: successful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void deductTuitionTest() throws Exception {
        Student student = (Student) DALQueries.get(Student.class, 8);
        double studentBalanceBefore = student.getAccountBalance();
        double tuitionBefore = student.getTotalTuition();
        Administration administration = (Administration) DALQueries.get(Administration.class, 1);
        double administrationBalanceBefore = administration.getAccountBalance();

        Assert.assertTrue(Administration.deductTuition(student));
        Assert.assertEquals(student.getAccountBalance(), studentBalanceBefore - tuitionBefore, DELTA_CONSTANT);
        Assert.assertEquals(administration.getAccountBalance(), administrationBalanceBefore + tuitionBefore, DELTA_CONSTANT);

        student.setAccountBalance(studentBalanceBefore);
        student.setTotalTuition(tuitionBefore);
        DALQueries.update(student);
        Administration.updateAdministrationBalanceInDatabase(0 - tuitionBefore);
    }

    /**
     * Method to check if it is possible for the Administration to take money from a Student's
     * account for them to pay for their tuition: unsuccessful
     *
     * @throws Exception if getting from database fails
     */
    @Test
    public void deductTuitionErrorConditionTest() throws Exception {
        Student student = (Student) DALQueries.get(Student.class, 1);

        try {
            Assert.assertFalse(Administration.deductTuition(student));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "Could not deduct the Student's tuition.");
        }
    }

}
