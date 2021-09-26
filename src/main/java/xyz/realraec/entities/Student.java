package xyz.realraec.entities;

import org.hibernate.annotations.DynamicUpdate;
import xyz.realraec.queries.DALQueries;

import javax.persistence.*;
import java.util.*;

/**
 * Student class
 */
@Table(name = "students")
@Entity(name = "Student")
@DynamicUpdate
public class Student extends Person {

    protected static final int CREDITS_PER_YEAR_COMPLETED = 60;
    protected static final int TUITION_FOR_BACHELORS_YEAR = 170;
    protected static final int TUITION_FOR_MASTERS_YEAR = 243;
    protected static final int TUITION_FOR_DOCTORATE_YEAR = 380;

    private int creditsObtained;
    private double totalTuition;
    private Degree majorDegree;
    private Degree minorDegree;
    private Set<Course> coursesCompleted = new HashSet<>();
    private String lastDiplomaObtained;

    public Student() throws Exception {
        this.lastName = "Stu";
        this.firstName = "Dent";
        this.sex = "Sex";
        this.email = "Email";
        this.phone = "Phone";
        this.majorDegree = null;
        this.minorDegree = null;
        this.lastDiplomaObtained = "";
        this.level = 1;
        calculateCreditsObtained();
        calculateTotalTuition();
        this.accountBalance -= totalTuition;
    }

