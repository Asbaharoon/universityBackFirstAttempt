package xyz.realraec.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Objects;

/**
 * Course class
 */
@Table(name = "courses")
@Entity(name = "Course")
@DynamicUpdate
public class Course {

    private long id;
    private String name;
    private boolean examMadeByProfessor = false;
    private boolean examTakenByStudent = false;
    private Professor taughtBy;
    public static int creditsBase = 5;

    public Course() {
        this.name = "Course";
    }

    public Course(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @OneToOne(fetch = FetchType.EAGER)
    public Professor getTaughtBy() {
        return taughtBy;
    }

    public void setTaughtBy(Professor taughtBy) {
        this.taughtBy = taughtBy;
    }

    @Column(name = "isExamMadeByProfessor", nullable = false)
    public boolean isExamMadeByProfessor() {
        return examMadeByProfessor;
    }

    public void setExamMadeByProfessor(boolean hasExamMade) {
        this.examMadeByProfessor = hasExamMade;
    }

    @Column(name = "isExamTakenByStudent", nullable = false)
    public boolean isExamTakenByStudent() {
        return examTakenByStudent;
    }

    public void setExamTakenByStudent(boolean examTakenByStudent) {
        this.examTakenByStudent = examTakenByStudent;
    }

    @Column(name = "creditsBase", nullable = false)
    public int getCreditsBase() {
        return creditsBase;
    }

    public void setCreditsBase(int creditsBase) {
        this.creditsBase = creditsBase;
    }


    /**
     * Overriding of the toString() method
     * to show all the attributes of the Course object instead of its unique code
     *
     * @return Course with all its attributes to String
     */
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", examMadeByProfessor=" + examMadeByProfessor +
                ", examTakenByStudent=" + examTakenByStudent +
                '}';
    }

    /**
     * Overriding of the equals() method
     * to compare Course objects based on their id and nothing else
     *
     * @param o other Object to compare with this object
     * @return true if equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id;
    }

    /**
     * Overriding of the hashCode() method
     * to base the identity of the Course object on its id and nothing else
     *
     * @return a hash code based on the Course's id
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
