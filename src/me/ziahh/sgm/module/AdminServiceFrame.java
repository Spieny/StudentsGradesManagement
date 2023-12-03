package me.ziahh.sgm.module;

import me.ziahh.sgm.bean.Teacher;

import java.util.Scanner;

public class AdminServiceFrame {

    private Teacher currentLoginedTeacher = null;
    private Scanner sc = new Scanner(System.in);
    private boolean quitFlag = false;

    public AdminServiceFrame(Teacher teacher){
        currentLoginedTeacher = teacher;
    }

    public void test(){

    }

    public void start(){
        System.out.println("欢迎您，管理员:" + currentLoginedTeacher.getTeacherName());
        while(!quitFlag){
            //DataWriter.writeAll();
            System.out.println("==========> 广东原友大学 管理员界面 <===========");
            System.out.println("                 -学生操作-                  ");
            System.out.println("  1.添加学生  2.删除学生  3.修改学生  4.查询学生  ");
            System.out.println(" 5.添加课程  6.修改课程  7.查询课程  8.未知领域");
            System.out.println(" 0.退出系统");
            System.out.println("============================================");
            System.out.println(" 输入指令：");
            String in = sc.next();
            switch (in){
                case "1":
                    //registerNewStudent();
                    break;
                case "2":
                    //deleteStudent();
                    break;
                case "3":
                    //modifyStudent();
                    break;
                case "4":
                    //queryStudent();
                    break;
                case "5":
                    //addNewCourse();
                    break;
                case "6":
                    //modifyCourse();
                    break;
                case "7":
                    //queryCourse();
                    break;
                case "8":
                    System.out.println("前面的区域，以后再来探索吧");
                    //updateStudentCourses();
                    break;
                case "0":
                    System.out.println("===退出成绩管理系统===");
                    quitFlag = true;
                    break;
                default:
                    System.out.println("你的指令不正确！请重试一次。");
            }
        }
        quitFlag = false;
    }
}
