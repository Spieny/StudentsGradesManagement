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

    @Override
    public String toString() {
        return "----教师基本信息----" + "\n"
                + "姓名:" + teacherName  + "\n"
                + "工号:" + teacherId + "\n"
                + "任课：" + teachCoursesIds.size() + "\n"
                + "------------------";
    }

    public String toStringLine(){
        return teacherId + "|" + teacherType + teacherName;
    }
}
