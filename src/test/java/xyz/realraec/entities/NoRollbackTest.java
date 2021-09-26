package xyz.realraec.entities;

import org.junit.Assert;
import org.junit.Test;
import xyz.realraec.queries.DALQueries;

/**
 * Tests with no rollback, effectively to be used
 * when an actual change is to be made within the database
 */
public class NoRollbackTest {

    //@Test
    public void associateDegreeAndCourseNoRollbackTest() throws Exception {
        Course course = (Course) DALQueries.get(Course.class, 7);
        Degree degree = (Degree) DALQueries.get(Degree.class, 4);

        Assert.assertTrue(Administration.associateDegreeAndCourse(degree, course));
    }

    //@Test
    public void setMajorDegreeMinorDegreeNoRollbackTest() throws Exception {
        Student student = (Student) DALQueries.get(Student.class, 2);

        Degree majorDegree = (Degree) DALQueries.get(Degree.class, 3);
        Degree minorDegree = (Degree) DALQueries.get(Degree.class, 4);

        student.setMajorDegree(majorDegree);
        student.setMinorDegree(minorDegree);

        Assert.assertTrue(DALQueries.update(student));
    }

    //@Test
    public void makeExamNoRollbackTest() throws Exception {
        Course course = (Course) DALQueries.get(Course.class, 2);
        Professor professor = (Professor) DALQueries.get(Professor.class, 2);

        Assert.assertTrue(professor.makeExam(course));
        Assert.assertTrue(course.isExamMadeByProfessor());
    }

    //@Test
    public void giveFailingGradeNoRollbackTest() throws Exception {
        Course course = (Course) DALQueries.get(Course.class, 2);
        Professor professor = (Professor) DALQueries.get(Professor.class, 2);
        Student student = (Student) DALQueries.get(Student.class, 1);

        Assert.assertTrue(professor.giveFailingGrade(student, course));
        Assert.assertFalse(course.isExamTakenByStudent());
    }

    //@Test
    public void givePassingGradeNoRollbackTest() throws Exception {
        Course course = (Course) DALQueries.get(Course.class, 2);
        Professor professor = (Professor) DALQueries.get(Professor.class, 2);
        Student student = (Student) DALQueries.get(Student.class, 1);

        Assert.assertTrue(professor.givePassingGrade(student, course));
        Assert.assertTrue(student.getCoursesCompleted().contains(course));
    }

    //@Test
    public void takeExamNoRollbackTest() throws Exception {
        Course course = (Course) DALQueries.get(Course.class, 2);
        Student student = (Student) DALQueries.get(Student.class, 1);

        Assert.assertTrue(student.takeExam(course));
        Assert.assertTrue(course.isExamTakenByStudent());
    }

    //@Test
    public void setProfessorLevelAndSalaryTest() throws Exception {
        Professor professor = (Professor) DALQueries.get(Professor.class, 2);

        professor.setLevel(2);
        DALQueries.update(professor);
    }

    //@Test
    public void setStudentLevelCreditsAndTuitionTest() throws Exception {
        Student student = (Student) DALQueries.get(Student.class, 1);
        student.setTotalTuition(0);

        student.setLevel(1);
        DALQueries.update(student);
    }

    //@Test
    public void promoteNoRollbackTest() throws Exception {
        Professor professor = (Professor) DALQueries.get(Professor.class, 2);

        Assert.assertTrue(Administration.promote(professor));
    }

    //@Test
    public void demoteNoRollbackTest() throws Exception {
        Professor professor = (Professor) DALQueries.get(Professor.class, 2);

        Assert.assertTrue(Administration.demote(professor));
    }

    //@Test
    public void giveWarningNoRollbackTest() throws Exception {
        Professor professor = (Professor) DALQueries.get(Professor.class, 3);
        Student student = (Student) DALQueries.get(Student.class, 8);

        Assert.assertTrue(Administration.giveWarning(student));
    }

    //@Test
    public void kickOutNoRollbackTest() throws Exception {
        Professor professor = (Professor) DALQueries.get(Professor.class, 2);
        Student student = (Student) DALQueries.get(Student.class, 9);

        Assert.assertTrue(Administration.kickOut(student));
    }

    //@Test
    public void giveCreditsNoRollbackTest() throws Exception {
        Student student = (Student) DALQueries.get(Student.class, 3);
        Course course = (Course) DALQueries.get(Course.class, 8);

        Assert.assertTrue(Administration.giveCredits(student, course));
        DALQueries.update(student);
    }

    //@Test
    public void saveProfessorTest() throws Exception {
        String lastName = "Doe";
        String firstName = "John";
        String sex = "Male";
        String email = "john.doe@email.com";
        String phone = "0606060606";
        int level = 1;

        Professor professor = new Professor(lastName, firstName, sex, email, phone, level);

        Assert.assertTrue(DALQueries.save(professor));
    }

    //@Test
    public void saveStudentNoRollbackTest() throws Exception {
        Degree degree1 = (Degree) DALQueries.get(Degree.class, 3);
        Degree degree2 = (Degree) DALQueries.get(Degree.class, 1);

        String lastName = "Evans";
        String firstName = "Kay";
        String sex = "Male";
        String email = "kay.evans@email.com";
        String phone = "4872569836";
        int level = 4;

        Student student = new Student(lastName, firstName, sex, email, phone, level,
                degree1, degree2);

        Assert.assertTrue(DALQueries.save(student));
    }

}
