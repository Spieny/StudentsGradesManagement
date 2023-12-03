package me.ziahh.sgm.bean;

import me.ziahh.sgm.util.Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Student {

    private String studentName;
    private String studentId;
    private String studentPassword;
    private char studentGender;
    //班级示例：2020级软件工程4班
    private String studentClass;
    private String studentEmail;

    //档案建立时间
    private LocalDateTime buildTime;
    //最后修改时间
    private LocalDateTime lastModifiedTime;
    //成绩表
    private ArrayList<Grade> grades;

    //学生的增加功能只能输入姓名，学号，邮箱。即其他信息由学号获取;
    public Student(String studentName, String studentId, String studentEmail) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.studentEmail = studentEmail;
        //设置默认密码
        try {
            this.studentPassword = Utils.getMD5("a12b34");
        } catch (Exception e){
            e.printStackTrace();
        }

        //由学号自动生成其他信息
        updateStudent();
    }

    public Student() {
    }

    public Student(String studentName, String studentId, String studentPassword,
                   char studentGender, String studentClass, String studentEmail,
                   LocalDateTime buildTime, LocalDateTime lastModifiedTime, ArrayList<Grade> grades) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.studentPassword = studentPassword;
        this.studentGender = studentGender;
        this.studentClass = studentClass;
        this.studentEmail = studentEmail;
        this.buildTime = buildTime;
        this.lastModifiedTime = lastModifiedTime;
        this.grades = grades;
    }

    //根据学号，更新学生的其他属性
    private void updateStudent(){
        String year = this.studentId.substring(0,4);
        String clazz = this.studentId.substring(8,9);
        this.studentClass = year + "级" + Utils.getStudentMajorById(studentId) + clazz + "班";
        this.buildTime = LocalDateTime.now();
        this.lastModifiedTime = LocalDateTime.now();
        this.studentGender = Utils.getStudentGenderById(this.studentId);
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public char getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(char studentGender) {
        this.studentGender = studentGender;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public LocalDateTime getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(LocalDateTime buildTime) {
        this.buildTime = buildTime;
    }

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<Grade> grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentName='" + studentName + '\'' +
                ", studentId='" + studentId + '\'' +
                ", studentPassword='" + studentPassword + '\'' +
                ", studentGender=" + studentGender +
                ", studentClass='" + studentClass + '\'' +
                ", studentEmail='" + studentEmail + '\'' +
                ", buildTime=" + buildTime +
                ", lastModifiedTime=" + lastModifiedTime +
                ", grades=" + grades +
                '}';
    }
}
