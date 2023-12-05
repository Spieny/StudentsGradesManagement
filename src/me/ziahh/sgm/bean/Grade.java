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

    public static GradeLevel getGradeLevelByScore(double score){
        if (score >= 0 && score < 60){
            return GradeLevel.FAIL;
        }
        if (score >= 60 && score < 80){
            return GradeLevel.PASS;
        }
        if (score >= 80 && score < 90){
            return GradeLevel.GOOD;
        }
        if (score >= 90 && score <= 100){
            return GradeLevel.EXCELLENT;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "courseId='" + courseId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", score=" + score +
                ", gradeLevel=" + gradeLevel +
                '}';
    }
}
