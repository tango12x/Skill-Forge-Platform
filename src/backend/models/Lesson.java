package backend.models;

import java.util.ArrayList;


public class Lesson {
    private String lessonId;
    private String title;
    private String content;
    private String courseId;
    private Quiz quiz;
    private ArrayList<String> optionalResources;

    // CLASS CONSTRUCTOR IN CASE CONTENT IS PROVIDED
    public Lesson(String lessonId, String title, String courseId, String content) {
        this.lessonId = lessonId;
        this.title = title;
        this.courseId = courseId;
        this.content = content;
        this.optionalResources = new ArrayList<String>();
    }

    // OVERLOADING CONSTRUCTOR IN CASE OF CONTENT IS NOT PROVIDED
    public Lesson(String lessonId, String title, String courseId) {
        this.lessonId = lessonId;
        this.title = title;
        this.courseId = courseId;
        this.optionalResources = new ArrayList<String>();
    }

    // standard getters and setters
    public String getLessonId() {
        return lessonId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getOptionalResources() {
        return optionalResources;
    }

    public void setOptionalResources(ArrayList<String> optionalResources) {
        this.optionalResources = optionalResources;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    //CHECKING IF THE LESSON IS VALID
    public boolean isValid(){
        if (quiz == null) {
            System.out.println("Lesson is not valid because it has no quiz");
            return false;
        } else if (content.equals("") ) {
            System.out.println("Lesson is not valid because it has no content");
            return false;
        } else if (title.equals("")) {
            System.out.println("Lesson is not valid because it has no title");
            return false;
        } else {
            return true;
            
        }
    }

    //METHOD TO ADD RESOURCES( URL )  TO THE LESSONS
    public void addResource(String url) {
        if (!optionalResources.contains(url)) {
            optionalResources.add(url);}}


}


