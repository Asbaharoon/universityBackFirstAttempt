package xyz.realraec.entities;

import org.junit.Assert;
import org.junit.Test;
import xyz.realraec.queries.DALQueries;

/**
 * Tests for the Professor object
 *
 * @see Professor
 */
public class ProfessorTest {

    private final double DELTA_CONSTANT = 1e-15;

    /**
     * Test to check that the default Professor constructor works,
     * together with other setters and getters
     *
     * @throws Exception if wrong level
     * @see Professor#Professor()
     */
    @Test
    public void newProfessorDefaultConstructorTest() throws Exception {
        int id = 1;
        String lastName = "Doe";
        String firstName = "John";
        String sex = "Male";
        String email = "john.doe@email.com";
        String phone = "0606060606";
        int warnings = 0;
        int level = 3;
        double salary = Professor.MINIMUM_WAGE * 1.4;

        Professor professor = new Professor();
        professor.setId(id);
        professor.setLastName(lastName);
        professor.setFirstName(firstName);
        professor.setSex(sex);
        professor.setEmail(email);
        professor.setPhone(phone);
        professor.setWarnings(warnings);
        professor.setLevel(level);
        professor.setSalary(salary);

        Assert.assertEquals(professor.getId(), id);
        Assert.assertEquals(professor.getLastName(), lastName);
        Assert.assertEquals(professor.getFirstName(), firstName);
        Assert.assertEquals(professor.getSex(), sex);
        Assert.assertEquals(professor.getEmail(), email);
        Assert.assertEquals(professor.getPhone(), phone);
        Assert.assertEquals(professor.getWarnings(), warnings);
        Assert.assertEquals(professor.getLevel(), level);
        Assert.assertEquals(professor.getSalary(), salary, DELTA_CONSTANT);
    }

    /**
     * Test to check that the non-default Professor constructor works
     *
     * @throws Exception if wrong level
     * @see Professor#Professor(String, String, String, String, String, int)
     */
    @Test
    public void newProfessorNonDefaultConstructorTest() throws Exception {
        String lastName = "Doe";
        String firstName = "John";
        String sex = "Male";
        String email = "john.doe@email.com";
        String phone = "0606060606";
        int level = 3;

        Professor professor = new Professor(lastName, firstName, sex, email, phone, level);

        Assert.assertEquals(professor.getLastName(), lastName);
        Assert.assertEquals(professor.getFirstName(), firstName);
        Assert.assertEquals(professor.getSex(), sex);
        Assert.assertEquals(professor.getEmail(), email);
        Assert.assertEquals(professor.getPhone(), phone);
        Assert.assertEquals(professor.getLevel(), level);
    }

    /**
     * Test to check that the toString(), equals(), and hashCode() methods
     * have been overridden properly and can be used to compare Professor objects
     *
     * @throws Exception if getting from database fails
     * @see Professor#toString()
     * @see Professor#equals(Object)
     * @see Professor#hashCode()
     */
    @Test
    public void toStringEqualsHashCodeCourseTest() throws Exception {
        Professor professor1 = (Professor) DALQueries.get(Professor.class, 1);
        Professor professor2 = (Professor) DALQueries.get(Professor.class, 2);

        Assert.assertNotEquals(professor1, professor2);
        Assert.assertNotEquals(professor1.hashCode(), professor2.hashCode());
        Assert.assertNotEquals(professor1.toString(), professor2.toString());
    }

