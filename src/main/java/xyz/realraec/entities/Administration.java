package xyz.realraec.entities;

import org.hibernate.annotations.DynamicUpdate;
import xyz.realraec.queries.DALQueries;

import javax.persistence.*;

/**
 * Administration class
 */
@Table(name = "administration")
@Entity(name = "Administration")
@DynamicUpdate
public class Administration {

    @Transient
    private static Administration administration = null;

    private long id;
    private static double accountBalance;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "balance", nullable = false)
    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        Administration.accountBalance = accountBalance;
    }


    /**
     * Method to take money from a Student so that they can pay back their tuition
     *
     * @param student to take money from
     * @return true if successful
     * @throws Exception "Could not deduct the Student's tuition."
     */
    public static boolean deductTuition(Student student) throws Exception {
        Exception exception = new Exception("Could not deduct the Student's tuition.");

        double tuitionToDeduct = student.getTotalTuition();

        try {
            if (student.getAccountBalance() >= tuitionToDeduct) {
                student.setAccountBalance(student.getAccountBalance() - tuitionToDeduct);
                student.setTotalTuition(0);
                DALQueries.update(student);
                updateAdministrationBalanceInDatabase(tuitionToDeduct);
                return true;
            } else {
                throw exception;
            }
        } catch (Exception e) {
            throw exception;
        }
    }

    /**
     * Method to give money to a Professor as their salary
     *
     * @param professor to give money to
     * @return true if successful
     * @throws Exception "Could not pay the Professor's salary."
     */
    public static boolean paySalary(Professor professor) throws Exception {
        Exception exception = new Exception("Could not pay the Professor's salary.");

        double salaryToPay = professor.getSalary();
        Administration.administration = (Administration) DALQueries.get(Administration.class, 1);
        double administrationBalance = administration.getAccountBalance();

        try {
            if (administrationBalance >= salaryToPay) {
                professor.setAccountBalance(professor.getAccountBalance() + salaryToPay);
                DALQueries.update(professor);
                updateAdministrationBalanceInDatabase(0 - salaryToPay);
                return true;
            } else {
                throw exception;
            }
        } catch (Exception e) {
            throw exception;
        }
    }

    /**
     * Method to update the Administration object in the database,
     * an Administration object being non-instantiable
     *
     * @param amountToAddOrSubtract to the Administration object's account
     * @return true if successful
     * @throws Exception "Invalid amount of money to add or subtract."
     */
    public static boolean updateAdministrationBalanceInDatabase(double amountToAddOrSubtract) throws Exception {
        if (amountToAddOrSubtract != 0) {
            if (administration == null) {
                Administration.administration = (Administration) DALQueries.get(Administration.class, 1);
            }
            administration.setAccountBalance(administration.getAccountBalance() + amountToAddOrSubtract);
            DALQueries.update(administration);
            return true;
        } else {
            throw new Exception("Invalid amount of money to add or subtract.");
        }
    }

    /**
     * Method to promote a Person by increasing their level by 1
     *
     * @param person to promote
     * @return true if successful
     * @throws Exception "This person could not be promoted."
     */
    public static boolean promote(Person person) throws Exception {
        try {
            person.setLevel(person.level + 1);
            DALQueries.update(person);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new Exception("This person could not be promoted.");
        }
    }

    /**
     * Method to demote a Person by decreasing their level by 1
     *
     * @param person to demote
     * @return true if successful
     * @throws Exception "This person could not be demoted."
     */
    public static boolean demote(Person person) throws Exception {
        Exception exception = new Exception("This person could not be demoted.");

        if (person.level > 1) {
            try {
                person.setLevel(person.level - 1);
                DALQueries.update(person);
                return true;
            } catch (Exception e) {
                System.err.println(e.getMessage());
                throw exception;
            }
        } else {
            throw exception;
        }
    }

    /**
     * Method to give a warning to a Person and check if it is the third warning,
     * in which case it will call the kickOut() method
     *
     * @param person to give a warning to
     * @return true if successful
     * @throws Exception "This person could not be given a warning."
     */
    public static boolean giveWarning(Person person) throws Exception {
        if (person.level != 0) {
            person.warnings++;
            DALQueries.update(person);

            if (person.warnings == 3) {
                kickOut(person);
            }
            return true;
        } else {
            throw new Exception("This person could not be given a warning.");
        }
    }

    /**
     * Method to kick out a Person by setting its level to 0
     *
     * @param person to kick out
     * @return true if successful
     * @throws Exception "This person could not be kicked out."
     */
    public static boolean kickOut(Person person) throws Exception {
        if (person.level != 0) {
            person.level = 0;

            if (person instanceof Professor) {
                ((Professor) person).setSalary(0);
            }

            DALQueries.update(person);
            return true;
        } else {
            throw new Exception("This person could not be kicked out.");
        }
    }

    /**
     * Method to associate a Course and a Professor
     *
     * @param course    to be associated
     * @param professor to be associated
     * @return true if successful
     * @throws Exception "The professor and course have already been associated."
     */
    public static boolean associateCourseAndProfessor(Course course, Professor professor) throws Exception {
        if (course.getTaughtBy() == null || !course.getTaughtBy().equals(professor)) {
            course.setTaughtBy(professor);
            DALQueries.update(course);
            return true;
        } else {
            throw new Exception("The professor and course have already been associated.");
        }
    }

    /**
     * Method to dissociate a Course and a Professor
     *
     * @param course    to be dissociated
     * @param professor to be dissociated
     * @return true if successful
     * @throws Exception "The professor and course have not been associated so they can't be dissociated."
     */
    public static boolean dissociateCourseAndProfessor(Course course, Professor professor) throws Exception {
        if (course.getTaughtBy() != null && course.getTaughtBy().equals(professor)) {
            course.setTaughtBy(null);
            DALQueries.update(course);
            return true;
        } else {
            throw new Exception("The professor and course have not been associated" +
                    " so they can't be dissociated.");
        }
    }

    /**
     * Method to associate a Degree and a Course
     *
     * @param degree to be associated
     * @param course to be associated
     * @return true if successful
     * @throws Exception "The course and degree have already been associated."
     */
    public static boolean associateDegreeAndCourse(Degree degree, Course course) throws Exception {
        if (!degree.getCourses().contains(course)) {
            degree.getCourses().add(course);
            DALQueries.update(degree);
            return true;
        } else {
            throw new Exception("The course and degree have already been associated.");
        }
    }

    /**
     * Method to dissociate a Degree and a Course
     *
     * @param degree to be dissociated
     * @param course to be dissociated
     * @return true if successful
     * @throws Exception "The course and degree have not been associated so they can't be dissociated."
     */
    public static boolean dissociateDegreeAndCourse(Degree degree, Course course) throws Exception {
        if (degree.getCourses().contains(course)) {
            degree.getCourses().remove(course);
            DALQueries.update(degree);
            return true;
        } else {
            throw new Exception("The course and degree have not been associated" +
                    " so they can't be dissociated.");
        }
    }

    /**
     * Method to get the amount of credits a given Course is worth to a given Student
     *
     * @param student taking the Course (or not)
     * @param course  taken by the Student (or not)
     * @return the amount of credits if successful
     * @throws Exception "The student is not enrolled in this course."
     */
    public static int getCreditsWorthAsCourse(Student student, Course course) throws Exception {
        if (student.getMajorDegree().getCourses().contains(course)) {
            return Course.creditsBase * 4;
        } else if (student.getMinorDegree().getCourses().contains(course)) {
            return Course.creditsBase * 2;
        } else {
            throw new Exception("The student is not enrolled in this course.");
        }
    }

    /**
     * Method to get the amount of credits a given Degree is worth to a given Student
     *
     * @param student getting the credits
     * @param degree  worth the credits
     * @return the amount of credits if successful
     * @throws Exception "The student is not enrolled in this degree."
     */
    public static int getCreditsWorthAsDegree(Student student, Degree degree) throws Exception {
        if (student.getMajorDegree().equals(degree)) {
            int numberCoursesInDegree = student.getMajorDegree().getCourses().size();
            return Course.creditsBase * 4 * numberCoursesInDegree;
        } else if (student.getMinorDegree().equals(degree)) {
            int numberCoursesInDegree = student.getMinorDegree().getCourses().size();
            return Course.creditsBase * 2 * numberCoursesInDegree;
        } else {
            throw new Exception("The student is not enrolled in this degree.");
        }
    }

    /**
     * Method to give credits to a Student after they have completed a Course
     *
     * @param student to be given the credits
     * @param course  to give the credits for
     * @return true if successful
     * @throws Exception "Credits could not be given to the Student for this Course."
     */
    public static boolean giveCredits(Student student, Course course) throws Exception {
        try {
            int creditsToBeGiven = getCreditsWorthAsCourse(student, course);
            if (student.getCoursesCompleted().contains(course)) {
                student.setCreditsObtained(student.getCreditsObtained() + creditsToBeGiven);
                return true;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        throw new Exception("Credits could not be given to the Student for this Course.");
    }

    /**
     * Method to give a Student a diploma
     *
     * @param student to give the diploma to
     * @return true if successful
     * @throws Exception "Could not give the Student a diploma."
     */
    public static boolean giveDiploma(Student student) throws Exception {
        Exception exception = new Exception("Could not give the Student a diploma.");
        System.out.println(student.enrolledInCourses());
        System.out.println(student.getCoursesCompleted());

        if (student.enrolledInCourses().size() == student.getCoursesCompleted().size()
                && student.enrolledInCourses().containsAll(student.getCoursesCompleted())
                && student.getCoursesCompleted().containsAll(student.enrolledInCourses())) {
            if (student.level == 3 || student.level == 4) {
                student.setLastDiplomaObtained("Bachelor's");
                DALQueries.update(student);
                return true;
            } else if (student.level == 5 || student.level == 6) {
                student.setLastDiplomaObtained("Master's");
                DALQueries.update(student);
                return true;
            } else if (student.level == 7) {
                student.setLastDiplomaObtained("Doctorate");
                DALQueries.update(student);
                return true;
            } else {
                student.setLastDiplomaObtained("");
                DALQueries.update(student);
                throw exception;
            }
        } else {
            throw exception;
        }
    }

}
