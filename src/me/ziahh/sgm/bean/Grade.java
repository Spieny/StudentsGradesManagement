package me.ziahh.sgm.bean;

public class Grade {
    private String courseId;
    private String studentId;
    private double score;
    private GradeLevel gradeLevel;

    public Grade() {
    }

    public Grade(String courseId, String studentId, double score, GradeLevel gradeLevel) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.score = score;
        this.gradeLevel = gradeLevel;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public GradeLevel getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
    }
}