    /**
     * Test to check the switch statements to be triggered
     * when setting the level of a Professor: successful
     *
     * @throws Exception if getting from database fails
     * @see Professor#setLevel(int)
     * @see Professor#calculateSalary()
     */
    @Test
    public void setProfessorLevelAndSalaryTest() throws Exception {
        Professor professorValidLevelYES = (Professor) DALQueries.get(Professor.class, 1);

        professorValidLevelYES.setLevel(1);
        Assert.assertEquals(professorValidLevelYES.getLevel(), 1);
        Assert.assertEquals(professorValidLevelYES.getSalary(),
                Professor.MINIMUM_WAGE * 1, DELTA_CONSTANT);

        professorValidLevelYES.setLevel(2);
        Assert.assertEquals(professorValidLevelYES.getLevel(), 2);
        Assert.assertEquals(professorValidLevelYES.getSalary(),
                Professor.MINIMUM_WAGE * 1.2, DELTA_CONSTANT);

        professorValidLevelYES.setLevel(3);
        Assert.assertEquals(professorValidLevelYES.getLevel(), 3);
        Assert.assertEquals(professorValidLevelYES.getSalary(),
                Professor.MINIMUM_WAGE * 1.4, DELTA_CONSTANT);

        professorValidLevelYES.setLevel(4);
        Assert.assertEquals(professorValidLevelYES.getLevel(), 4);
        Assert.assertEquals(professorValidLevelYES.getSalary(),
                Professor.MINIMUM_WAGE * 1.6, DELTA_CONSTANT);

        professorValidLevelYES.setLevel(5);
        Assert.assertEquals(professorValidLevelYES.getLevel(), 5);
        Assert.assertEquals(professorValidLevelYES.getSalary(),
                Professor.MINIMUM_WAGE * 1.8, DELTA_CONSTANT);

        professorValidLevelYES.setLevel(6);
        Assert.assertEquals(professorValidLevelYES.getLevel(), 6);
        Assert.assertEquals(professorValidLevelYES.getSalary(),
                Professor.MINIMUM_WAGE * 2, DELTA_CONSTANT);
    }

    /**
     * Test to check the switch statements to be triggered
     * when setting the level of a Student: unsuccessful
     *
     * @throws Exception if getting from database fails
     * @see Professor#setLevel(int)
     * @see Professor#calculateSalary()
     */
    @Test
    public void setProfessorLevelAndSalaryErrorConditionTest() throws Exception {
        Professor professorValidLevelNO = (Professor) DALQueries.get(Professor.class, 1);

        try {
            professorValidLevelNO.setLevel(10);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "Invalid level for a professor.");
        }

