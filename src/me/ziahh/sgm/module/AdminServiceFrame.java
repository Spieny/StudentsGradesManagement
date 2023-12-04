package me.ziahh.sgm.module;

import me.ziahh.sgm.bean.Student;
import me.ziahh.sgm.bean.Teacher;
import me.ziahh.sgm.util.Utils;

import java.util.ArrayList;
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
            System.out.println("      1. 学生操作     2. 课程操作");
            System.out.println("      3. 教师操作     4. 搜索界面");
            System.out.println("      5. 修改密码");
            System.out.println("      0. 退出系统");
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
        boolean flag = false;
        while (!flag){
            System.out.println("====> 管理员界面 学生子菜单 <====");
            System.out.println("         a. 添加学生");
            System.out.println("         b. 删除学生");
            System.out.println("         c. 修改学生");
            System.out.println("         d. 查询学生");
            System.out.println("         0. 退出系统");
            System.out.println("=============================");
            System.out.println(" 请输入指令：");
            String in = sc.next();
            switch (in){
                case "a":
                    //
                    addStudent();
                case "b":
                    //
                    deleteStudent();
                    break;
                case "c":
                    //
                    modifyStudent();
                    break;
                case "d":
                    //
                    queryStudent();
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

    private void queryStudent() {
        ArrayList<Student> students = DataHandler.getStudents();
        int page = 1;
        //int command = -1;
        while (true){
            System.out.println();
            System.out.println("-------------------- 第" + page + "页 --------------------");
            String[] r = Utils.pagedQuery(students,5,page);
            if (r != null) {
                for (String s : r) {
                    System.out.println(s);
                }
            }
            System.out.println("-------------------- 第" + page + "页 --------------------");
            System.out.println("输入 1 返回上一页 | 输入 2 进入下一页 | 输入 0 退出查询");
            String studentID = sc.next();
            if (studentID.equals("2") && page < (students.size() / 5) + 1){
                page++;
                continue;//如果输入的是翻页指令，直接跳过下面的代码
            }
            if (studentID.equals("1") && page > 1){
                page--;
                continue;//如果输入的是翻页指令，直接跳过下面的代码
            }
            if (studentID.equals("0")){
                System.out.println("退出查询......");
                break;
            }
            Student s = (Student) Utils.getPersonById(studentID);
            if (s == null){
                System.out.println("你查找的学生不存在！");
            } else {
                System.out.println(s);
            }
        }
    }

    private void modifyStudent() {
    }

    private void deleteStudent() {
    }

    private void addStudent() {
        String id;
        String email;
        System.out.println("请输入学生的姓名：");
        String name = sc.next();

        while (true){
            System.out.println("请输入学生的学号：");
            String next = sc.next();
            if (Utils.isValidStudentId(next)){
                id = next;
                break;
            } else {
                System.out.println("你输入的学号格式有误！请重新输入。");
            }
        }

        while (true){
            System.out.println("请输入学生的邮箱地址：");
            String next = sc.next();
            //使用工具类里的方法（正则表达式）验证邮箱格式
            if(Utils.isMail(next)){
                email = next;
                break;
            } else {
                System.out.println("你输入的邮箱格式有误！请重新输入。");
            }
        }
        //有参构造器创建基本学生对象，此构造器可以根据学号自动生成其他属性
        Student student = new Student(name,id,email);

        DataHandler.getStudents().add(student);
        System.out.println("添加学生成功！\n" + student.toString());
    }
}
