package xyz.realraec.entities;

import org.hibernate.annotations.DynamicUpdate;
import xyz.realraec.queries.DALQueries;

import javax.persistence.*;
import java.util.*;

/**
 * Professor class
 */
@Table(name = "professors")
@Entity(name = "Professor")
@DynamicUpdate
public class Professor extends Person {

    protected static final double MINIMUM_WAGE = 1539.42;
    private double salary;

    public Professor() throws Exception {
        this.lastName = "Pro";
        this.firstName = "Fessor";
        this.sex = "Sex";
        this.email = "Email";
        this.phone = "Phone";
        this.warnings = 0;
        this.level = 1;
        calculateSalary();
    }

    public Professor(String lastName, String firstName, String sex, String email, String phone, int level) throws Exception {
        this.lastName = lastName;
        this.firstName = firstName;
        this.sex = sex;
        this.email = email;
        this.phone = phone;
        this.level = level;
        this.warnings = 0;
        calculateSalary();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    @Column(name = "lastname", nullable = false)
    public String getLastName() {
        return super.getLastName();
    }

    @Override
    @Column(name = "firstname", nullable = false)
    public String getFirstName() {
        return super.getFirstName();
    }

    @Override
    @Column(name = "sex", nullable = false)
    public String getSex() {
        return super.getSex();
    }

    @Override
    @Column(name = "email", nullable = false)
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    @Column(name = "phone", nullable = false)
    public String getPhone() {
        return super.getPhone();
    }

    @Override
    @Column(name = "warnings", nullable = false)
    public int getWarnings() {
        return super.getWarnings();
    }

    @Override
    @Column(name = "balance", nullable = false)
    public double getAccountBalance() {
        return super.getAccountBalance();
    }

    @Column(name = "salary", nullable = false)
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    @Column(name = "level", nullable = false)
    public int getLevel() {
        return super.getLevel();
    }

    /**
     * Overriding of the Person setLevel(int level) method
     * to make sure the level is correct before changing it
     *
     * @param level to give to the Professor object
     * @throws Exception "Invalid level for a professor."
     */
    @Override
    public void setLevel(int level) throws Exception {
        if ((level >= 1) && (level <= 6)) {
            super.setLevel(level);
            calculateSalary();
        } else if (level == 0) {
            super.setLevel(level);
            salary = 0;
        } else {
            throw new Exception("Invalid level for a professor.");
        }
    }


    /**
     * Method to calculate the salary a Professor is entitled to based on their level
     *
     * @return the salary
     * @throws Exception "The salary could not be calculated."
     */
    public boolean calculateSalary() throws Exception {
        if (level >= 1 && level <= 6) {
            switch (level) {
                case 2:
                    salary = MINIMUM_WAGE * 1.2;
                    break;
                case 3:
                    salary = MINIMUM_WAGE * 1.4;
                    break;
                case 4:
                    salary = MINIMUM_WAGE * 1.6;
                    break;
                case 5:
                    salary = MINIMUM_WAGE * 1.8;
                    break;
                case 6:
                    salary = MINIMUM_WAGE * 2;
                    break;
                default:
                    salary = MINIMUM_WAGE;
            }
            return true;
        } else {
            throw new Exception("The salary could not be calculated.");
        }
    }

    /**
     * Method for a professor to make an exam for a given Course
     *
     * @param course to make the exam for
     * @return true if successful
     * @throws Exception "The professor could not make the exam for this course."
     */
    public boolean makeExam(Course course) throws Exception {
        if (course.getTaughtBy().equals(this) && !course.isExamMadeByProfessor()) {
            course.setExamMadeByProfessor(true);
            DALQueries.update(course);
            return true;
        } else {
            throw new Exception("The professor could not make the exam for this course.");
        }
    }

    /**
     * Method for a Professor to give a failing grade to a Student for a given Course
     *
     * @param student to give the failing grade to
     * @param course  the Student is enrolled in
     * @return true if successful
     * @throws Exception "A failing grade could not be given to the student for this course."
     */
    public boolean giveFailingGrade(Student student, Course course) throws Exception {
        if (!student.getCoursesCompleted().contains(course)
                && student.enrolledInCourses().contains(course)
                && course.isExamMadeByProfessor()
                && course.isExamTakenByStudent()
                && course.getTaughtBy().equals(this)) {
            course.setExamTakenByStudent(false);
            DALQueries.update(course);
            return true;
        } else {
            throw new Exception("A failing grade could not be given to the student for this course.");
        }
    }

    /**
     * Method for a Professor to give a passing grade to a Student for a given Course
     *
     * @param student to give the passing grade to
     * @param course  the Student is enrolled in
     * @return true if successful
     * @throws Exception "A passing grade could not be given to the student for this course."
     */
    public boolean givePassingGrade(Student student, Course course) throws Exception {
        if (!student.getCoursesCompleted().contains(course)
                && student.enrolledInCourses().contains(course)
                && course.isExamMadeByProfessor()
                && course.isExamTakenByStudent()
                && course.getTaughtBy().equals(this)) {
            student.getCoursesCompleted().add(course);
            Administration.giveCredits(student, course);
            DALQueries.update(student);
            return true;
        } else {
            throw new Exception("A passing grade could not be given to the student for this course.");
        }
    }

    /**
     * Overriding of the toString() method
     * to show all the attributes of the Professor object instead of its unique code
     *
     * @return Professor with all its attributes to String
     */
    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", level=" + level +
                ", warnings=" + warnings +
                ", salary=" + salary +
                '}';
    }

    /**
     * Overriding of the equals() method
     * to compare Professor objects based on their id and nothing else
     *
     * @param o other Object to compare with this object
     * @return true if equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Professor professor = (Professor) o;
        return id == professor.id;
    }

    /**
     * Overriding of the hashCode() method
     * to base the identity of the Professor object on its id and nothing else
     *
     * @return a hash code based on the Professor's id
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
