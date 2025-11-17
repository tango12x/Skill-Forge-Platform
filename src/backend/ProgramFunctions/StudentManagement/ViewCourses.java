package backend.ProgramFunctions.StudentManagement;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.json.JSONArray;

import backend.ProgramFunctions.CourseManagement.*;
import backend.ProgramFunctions.InstructorManagement.*;
import backend.JsonDatabaseManager.*;


public class ViewCourses {
    Student student;
    private CourseDatabaseManager Cdb;
    private UsersDatabaseManager Udb;
    private ArrayList<Course> availableCourses;
    private ArrayList<Instructor> instructors;

    //CLASS CONSTRUCTOR
    public ViewCourses(Student student) {
        this.student = student;
        Cdb = new CourseDatabaseManager();
        Udb = new UsersDatabaseManager();
        this.availableCourses = new ArrayList<Course>();
        this.instructors = new ArrayList<Instructor>();
    }
    public ViewCourses(String studentId) {
        Cdb = new CourseDatabaseManager();
        Udb = new UsersDatabaseManager();
        this.student = (Student)Udb.getUser(studentId);
        this.availableCourses = new ArrayList<Course>();
        this.instructors = new ArrayList<Instructor>();
    }


    //METHOD TO GET ALL available COURSES AND INSTRUCTORS for a STUDENT (excluding ENROLLED)
    public void getCoursesAndInstructors() {
        ArrayList<String> enrolledCourseIds = student.getEnrolledCourses();
        this.availableCourses.clear();
        this.instructors.clear();
        JSONArray allCourses = Cdb.getAllCourses();
        for (int i = 0; i < allCourses.length(); i++) {
            String id = allCourses.getJSONObject(i).getString("courseId");
            boolean isEnrolled = false;
            for (String enrolledId : enrolledCourseIds) {
                if (id.equals(enrolledId)) {
                    isEnrolled = true;
                    break;
                }
            }
            if (!isEnrolled) {
                Course course = Cdb.getCourse(id);
                this.availableCourses.add(course);
                Instructor instructor = (Instructor) Udb.getUser(course.getInstructorId());
                this.instructors.add(instructor);
            }
        }

    }

    //standard getters
    public ArrayList<Course> getAvailableCourses() {
        return availableCourses;
    }
    public ArrayList<Instructor> getInstructors() {
        return instructors;
    }
    
}
