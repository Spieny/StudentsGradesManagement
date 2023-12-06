package me.ziahh.sgm.module;

import me.ziahh.sgm.bean.Course;
import me.ziahh.sgm.bean.Grade;
import me.ziahh.sgm.bean.Student;
import me.ziahh.sgm.bean.Teacher;

import java.io.*;
import java.util.ArrayList;

public class DataHandler {

    //约定学生的学号长度为十二
    public static final int STUDENT_ID_LENGTH = 12;
    //约定教师的工号长度为四
    public static final int TEACHER_IN_LENGTH = 4;

    private static ArrayList<Student> students = new ArrayList<>();
    private static ArrayList<Teacher> teachers = new ArrayList<>();
    private static ArrayList<Course> courses = new ArrayList<>();
    private static ArrayList<Grade> grades = new ArrayList<>();

    public DataHandler(){
        readAllData();
    }

    public static void saveAllData(){
        try (ObjectOutputStream studentsStream = new ObjectOutputStream(new FileOutputStream("src/students.txt"));
             ObjectOutputStream coursesStream = new ObjectOutputStream(new FileOutputStream("src/courses.txt"));
             ObjectOutputStream teachersStream = new ObjectOutputStream(new FileOutputStream("src/teachers.txt"));
             ObjectOutputStream gradesStream = new ObjectOutputStream(new FileOutputStream("src/grades.txt"))){

            studentsStream.writeObject(students);
            System.out.println("已写入 " + students.size() + " 条学生数据至本地");
            teachersStream.writeObject(teachers);
            System.out.println("已写入 " + teachers.size() + " 条教师数据至本地");
            coursesStream.writeObject(courses);
            System.out.println("已写入 " + courses.size() + " 条课程数据至本地");
            gradesStream.writeObject(grades);
            System.out.println("已写入 " + grades.size() + " 条成绩数据至本地");

            System.out.println("成功保存所有数据至本地");
        } catch (IOException e) {
            System.out.println("本地数据写入失败:" + e.getMessage());
        }
    }

    public static void readAllData(){
        try (ObjectInputStream studentsStream = new ObjectInputStream(new FileInputStream("src/students.txt"));
             ObjectInputStream coursesStream = new ObjectInputStream(new FileInputStream("src/courses.txt"));
             ObjectInputStream teachersStream = new ObjectInputStream(new FileInputStream("src/teachers.txt"));
             ObjectInputStream gradesStream = new ObjectInputStream(new FileInputStream("src/grades.txt"))
        ) {
            ArrayList<Student> inputStudents = (ArrayList<Student>) studentsStream.readObject();
            System.out.println("已读取学生数据 " + inputStudents.size() + " 条");
            ArrayList<Teacher> inputTeachers = (ArrayList<Teacher>) teachersStream.readObject();
            System.out.println("已读取教师数据 " + inputTeachers.size() + " 条");
            ArrayList<Course> inputCourses = (ArrayList<Course>) coursesStream.readObject();
            System.out.println("已读取课程数据 " + inputCourses.size() + " 条");
            ArrayList<Grade> inputGrades = (ArrayList<Grade>) gradesStream.readObject();
            System.out.println("已读取成绩数据 " + inputGrades.size() + " 条");

            courses = inputCourses;
            students = inputStudents;
            teachers = inputTeachers;
            grades = inputGrades;

            System.out.println("读取本地数据完毕");

        } catch (Exception e) {
            System.out.println("读取本地数据失败:" + e.getMessage());
        }
    }

    public static ArrayList<Grade> getGrades() {
        return grades;
    }

    public static void setGrades(ArrayList<Grade> grades) {
        DataHandler.grades = grades;
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }

    public static ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public static ArrayList<Course> getCourses() {
        return courses;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void setTeachers(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}
