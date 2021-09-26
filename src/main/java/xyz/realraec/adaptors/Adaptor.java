package xyz.realraec.adaptors;

import org.hibernate.Session;
import xyz.realraec.entities.*;
import xyz.realraec.queries.DALQueries;

import java.util.List;

/**
 * Bridge class to keep entity classes and DAL classes independent
 *
 * @see DALQueries
 * @see xyz.realraec.entities
 */
public class Adaptor {

    /**
     * Method to check if the object['s class] is valid
     *
     * @param objectToCheck object whose class needs to be tested
     * @return true if valid object
     * @throws Exception "Invalid object."
     */
    public static boolean isValidObject(Object objectToCheck) throws Exception {
        if (objectToCheck instanceof Course
                || objectToCheck instanceof Degree
                || objectToCheck instanceof Professor
                || objectToCheck instanceof Student
                || objectToCheck instanceof Administration) {
            return true;
        } else {
            throw new Exception("Invalid object.");
        }
    }

    /**
     * Method to check if the class is valid
     *
     * @param classToCheck class to check
     * @return true if valid class
     * @throws Exception "Invalid class."
     */
    public static boolean isValidClass(Class<?> classToCheck) throws Exception {
        if (classToCheck.equals(Course.class)
                || classToCheck.equals(Degree.class)
                || classToCheck.equals(Professor.class)
                || classToCheck.equals(Student.class)
                || classToCheck.equals(Administration.class)) {
            return true;
        } else {
            throw new Exception("Invalid class.");
        }
    }

    /**
     * Method to check if the id is valid (in a given table)
     *
     * @param classGiven class on which the table is based
     * @param id         id to check
     * @return true if valid id (not too high)
     * @throws Exception "Invalid id."
     */
    public static boolean isValidId(Class<?> classGiven, long id) throws Exception {
        if (id > 0 && id <= getMaxIdTable(classGiven)) {
            return true;
        } else {
            throw new Exception("Invalid id.");
        }
    }

    /**
     * Method to retrieve the id of the last object in a given table
     *
     * @param classGiven class on which the table is based
     * @return id of the last object in the table, -1 if the table does not exist
     */
    public static long getMaxIdTable(Class<?> classGiven) {
        Session session = DALQueries.beforeTryBlock();
        long maxId;

        if (classGiven.equals(Course.class)) {
            Class<Course> classWanted = Course.class;
            String databaseWanted = "courses";
            String columnWanted = "course_id";
            String query = "SELECT * FROM " + databaseWanted + " ORDER BY " + columnWanted + " DESC";
            List<Course> list = session.createNativeQuery(query, classWanted).getResultList();
            maxId = list.get(0).getId();
        } else if (classGiven.equals(Degree.class)) {
            Class<Degree> classWanted = Degree.class;
            String databaseWanted = "degrees";
            String columnWanted = "degree_id";
            String query = "SELECT * FROM " + databaseWanted + " ORDER BY " + columnWanted + " DESC";
            List<Degree> list = session.createNativeQuery(query, classWanted).getResultList();
            maxId = list.get(0).getId();
        } else if (classGiven.equals(Professor.class)) {
            Class<Professor> classWanted = Professor.class;
            String databaseWanted = "professors";
            String columnWanted = "professor_id";
            String query = "SELECT * FROM " + databaseWanted + " ORDER BY " + columnWanted + " DESC";
            List<Professor> list = session.createNativeQuery(query, classWanted).getResultList();
            maxId = list.get(0).getId();
        } else if (classGiven.equals(Student.class)) {
            Class<Student> classWanted = Student.class;
            String databaseWanted = "students";
            String columnWanted = "student_id";
            String query = "SELECT * FROM " + databaseWanted + " ORDER BY " + columnWanted + " DESC";
            List<Student> list = session.createNativeQuery(query, classWanted).getResultList();
            maxId = list.get(0).getId();
        } else if (classGiven.equals(Administration.class)) {
            maxId = 1;
        } else {
            maxId = -1L;
        }
        return maxId;
    }

}
