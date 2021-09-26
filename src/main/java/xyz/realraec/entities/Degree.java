package xyz.realraec.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.*;

/**
 * Degree class
 */
@Table(name = "degrees")
@Entity(name = "Degree")
@DynamicUpdate
public class Degree {

    private long id;
    private String name;
    private Set<Course> courses = new HashSet<>();

    public Degree() {
        this.name = "Degree";
    }

    public Degree(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "degree_id", nullable = false)
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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "partOfDegree_id")
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    /**
     * Overriding of the toString() method
     * to show all the attributes of the Degree object instead of its unique code
     *
     * @return Degree with all its attributes to String
     */
    @Override
    public String toString() {
        return "Degree{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", courses=" + courses +
                '}';
    }

    /**
     * Overriding of the equals() method
     * to compare Degree objects based on their id and nothing else
     *
     * @param o other Object to compare with this object
     * @return true if equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Degree degree = (Degree) o;
        return id == degree.id;
    }

    /**
     * Overriding of the hashCode() method
     * to base the identity of the Degree object on its id and nothing else
     *
     * @return a hash code based on the Degree's id
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