    public Student(String lastName, String firstName, String sex, String email, String phone, int level,
                   Degree majorDegree, Degree minorDegree) throws Exception {
        this.lastName = lastName;
        this.firstName = firstName;
        this.sex = sex;
        this.email = email;
        this.phone = phone;
        this.warnings = 0;
        this.majorDegree = majorDegree;
        this.minorDegree = minorDegree;
        this.lastDiplomaObtained = "";
        this.level = level;
        calculateCreditsObtained();
        calculateTotalTuition();
        this.accountBalance -= totalTuition;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", nullable = false)
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

    @Column(name = "totalTuition", nullable = false)
    public double getTotalTuition() {
        return totalTuition;
    }

    public void setTotalTuition(double tuitionLeftToPay) {
        this.totalTuition = tuitionLeftToPay;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "majorDegree_id")
    public Degree getMajorDegree() {
        return majorDegree;
    }

    public void setMajorDegree(Degree majorDegree) {
        this.majorDegree = majorDegree;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "minorDegree_id")
    public Degree getMinorDegree() {
        return minorDegree;
    }

    public void setMinorDegree(Degree minorDegree) {
        this.minorDegree = minorDegree;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "coursesCompleted_set")
    public Set<Course> getCoursesCompleted() {
        return coursesCompleted;
    }

    public void setCoursesCompleted(Set<Course> coursesCompleted) {
        this.coursesCompleted = coursesCompleted;
    }

    @Column(name = "creditsObtained", nullable = false)
    public int getCreditsObtained() {
        return creditsObtained;
    }

    public void setCreditsObtained(int creditsObtained) {
        this.creditsObtained = creditsObtained;
    }

    @Column(name = "lastDiplomaObtained", nullable = false)
    public String getLastDiplomaObtained() {
        return lastDiplomaObtained;
    }

    public void setLastDiplomaObtained(String lastDiplomaObtained) {
        this.lastDiplomaObtained = lastDiplomaObtained;
    }

    @Override
    @Column(name = "level", nullable = false)
    public int getLevel() {
        return super.getLevel();
    }

    /**
     * Overriding of the setLevel(int level) method
     * to make sure the level is correct before changing it
     *
     * @param level to give to the Student object
     * @throws Exception "Invalid level for a student."
     * @see Person#setLevel(int)
     */
    @Override
    public void setLevel(int level) throws Exception {
        if ((level >= 1) && (level <= 7)) {
            super.setLevel(level);
            calculateCreditsObtained();
            calculateTotalTuition();
        } else if (level == 0) {
            super.setLevel(level);
        } else {
            throw new Exception("Invalid level for a student.");
        }
    }

    /**
     * Method to calculate the credits obtained by a Student based on their level
     *
     * @return the amount of credits obtained
     * @throws Exception "The credits could not be calculated."
     */
    public boolean calculateCreditsObtained() throws Exception {
        if (level < 1 || level > 7) {
            throw new Exception("The credits could not be calculated.");
        } else {
            switch (level) {
                case 2:
                    creditsObtained = CREDITS_PER_YEAR_COMPLETED * 1;
                    break;
                case 3:
                    creditsObtained = CREDITS_PER_YEAR_COMPLETED * 2;
                    break;
                case 4:
                    creditsObtained = CREDITS_PER_YEAR_COMPLETED * 3;
                    break;
                case 5:
                    creditsObtained = CREDITS_PER_YEAR_COMPLETED * 4;
                    break;
                case 6:
                    creditsObtained = CREDITS_PER_YEAR_COMPLETED * 5;
                    break;
                case 7:
                    creditsObtained = CREDITS_PER_YEAR_COMPLETED * 6;
                    break;
                default:
                    creditsObtained = CREDITS_PER_YEAR_COMPLETED * 0;
            }
            return true;
        }
    }

    /**
     * Method to calculate the total tuition a Student has to pay off based on their level
     *
     * @return the total tuition
     * @throws Exception "The tuition could not be calculated."
     */
    public boolean calculateTotalTuition() throws Exception {
        if (level < 1 || level > 7) {
            throw new Exception("The tuition could not be calculated.");
        } else {
            switch (level) {
                case 4:
                case 5:
                    totalTuition += TUITION_FOR_MASTERS_YEAR;
                    break;
                case 6:
                case 7:
                    totalTuition += TUITION_FOR_DOCTORATE_YEAR;
                    break;
                case 2:
                case 3:
                default:
                    totalTuition += TUITION_FOR_BACHELORS_YEAR;
            }
            return true;
        }
    }

    /**
     * Method to easily retrieve a set of all the Courses
     * the Student is enrolled in based on their two degrees
     *
     * @return a set of all the Courses the Student is enrolled in
     */
    public Set<Course> enrolledInCourses() {
        HashSet<Course> coursesSet = new HashSet<>();
        coursesSet.addAll(majorDegree.getCourses());
        coursesSet.addAll(minorDegree.getCourses());
        return coursesSet;
    }

    /**
     * Method for a Student to take an exam for a given Course
     *
     * @param course the Student takes the exam for
     * @return true if successful
     * @throws Exception "The student could not take the exam."
     */
    public boolean takeExam(Course course) throws Exception {
        if (!coursesCompleted.contains(course)
                && course.isExamMadeByProfessor()
                && enrolledInCourses().contains(course)) {
            course.setExamTakenByStudent(true);
            DALQueries.update(course);
            return true;
        } else {
            throw new Exception("The student could not take the exam.");
        }
    }


    /**
     * Method for a Student to put money on their account to pay for their tuition
     *
     * @param amount of money to put on the account
     * @return true if successful
     * @throws Exception "Could not put this amount of money on the account."
     */
    public boolean putMoneyOnAccount(double amount) throws Exception {
        System.out.println(accountBalance);
        if (accountBalance + amount <= 500) {
            accountBalance += amount;
            return true;
        } else {
            throw new Exception("Could not put this amount of money on the account.");
        }
    }


    /**
     * Overriding of the toString() method
     * to show all the attributes of the Student object instead of its unique code
     *
     * @return Student with all its attributes to String
     */
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", level=" + level +
                ", warnings=" + warnings +
                ", creditsObtained=" + creditsObtained +
                ", lastDiplomaObtained='" + lastDiplomaObtained + '\'' +
                ", totalTuition=" + totalTuition +
                //", tuitionLeftToPay=" + tuitionLeftToPay +
                ", majorDegree=" + majorDegree +
                ", minorDegree=" + minorDegree +
                ", coursesCompleted=" + coursesCompleted +
                '}';
    }

    /**
     * Overriding of the equals() method
     * to compare Student objects based on their id and nothing else
     *
     * @param o other Object to compare with this object
     * @return true if equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    /**
     * Overriding of the hashCode() method
     * to base the identity of the Student object on its id and nothing else
     *
     * @return a hash code based on the Student's id
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
