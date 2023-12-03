package me.ziahh.sgm.module;

import me.ziahh.sgm.bean.Course;
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

    //private ArrayList<Student> grades = new ArrayList<>();

    public void saveAllData(){
        try (ObjectOutputStream studentsStream = new ObjectOutputStream(new FileOutputStream("src/students.txt"));
             ObjectOutputStream coursesStream = new ObjectOutputStream(new FileOutputStream("src/courses.txt"));
             ObjectOutputStream teachersStream = new ObjectOutputStream(new FileOutputStream("src/teachers.txt"))) {

            studentsStream.writeObject(students);
            teachersStream.writeObject(teachers);
            coursesStream.writeObject(courses);

            System.out.println("已保存所有数据至本地");
        } catch (IOException e) {
            System.out.println("本地数据写入失败:" + e.getMessage());
        }
    }

    public void readAllData(){
        try (ObjectInputStream studentsStream = new ObjectInputStream(new FileInputStream("src/students.txt"));
             ObjectInputStream coursesStream = new ObjectInputStream(new FileInputStream("src/courses.txt"));
             ObjectInputStream teachersStream = new ObjectInputStream(new FileInputStream("src/teachers.txt"))
        ) {
            ArrayList<Student> inputStudents = (ArrayList<Student>) studentsStream.readObject();
            ArrayList<Course> inputCourses = (ArrayList<Course>) coursesStream.readObject();
            ArrayList<Teacher> inputTeachers = (ArrayList<Teacher>) teachersStream.readObject();
            courses = inputCourses;
            students = inputStudents;
            teachers = inputTeachers;
            System.out.println("已成功读取本地数据");

        } catch (Exception e) {
            System.out.println("读取本地数据失败:" + e.getMessage());
        }
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
