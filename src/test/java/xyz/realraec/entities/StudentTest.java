package xyz.realraec.entities;

import org.junit.Assert;
import org.junit.Test;
import xyz.realraec.queries.DALQueries;

import java.util.HashSet;

/**
 * Tests for the Student object
 *
 * @see Student
 */
public class StudentTest {

    private final double DELTA_CONSTANT = 1e-15;

    /**
     * Test to check that the default Student constructor works,
     * together with other setters and getters
     *
     * @throws Exception if wrong level
     * @see Student#Student()
     */
    @Test
    public void newStudentDefaultConstructorTest() throws Exception {
        long id = 1;
        String lastName = "Doe";
        String firstName = "John";
        String sex = "Male";
        String email = "john.doe@email.com";
        String phone = "0606060606";
        Degree majorDegree = new Degree();
        Degree minorDegree = new Degree();
        int level = 3;
        int warnings = 0;
        double totalTuition = Student.TUITION_FOR_BACHELORS_YEAR;
        int creditsObtained = Student.CREDITS_PER_YEAR_COMPLETED * 2;
        String lastDiplomaObtained = "";
        HashSet<Course> coursesCompleted = new HashSet<>();

        Student student = new Student();
        student.setId(id);
        student.setLastName(lastName);
        student.setFirstName(firstName);
        student.setSex(sex);
        student.setEmail(email);
        student.setPhone(phone);
        student.setMajorDegree(majorDegree);
        student.setMinorDegree(minorDegree);
        student.setWarnings(warnings);
        student.setLevel(level);
        student.setLastDiplomaObtained(lastDiplomaObtained);
        student.setCoursesCompleted(coursesCompleted);
        student.setTotalTuition(totalTuition);
        student.setCreditsObtained(creditsObtained);

        Assert.assertEquals(student.getId(), id);
        Assert.assertEquals(student.getLastName(), lastName);
        Assert.assertEquals(student.getFirstName(), firstName);
        Assert.assertEquals(student.getSex(), sex);
        Assert.assertEquals(student.getEmail(), email);
        Assert.assertEquals(student.getPhone(), phone);
        Assert.assertEquals(student.getMajorDegree(), majorDegree);
        Assert.assertEquals(student.getMinorDegree(), minorDegree);
        Assert.assertEquals(student.getWarnings(), warnings);
        Assert.assertEquals(student.getLevel(), level);
        Assert.assertEquals(student.getTotalTuition(), totalTuition, 1e-15);
        Assert.assertEquals(student.getCreditsObtained(), creditsObtained);
        Assert.assertEquals(student.getLastDiplomaObtained(), lastDiplomaObtained);
        Assert.assertNotNull(student.getCoursesCompleted());
    }

    /**
     * Test to check that the non-default Student constructor works
     *
     * @throws Exception if wrong level
     * @see Student#Student(String, String, String, String, String, int, Degree, Degree)
     */
    @Test
    public void newStudentNonDefaultConstructorTest() throws Exception {
        String lastName = "Doe";
        String firstName = "John";
        String sex = "Male";
        String email = "john.doe@email.com";
        String phone = "0606060606";
        int level = 3;
        Degree majorDegree = new Degree();
        Degree minorDegree = new Degree();

        Student student = new Student(lastName, firstName, sex, email, phone, level,
                majorDegree, minorDegree);

        Assert.assertEquals(student.getLastName(), lastName);
        Assert.assertEquals(student.getFirstName(), firstName);
        Assert.assertEquals(student.getSex(), sex);
        Assert.assertEquals(student.getEmail(), email);
        Assert.assertEquals(student.getPhone(), phone);
        Assert.assertEquals(student.getLevel(), level);
        Assert.assertEquals(student.getMajorDegree(), majorDegree);
        Assert.assertEquals(student.getMinorDegree(), minorDegree);
    }

