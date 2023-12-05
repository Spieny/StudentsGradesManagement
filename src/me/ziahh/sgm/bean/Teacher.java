package me.ziahh.sgm.bean;

import me.ziahh.sgm.module.DataHandler;
import me.ziahh.sgm.util.Utils;

import java.util.ArrayList;

public class Teacher {

    private String teacherName;
    //约定教师的工号长度为四位数
    private String teacherId;
    private String teacherPassword;
    //默认情况下为任课老师权限
    private TeacherType teacherType = TeacherType.TEACHER;
    private ArrayList<String> teachCoursesIds = new ArrayList<>();

    private int failToLoginTimes = 0;

    public Teacher(String teacherName, String teacherPassword, TeacherType teacherType) {
        this.teacherName = teacherName;
        this.teacherType = teacherType;
        this.teacherId = String.valueOf(DataHandler.getTeachers().size() + 1000);
        try {
            this.teacherPassword = Utils.getMD5(teacherPassword);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getFailToLoginTimes() {
        return failToLoginTimes;
    }

    public void setFailToLoginTimes(int failToLoginTimes) {
        this.failToLoginTimes = failToLoginTimes;
    }

    public Teacher() {
        this.teacherId = String.valueOf(DataHandler.getTeachers().size() + 1000);
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherPassword() {
        return teacherPassword;
    }

    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }

    public TeacherType getTeacherType() {
        return teacherType;
    }

    public void setTeacherType(TeacherType teacherType) {
        this.teacherType = teacherType;
    }

    public ArrayList<String> getTeachCoursesIds() {
        return teachCoursesIds;
    }

    public void setTeachCoursesIds(ArrayList<String> teachCoursesIds) {
        this.teachCoursesIds = teachCoursesIds;
    }

    @Override
    public String toString() {
        return "--------教师基本信息--------" + "\n"
                + "姓名:" + teacherName  + "\n"
                + "工号:" + teacherId + "\n"
                + "          -任课-         \n"
                + getStringOfTeachCourses()
                + "--------------------------";
    }

    public String toStringLine(){
        return teacherId + " | " + teacherType + " " +teacherName;
    }

    public String getStringOfTeachCourses(){
        StringBuilder coursesString = new StringBuilder();
        if (teachCoursesIds.isEmpty()){
            return "    该教师暂无教授任何课程\n";
        }
        for(String c : teachCoursesIds){
            Course course = Utils.getCourseById(c);
            if (c != null){
                coursesString.append("    ");
                coursesString.append(course.getCourseId() + " " +course.getCourseName());
                coursesString.append("\n");
            }
        }
        return coursesString.toString();
    }
}
