package me.ziahh.sgm.bean;

import me.ziahh.sgm.module.DataHandler;

public class Course {

    private String courseId;
    private String courseName;
    private double courseScore;
    private double courseDuration;

    public Course() {
    }

    public Course(String courseName, double courseScore, double courseDuration) {
        this.courseId = "c" + String.valueOf(DataHandler.getCourses().size() + 10000);
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

    public String toStringLine(){
        return courseId + " | " + courseName + " " + courseScore + "分,学时:" + courseDuration + "h";
    }

}