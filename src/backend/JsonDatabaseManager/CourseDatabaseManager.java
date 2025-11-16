package backend.JsonDatabaseManager;

import org.json.JSONArray;
import org.json.JSONObject;

import backend.ProgramFunctions.UserAccountManagement.User;
import backend.ProgramFunctions.CourseManagement.Course;
import backend.ProgramFunctions.InstructorManagement.Instructor;
import backend.ProgramFunctions.StudentManagement.Student;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CourseDatabaseManager {
    private JSONArray courses;
    private final String COURSES_FILE = "data/DatabaseJSONFiles/courses.json";

    // CLASS CONSTRUCTOR and INITIALIZER (READS THE FILE OR CREATES A NEW ONE IF NOT
    // EXIST)
    public CourseDatabaseManager() {
        try {
            File file = new File(COURSES_FILE);
            String content = Files.readString(Paths.get(COURSES_FILE));
            System.out.println(content);
            System.out.println("Users loaded successfully.");
            this.courses = new JSONArray(content);
            System.out.println("Users loaded successfully2.");
        } catch (Exception e) {
            e.printStackTrace();
            // If file not found, create a new one
            File file = new File(COURSES_FILE);
            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        System.out.println("File created: " + file.getName());
                    }
                } catch (IOException e2) {
                    System.out.println("An error occurred.");
                    e2.printStackTrace();
                }
            }
            this.courses = new JSONArray();
            System.out.println("No existing users file found. Starting with an empty course database.");
        }

    }

    // METHOD TO SEARCH AND RETURN THE INDEX OF THE COURSE IF EXIST IN THE DB , -1
    // IF
    public int SearchCourseIndex(String courseId) {
        for (int i = 0; i < this.courses.length(); i++) {
            JSONObject obj = this.courses.getJSONObject(i);
            if (obj.getString("courseId").equals(courseId)) {
                return i; // Return the index if found
            }
        }
        return -1; // Return -1 if not found
    }

    // METHOD TO SEARCH AND RETURN THE COURSE IF EXIST IN THE DB
    public Course getCourse(String courseId) {
        Course course = null;
        try {
            if (this.courses.length() == 0) {
                return null; // No users in the database
            }
            // Search for the userId
            int userIndex = SearchCourseIndex(courseId);
            if (userIndex == -1) {
                return null; // User not found
            }
            JSONObject obj = this.courses.getJSONObject(userIndex);
            String title = obj.getString("title");
            String instructorId = obj.getString("instructorId");
            String description = obj.getString("description");
            course = new Course(courseId, title, instructorId, description);

            JSONArray students = obj.getJSONArray("students");
            course.setStudents(JsonDatabaseManager.toStringList(students));
            JSONArray lessons = obj.getJSONArray("lessons");
            course.setLessons();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return course;
    }

    // METHOD TO ADD A COURSE 
    public void addCourse(Course newCourse) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("courseId", generateId());
            obj.put("title", newCourse.getTitle());
            obj.put("instructorId", newCourse.getInstructorId());
            obj.put("description", newCourse.getDescription());

            // optional fields if exit now
            obj.put("lessons", newCourse.getLessons() == null ? new ArrayList<String>()
                    : JsonDatabaseManager.toJSONArray(newCourse.getLessons()));
            obj.put("students", newCourse.getStudents() == null ? new ArrayList<String>()
                    : JsonDatabaseManager.toJSONArray(newCourse.getStudents()));
            this.courses.put(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // USED TO UPDATE THE COURSE DETAILS (LIKE LESSONS ADDED OR STUDENTS ENROLLED)
    public void update(Course updatedCourse) {
        int courseIndex = SearchCourseIndex(updatedCourse.getCourseId());
        if (courseIndex == -1) {
            addCourse(updatedCourse);
            return;
        }
        try {
            JSONObject obj = this.courses.getJSONObject(courseIndex);
            // optional fields if exit now
            obj.put("enrolledCourses", new ArrayList<String>());
            obj.put("lessons", updatedCourse.getLessons() == null ? new ArrayList<String>()
                    : JsonDatabaseManager.toJSONArray(updatedCourse.getLessons()));
            obj.put("students", updatedCourse.getStudents() == null ? new ArrayList<String>()
                    : JsonDatabaseManager.toJSONArray(updatedCourse.getStudents()));
            this.courses.put(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // METHOD TO SAVE COURSES TO FILE
    public void SaveCoursesToFile() {
        try {
            FileWriter writer = new FileWriter(COURSES_FILE);
            writer.write(this.courses.toString(4)); // pretty print
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // METHOD TO DELETE A COURSE FROM THE DB
    public void deleteCourse(String courseId) {
        int courseIndex = SearchCourseIndex(courseId);
        if (courseIndex != -1) {
            this.courses.remove(courseIndex);
        }
    }

    // METHOD TO RETURN ALL COURSES JSON ARRAY (FOR VALIDATION PURPOSES)
    public JSONArray getAllCourses() {
        return this.courses;
    }

    // METHOD TO GENERATE A UNIQUE ID
    public String generateId() {
        return String.format("%d", this.courses.length() + 1);
    }

    public ArrayList<Lesson> () {
        ArrayList<Course> courseList = new ArrayList<>();
        for (int i = 0; i < this.courses.length(); i++) {
            JSONObject obj = this.courses.getJSONObject(i);
            String courseId = obj.getString("courseId");
            Course course = getCourse(courseId);
            if (course != null) {
                courseList.add(course);
            }
        }
        return courseList;
    }

    //For testing purposes only
    public static void main(String[] args) {
        //test for all methods here
        CourseDatabaseManager coursesDB = new CourseDatabaseManager();
        Course course1 = new Course("Java Programming", "1", "Learn Java from scratch");
        coursesDB.addCourse(course1);
        coursesDB.SaveCoursesToFile();
        



    }


}