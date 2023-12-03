package me.ziahh.sgm.module;

import me.ziahh.sgm.bean.Teacher;
import me.ziahh.sgm.util.Utils;

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
            System.out.println("=======> 广东原友大学 管理员界面 <========");
            System.out.println("  1. 学生操作");
            System.out.println("  2. 课程操作");
            System.out.println("  3. 教师操作");
            System.out.println("  4. 搜   索");
            System.out.println("  5. 修改密码");
            System.out.println("  0.退出系统");
            System.out.println("======================================");
            System.out.println(" 请输入指令：");
            String in = sc.next();
            switch (in){
                case "1":
                    studentOpearateMenu();
                    break;
                case "2":
                    courseOpearateMenu();
                    break;
                case "3":
                    teacherOpearateMenu();
                    break;
                case "4":
                    searchMenu();
                    break;
                case "5":
                    changePassword();
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

    private void changePassword() {
        while (true){
            System.out.println("请输入您原来的密码：");
            System.out.println("(输入 0 退出修改)");
            String orignalPassword = sc.next();

            //输入0退出
            if (orignalPassword.equals("0")){return;}

            //转换为MD5
            orignalPassword = Utils.getMD5(orignalPassword);

            System.out.println("请输入您的新密码：");
            String newPassword = sc.next();
            System.out.println("请再次输入您的新密码：");
            String confirmPassword = sc.next();
            if (orignalPassword.equals(currentLoginedTeacher.getTeacherPassword())){
                if (confirmPassword.equals(newPassword)){
                    currentLoginedTeacher.setTeacherPassword(Utils.getMD5(newPassword));
                    System.out.println("修改密码成功！请牢记新密码！");
                    //这里保存
                    break;
                } else {
                    System.out.println("两次输入的密码不一致！");
                }
            } else {
                System.out.println("你输入的原密码不正确！");
            }
        }
    }

    private void searchMenu() {

    }

    /*      teacher field start        */

    private void teacherOpearateMenu() {
        boolean flag = false;
        while (!flag){
            System.out.println("====> 管理员界面 教师子菜单 <====");
            System.out.println("         a. 添加教师");
            System.out.println("         b. 删除教师");
            System.out.println("         c. 修改教师");
            System.out.println("         d. 查询教师");
            System.out.println("         0. 退出系统");
            System.out.println("=============================");
            System.out.println(" 请输入指令：");
            String in = sc.next();
            switch (in){
                case "a":
                    //
                    addTeacher();
                case "b":
                    //
                    deleteTeacher();
                    break;
                case "c":
                    //
                    modifyTeacher();
                    break;
                case "d":
                    //
                    queryTeacher();
                    break;
                case "0":
                    flag = true;
                    break;

            }
            if (flag){
                break;
            }
        }
        flag = false;
    }

    private void queryTeacher() {
    }

    private void modifyTeacher() {
    }

    private void deleteTeacher() {
    }

    private void addTeacher() {
        Teacher teacher = new Teacher();
        System.out.println("请输入教师的姓名：");
        teacher.setTeacherName(sc.next());
        //输入教师，使用正则表达式，要求密码大于等于6位，包含字母和数字
        while (true){
            System.out.println("请输入教师的账号密码：");
            String password = sc.next();
            //使用正则表达式验证密码安全性
            if(password.matches("[a-z][0-9]]")){
                teacher.setTeacherPassword(Utils.getMD5(password));
                break;
            } else {
                System.out.println("你输入的密码不符合安全要求！请换一个密码。");
            }
        }

        DataHandler.getTeachers().add(teacher);
        System.out.println("添加教师成功！\n" + teacher.toString());
    }

    /*      teacher field end        */

    private void courseOpearateMenu() {
    }

    private void studentOpearateMenu() {
    }
}