        professorValidLevelNO.level = 10;
        try {
            Assert.assertFalse(professorValidLevelNO.calculateSalary());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "The salary could not be calculated.");
        }
    }


    /**
     * Test to check that it is possible for a Professor teaching a Course
     * with no exam already made to make an exam for it: successful
     *
     * @throws Exception if getting from database fails
     * @see Professor#makeExam(Course)
     */
    @Test
    public void makeExamTest() throws Exception {
        Professor professor = (Professor) DALQueries.get(Professor.class, 3);
        Course courseTaughtByProfessorYESExamMadeNO = (Course) DALQueries.get(Course.class, 3);

        Assert.assertTrue(professor.makeExam(courseTaughtByProfessorYESExamMadeNO));
        Assert.assertTrue(courseTaughtByProfessorYESExamMadeNO.isExamMadeByProfessor());

        courseTaughtByProfessorYESExamMadeNO.setExamMadeByProfessor(false);
        DALQueries.update(courseTaughtByProfessorYESExamMadeNO);
    }

    /**
     * Test to check that it is possible for a Professor teaching a Course
     * with no exam already made to make an exam for it: unsuccessful
     *
     * @throws Exception if getting from database fails
     * @see Professor#makeExam(Course)
     */
    @Test
    public void makeExamErrorConditionTest() throws Exception {
        Professor professor = (Professor) DALQueries.get(Professor.class, 3);
        Course courseTaughtByProfessorNO = (Course) DALQueries.get(Course.class, 2);

        try {
            Assert.assertFalse(professor.makeExam(courseTaughtByProfessorNO));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(), "The professor could not make the exam for this course.");
        }
    }

    /**
     * Test to check that it is possible for a Professor to give a failing grade
     * to a Student if the exam has been made, the exam has been taken,
     * the Professor teaches the Course, and the Student is enrolled in the Course:
     * successful
     *
     * @throws Exception if getting from database fails
     * @see Professor#giveFailingGrade(Student, Course)
     */
    @Test
    public void giveFailingGradeTest() throws Exception {
        Professor professor = (Professor) DALQueries.get(Professor.class, 2);
        Student student = (Student) DALQueries.get(Student.class, 1);
        Course courseTaughtByProfessorYESTakenByStudentYESExamMadeYESExamTakenYES = (Course) DALQueries.get(Course.class, 2);

        Assert.assertTrue(professor.giveFailingGrade(student, courseTaughtByProfessorYESTakenByStudentYESExamMadeYESExamTakenYES));
        Assert.assertFalse(courseTaughtByProfessorYESTakenByStudentYESExamMadeYESExamTakenYES.isExamTakenByStudent());

        courseTaughtByProfessorYESTakenByStudentYESExamMadeYESExamTakenYES.setExamTakenByStudent(true);
        DALQueries.update(courseTaughtByProfessorYESTakenByStudentYESExamMadeYESExamTakenYES);
    }

    /**
     * Test to check that it is possible for a Professor to give a failing grade
     * to a Student if the exam has been made, the exam has been taken,
     * the Professor teaches the Course, and the Student is enrolled in the Course:
     * unsuccessful
     *
     * @throws Exception if getting from database fails
     * @see Professor#giveFailingGrade(Student, Course)
     */
    @Test
    public void giveFailingGradeErrorConditionTest() throws Exception {
        Professor professor = (Professor) DALQueries.get(Professor.class, 3);
        Student student = (Student) DALQueries.get(Student.class, 2);
        Course courseTaughtByProfessorYESTakenByStudentNO = (Course) DALQueries.get(Course.class, 3);

        try {
            Assert.assertFalse(professor.giveFailingGrade(student, courseTaughtByProfessorYESTakenByStudentNO));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(),
                    "A failing grade could not be given to the student for this course.");
        }
    }


    /**
     * Test to check that it is possible for a Professor to give a passing grade
     * to a Student if the exam has been made, the exam has been taken,
     * the Professor teaches the Course, and the Student is enrolled in the Course:
     * successful
     *
     * @throws Exception if getting from database fails
     * @see Professor#givePassingGrade(Student, Course)
     */
    @Test
    public void givePassingGradeTest() throws Exception {
        Professor professor = (Professor) DALQueries.get(Professor.class, 5);
        Student student = (Student) DALQueries.get(Student.class, 5);
        Course courseTaughtByProfessorYESTakenByStudentYESExamMadeYESExamTakenYES = (Course) DALQueries.get(Course.class, 6);

        Assert.assertTrue(professor.givePassingGrade(student, courseTaughtByProfessorYESTakenByStudentYESExamMadeYESExamTakenYES));
        Assert.assertTrue(student.getCoursesCompleted().contains(courseTaughtByProfessorYESTakenByStudentYESExamMadeYESExamTakenYES));

        student.setCreditsObtained(student.getCreditsObtained() - (Course.creditsBase * 4));
        student.getCoursesCompleted().remove(courseTaughtByProfessorYESTakenByStudentYESExamMadeYESExamTakenYES);
        DALQueries.update(student);
    }

    /**
     * Test to check that it is possible for a Professor to give a failing grade
     * to a Student if the exam has been made, the exam has been taken,
     * the Professor teaches the Course, and the Student is enrolled in the Course:
     * successful
     *
     * @throws Exception if getting from database fails
     * @see Professor#givePassingGrade(Student, Course)
     */
    @Test
    public void givePassingGradeErrorConditionTest() throws Exception {
        Professor professor = (Professor) DALQueries.get(Professor.class, 3);
        Student student = (Student) DALQueries.get(Student.class, 2);
        Course courseTaughtByProfessorYESTakenByStudentNO = (Course) DALQueries.get(Course.class, 3);

        try {
            Assert.assertTrue(professor.givePassingGrade(student, courseTaughtByProfessorYESTakenByStudentNO));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertEquals(e.getMessage(),
                    "A passing grade could not be given to the student for this course.");
        }
    }

}