    /**
     * Test to check that the toString(), equals(), and hashCode() methods
     * have been overridden properly and can be used to compare Student objects
     *
     * @throws Exception if getting from database fails
     * @see Student#toString()
     * @see Student#equals(Object)
     * @see Student#hashCode()
     */
    @Test
    public void toStringEqualsHashCodeCourseTest() throws Exception {
        Student course1 = (Student) DALQueries.get(Student.class, 1);
        Student course2 = (Student) DALQueries.get(Student.class, 2);

        Assert.assertNotEquals(course1, course2);
        Assert.assertNotEquals(course1.hashCode(), course2.hashCode());
        Assert.assertNotEquals(course1.toString(), course2.toString());
    }

    /**
     * Test to check the switch statements to be triggered
     * when setting the level of a Student: successful
     *
     * @throws Exception if getting from database fails
     * @see Student#setLevel(int)
     * @see Student#calculateCreditsObtained()
     * @see Student#calculateTotalTuition()
     */
    @Test
    public void newStudentLevelCreditsAndTuitionTest() throws Exception {
        Student studentValidLevelYES = (Student) DALQueries.get(Student.class, 1);
        studentValidLevelYES.setTotalTuition(0);

        studentValidLevelYES.setLevel(1);
        Assert.assertEquals(studentValidLevelYES.getLevel(), 1);
        Assert.assertEquals(studentValidLevelYES.getCreditsObtained(),
                Student.CREDITS_PER_YEAR_COMPLETED * 0);
        Assert.assertEquals(studentValidLevelYES.getTotalTuition(),
                Student.TUITION_FOR_BACHELORS_YEAR * 1, DELTA_CONSTANT);

        studentValidLevelYES.setLevel(2);
        Assert.assertEquals(studentValidLevelYES.getLevel(), 2);
        Assert.assertEquals(studentValidLevelYES.getCreditsObtained(),
                Student.CREDITS_PER_YEAR_COMPLETED * 1);
        Assert.assertEquals(studentValidLevelYES.getTotalTuition(),
                Student.TUITION_FOR_BACHELORS_YEAR * 2, DELTA_CONSTANT);

        studentValidLevelYES.setLevel(3);
        Assert.assertEquals(studentValidLevelYES.getLevel(), 3);
        Assert.assertEquals(studentValidLevelYES.getCreditsObtained(),
                Student.CREDITS_PER_YEAR_COMPLETED * 2);
        Assert.assertEquals(studentValidLevelYES.getTotalTuition(),
                Student.TUITION_FOR_BACHELORS_YEAR * 3, DELTA_CONSTANT);

        studentValidLevelYES.setLevel(4);
        Assert.assertEquals(studentValidLevelYES.getLevel(), 4);
        Assert.assertEquals(studentValidLevelYES.getCreditsObtained(),
                Student.CREDITS_PER_YEAR_COMPLETED * 3);
        Assert.assertEquals(studentValidLevelYES.getTotalTuition(),
                (Student.TUITION_FOR_BACHELORS_YEAR * 3)
                        + (Student.TUITION_FOR_MASTERS_YEAR * 1), DELTA_CONSTANT);

        studentValidLevelYES.setLevel(5);
        Assert.assertEquals(studentValidLevelYES.getLevel(), 5);
        Assert.assertEquals(studentValidLevelYES.getCreditsObtained(),
                Student.CREDITS_PER_YEAR_COMPLETED * 4);
        Assert.assertEquals(studentValidLevelYES.getTotalTuition(),
                (Student.TUITION_FOR_BACHELORS_YEAR * 3)
                        + (Student.TUITION_FOR_MASTERS_YEAR * 2), DELTA_CONSTANT);

        studentValidLevelYES.setLevel(6);
        Assert.assertEquals(studentValidLevelYES.getLevel(), 6);
        Assert.assertEquals(studentValidLevelYES.getCreditsObtained(),
                Student.CREDITS_PER_YEAR_COMPLETED * 5);
        Assert.assertEquals(studentValidLevelYES.getTotalTuition(),
                (Student.TUITION_FOR_BACHELORS_YEAR * 3)
                        + (Student.TUITION_FOR_MASTERS_YEAR * 2)
                        + (Student.TUITION_FOR_DOCTORATE_YEAR * 1), DELTA_CONSTANT);

        studentValidLevelYES.setLevel(7);
        Assert.assertEquals(studentValidLevelYES.getLevel(), 7);
        Assert.assertEquals(studentValidLevelYES.getCreditsObtained(),
                Student.CREDITS_PER_YEAR_COMPLETED * 6);
        Assert.assertEquals(studentValidLevelYES.getTotalTuition(),
                (Student.TUITION_FOR_BACHELORS_YEAR * 3)
                        + (Student.TUITION_FOR_MASTERS_YEAR * 2)
                        + (Student.TUITION_FOR_DOCTORATE_YEAR * 2), DELTA_CONSTANT);
    }

