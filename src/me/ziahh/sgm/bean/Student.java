package me.ziahh.sgm.bean;

import me.ziahh.sgm.module.DataHandler;
import me.ziahh.sgm.util.Utils;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    //密码错误次数
    private int failToLoginTimes = 0;

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
                   LocalDateTime buildTime, LocalDateTime lastModifiedTime) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.studentPassword = studentPassword;
        this.studentGender = studentGender;
        this.studentClass = studentClass;
        this.studentEmail = studentEmail;
        this.buildTime = buildTime;
        this.lastModifiedTime = lastModifiedTime;
    }

    //根据学号，更新学生的其他属性
    private void updateStudent(){
        String year = this.studentId.substring(0,4);
        String clazz = this.studentId.substring(8,9);
        this.studentClass = year + "级 " + Utils.getStudentMajorById(studentId) + " " + clazz + "班";
        this.buildTime = LocalDateTime.now();
        this.lastModifiedTime = LocalDateTime.now();
        this.studentGender = Utils.getStudentGenderById(this.studentId);
    }

    public int getFailToLoginTimes() {
        return failToLoginTimes;
    }

    public void setFailToLoginTimes(int failToLoginTimes) {
        this.failToLoginTimes = failToLoginTimes;
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
        this.studentPassword = Utils.getMD5(studentPassword);
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

    @Override
    public String toString() {
        return "[DEBUG] Student{" +
                "studentName='" + studentName + '\'' +
                ", studentId='" + studentId + '\'' +
                ", studentPassword='" + studentPassword + '\'' +
                ", studentGender=" + studentGender +
                ", studentClass='" + studentClass + '\'' +
                ", studentEmail='" + studentEmail + '\'' +
                ", buildTime=" + buildTime +
                ", lastModifiedTime=" + lastModifiedTime +
                '}';
    }

    public String toBasicInformation() {
        return    "--------学生基本信息--------" + "\n"
                + "姓名:" + studentName  + "\n"
                + "性别:" + studentGender  + "\n"
                + "班级:" + studentClass  + "\n"
                + "学号:" + studentId + "\n"
                + "邮箱：" + studentEmail + "\n"
                + "-------------------------";
    }

    public String toStringLine(){
        //return studentId + " | " + studentClass + " " + studentName + " " + studentGender;
        return String.format("%-12s %-18s %-3s %-1s", studentId,studentClass,studentName,studentGender);
    }

    /**
     * 返回可以直接打印出来的多行字符串，代表学生的成绩单
     * @return
     */
    public String toGradesList(){
        StringBuilder sb= new StringBuilder();
        sb.append("------------ 学生" + studentName + "的成绩单 ------------\n");
        for(String s: getGradesStringList()){
            sb.append(s).append("\n");
        }
        sb.append("绩点：").append(String.format("%.2f",getGPA())).append(" ,总修学时：").append(getTime()).append("小时\n");
        sb.append("---------------------------------------\n");
        return sb.toString();
    }

    /**
     * 获得一个考生成绩字符串的List
     * @return
     */
    public ArrayList<String> getGradesStringList(){
        ArrayList<String> gradesStringList = new ArrayList<>();
        for(Grade g : DataHandler.getGrades()){
            if (g.getStudentId().equals(studentId)){
                String id = g.getCourseId();
                Course c = Utils.getCourseById(id);
                //我就不信他会爆空指针错误
                gradesStringList.add(id + " " + c.getCourseName() + " " + String.format("%.1f",g.getScore()) + "分");
            }
        }
        return gradesStringList;
    }

    /**
     * 获取学生的成绩HashMap，key为课程id,value为成绩分数
     * @return
     */
    public HashMap<String,Double> getScoreToCourse(){
        HashMap<String,Double> scoreToCourse = new HashMap<>();
        for(Grade g : DataHandler.getGrades()){
            if (g.getStudentId().equals(studentId)){
                String id = g.getCourseId();
                Course c = Utils.getCourseById(id);
                scoreToCourse.put(c.getCourseId(),g.getScore());
            }
        }
        return scoreToCourse;
    }

    /**
     * 获取学生的成绩HashMap，key为课程id,value为这们课对应的学时
     * @return
     */
    public HashMap<String,Double> getDurationToCourse(){
        HashMap<String,Double> durationToCourse = new HashMap<>();
        for(Grade g:DataHandler.getGrades()){
            if (g.getStudentId().equals(studentId)){
                durationToCourse.put(g.getCourseId(), Objects.requireNonNull(Utils.getCourseById(g.getCourseId())).getCourseDuration());
            }
        }
        return durationToCourse;
    }

    /**
     * 获取学生的成绩HashMap，key为课程id,value为这们课对应的学分
     * @return
     */
    public HashMap<String,Double> getCourseScoreToCourse(){
        HashMap<String,Double> csToCourse = new HashMap<>();
        for(Course c: DataHandler.getCourses()){
            csToCourse.put(c.getCourseId(),c.getCourseScore());
        }
        return csToCourse;
    }

    /**
     * 获取学生的总学分
     */
    public double getTotalCourseScore(){
        double total = 0;
        for(Grade g : DataHandler.getGrades()){
            if (g.getStudentId().equals(studentId)){
                String id = g.getCourseId();
                Course c = Utils.getCourseById(id);
                total += c.getCourseScore();
            }
        }
        return total;
    }


    /**
     * 计算学生的GPA
     */
    public double getGPA(){
        double gpa = 0;
        //共修学时
        HashMap<String, Double> map = getScoreToCourse();
        for (Map.Entry<String,Double> entry : map.entrySet()) {
            Course c = Utils.getCourseById(entry.getKey());
            if (c != null) {
                //处理，防止绩点变成负数
               double var = entry.getValue() - 50;
               if (var < 0){var=0;}

               gpa+=(var / 10.0) * c.getCourseScore();

            }
        }
        gpa = gpa / getTotalCourseScore();
        return gpa;
    }

    /**
     * 计算学生的总学时
     * @return
     */
    public double getTime(){
        double time = 0;
        //共修学时
        HashMap<String, Double> map = getDurationToCourse();
        for (Map.Entry<String,Double> entry : map.entrySet()) {
            time+=entry.getValue();
        }
        return time;
    }

    /**
     * 获取学生某门课的成绩对象
     * @param courseId
     * @return
     */
    public Grade getSpecificGradeOfStudent(String courseId){
        for(Grade g : DataHandler.getGrades()){
            if (g.getCourseId().equals(courseId)){
                return g;
            }
        }
        return null;
    }
}
