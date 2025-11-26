package backend.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Question {
    // CLASS ATTRIBUTES
    private String questionId;
    private String questionText;
    private ArrayList<String> options;
    private String correctOption;
    private String explanation;
    private int points;

    // FULL CLASS CONSTRUCTOR
    public Question(String questionId, String questionText, ArrayList<String> options,
            String correctOption, String explanation, int points) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.options = (options != null) ? new ArrayList<>(options) : new ArrayList<>();
        this.correctOption = correctOption;
        this.explanation = explanation;
        this.points = points;
    }

    // CONSTRUCTOR TO DEAL WITH JSON
    public Question() {
        this.options = new ArrayList<>();
        this.explanation = "";
        this.points = 1;
        this.correctOption = "";
    }

    // CLASS CONSTRUCTOR WITH NO EXPLANATION AND DEFAULT POINTS VALUE
    public Question(String questionId, String questionText, ArrayList<String> options, String correctOption) {
        this(questionId, questionText, options, correctOption, "", 1);
    }

    // GETTERS AND SETTERS
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = (options != null) ? options : new ArrayList<>();
    }

    public String getCorrectOption() {
        return correctOption;
    }

    // SET CORRECT OPTION
    public void setCorrectOption(String correctOption) {
        if (this.options == null) {
            System.out.println("setCorrectOption : No options found , please add options first");
            return;
        } else if (!this.options.contains(correctOption)) {
            System.out.println("setCorrectOption : Option not found , adding the option to options");
            addOption(correctOption);;
        }
        this.correctOption = correctOption;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points > 0 ? points : 1;
    }

    // CLASS METHODS

    // CHECK IF THE CHOSEN OPTION MAPS TO THE CORRECT ANSWER
    public boolean isCorrectAnswer(String selectedOption) {
        return selectedOption.equals(correctOption);
    }

    // ADD OPTION TO THE QUESTION OPTIONS
    public void addOption(String option) {
        if (option != null && !option.trim().isEmpty()) {
            if (options == null) {
                options = new ArrayList<>();
            }
            if (!options.contains(option)) {
                options.add(option);
            }
        }
    }

    // REMOVE OPTION FROM OPTIONS BY STRING VALUE THEN UPDATE CORRECT OPTION
    public void removeOption(String option) {
        if (options == null || !options.contains(option)) {
            System.out.println("removeOption : Option not found to remove");
            return;
        }
        if (option == correctOption) {
            correctOption = "";
            System.out.println("removeOption : Correct option removed , please set a new correct option");
        }
        options.remove(option);
    }

    // REMOVE OPTION FROM OPTIONS BY INDEX THEN UPDATE CORRECT OPTION
    public void removeOption(int index) {
        if (options == null || index < 0 || index >= options.size()) {
            return;
        }
        if (index == options.indexOf(correctOption)) {
            correctOption = "";
            System.out.println("removeOption : Correct option removed , please set a new correct option");
        }
        options.remove(index);
    }

    // Validation methods
    public boolean isValid() {
        return questionId != null && !questionId.trim().isEmpty() &&
                questionText != null && !questionText.trim().isEmpty() &&
                options != null && options.size() >= 2 &&
                options.contains(correctOption) &&
                points > 0;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId='" + questionId + '\'' +
                ", questionText='" + questionText + '\'' +
                ", options=" + options +
                ", correctOption=" + correctOption +
                ", points=" + points +
                '}';
    }
}