    /**
     * Test to check the switch statements to be triggered
     * when setting the level of a Student: unsuccessful
     *
     * @throws Exception if getting from database fails
     * @see Student#setLevel(int)
     * @see Student#calculateCreditsObtained()
     * @see Student#calculateTotalTuition()
     */
    @Test
    public void newStudentLevelCreditsAndTuitionErrorConditionTest() throws Exception {
        Student studentValidLevelNO = (Student) DALQueries.get(Student.class, 1);
        try {
            studentValidLevelNO.setLevel(10);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "Invalid level for a student.");
        }
        Assert.assertNotEquals(studentValidLevelNO.level, 10);

        studentValidLevelNO.level = 10;
        try {
            Assert.assertFalse(studentValidLevelNO.calculateCreditsObtained());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "The credits could not be calculated.");
        }
        try {
            Assert.assertFalse(studentValidLevelNO.calculateTotalTuition());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "The tuition could not be calculated.");
        }
    }

    /**
     * Test to check if it is possible for a Student enrolled a Course
     * with an exam that they have not taken yet to take an exam for it: successful
     *
     * @throws Exception if getting from database fails
     * @see Student#takeExam(Course)
     */
    @Test
    public void takeExamTest() throws Exception {
        Student student = (Student) DALQueries.get(Student.class, 2);
        Course courseTakenByStudentYESExamMadeNO = (Course) DALQueries.get(Course.class, 7);

        Assert.assertTrue(student.takeExam(courseTakenByStudentYESExamMadeNO));
        Assert.assertTrue(courseTakenByStudentYESExamMadeNO.isExamTakenByStudent());

        courseTakenByStudentYESExamMadeNO.setExamTakenByStudent(false);
        DALQueries.update(courseTakenByStudentYESExamMadeNO);
    }

    /**
     * Test to check if it is possible for a Student enrolled a Course
     * with an exam that they have not taken yet to take an exam for it: unsuccessful
     *
     * @throws Exception if getting from database fails
     * @see Student#takeExam(Course)
     */
    @Test
    public void takeExamErrorConditionTest() throws Exception {
        Student student = (Student) DALQueries.get(Student.class, 1);
        Course courseTakenByStudentYESExamMadeNO = (Course) DALQueries.get(Course.class, 1);

        try {
            Assert.assertFalse(student.takeExam(courseTakenByStudentYESExamMadeNO));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "The student could not take the exam.");
        }
    }

    /**
     * Test to check if it is possible for a Student to put money on their account: successful
     *
     * @throws Exception if getting from database fails
     * @see Student#putMoneyOnAccount(double)
     */
    @Test
    public void putMoneyOnAccountTest() throws Exception {
        Student student = (Student) DALQueries.get(Student.class, 1);
        double balanceBefore = student.getAccountBalance();
        double validAmount = 200;

        Assert.assertTrue(student.putMoneyOnAccount(validAmount));
        Assert.assertEquals(student.getAccountBalance(), balanceBefore + validAmount, DELTA_CONSTANT);
        student.accountBalance = balanceBefore;
        DALQueries.update(student);
    }

    /**
     * Test to check if it is possible for a Student to put money on their account: unsuccessful
     *
     * @throws Exception if getting from database fails
     * @see Student#putMoneyOnAccount(double)
     */
    @Test
    public void putMoneyOnAccountErrorConditionTest() throws Exception {
        Student student = (Student) DALQueries.get(Student.class, 1);
        double invalidAmount = 5000;

        try {
            Assert.assertTrue(student.putMoneyOnAccount(invalidAmount));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "Could not put this amount of money on the account.");
        }
    }

}
