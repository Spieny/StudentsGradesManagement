package me.ziahh.sgm.bean;

public class Course {

    private String courseId;
    private String courseName;
    private double courseScore;
    private double courseDuration;

    public Course() {
    }

    public Course(String courseId, String courseName, double courseScore, double courseDuration) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseScore = courseScore;
        this.courseDuration = courseDuration;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getCourseScore() {
        return courseScore;
    }

    public void setCourseScore(double courseScore) {
        this.courseScore = courseScore;
    }

    public double getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(double courseDuration) {
        this.courseDuration = courseDuration;
    }
}
