package me.ziahh.sgm.module;

import me.ziahh.sgm.bean.Student;
import me.ziahh.sgm.bean.Teacher;
import me.ziahh.sgm.bean.TeacherType;
import me.ziahh.sgm.util.Utils;

import java.util.Objects;
import java.util.Scanner;

/**
 * @author Ziahh
 * 登陆界面
 * 根据账号前往不同的模块
 */
public class LoginFrame {

    private AdminServiceFrame adminServiceFrame = null;
    private StudentServiceFrame studentServiceFrame = null;
    private TeacherServiceFrame teacherServiceFrame = null;
    private Scanner sc = new Scanner(System.in);

    public void run(){
        boolean flag = false;
        initInitialTestData();
        for(Student s:DataHandler.getStudents()){
            System.out.println(s.toString());
        }
        System.out.println("--------------------");
        for(Teacher t:DataHandler.getTeachers()){
            System.out.println(t.toString());
        }

        while (true){
            System.out.println();
            System.out.println("=========> 广东原友大学成绩管理系统 <==========");
            System.out.println("1.登录账号");
            System.out.println("0.退出系统");
            System.out.println("===============> 欢迎使用 <=================");
            System.out.println();
            String command = sc.next();
            switch (command){
                case "1":
                    login();
                    break;
                case "0":
                    System.out.println("下次再见~");
                    flag = true;
                    break;
                default:
                    System.out.println("你的指令不正确！请重试一次。");
            }
            //如果用户输入了0，则退出系统
            if (flag){
                break;
            }
        }
    }

    //注入一些测试用的数据，版本完成后删除
    private void initInitialTestData(){
        //学生测试
        DataHandler.getStudents().add(new Student("张三","202000014126","zhangsan@163.com"));
        DataHandler.getStudents().add(new Student("李四","202000064127","lisifour@163.com"));
        DataHandler.getStudents().add(new Student("小云","202000054031","xiaocloud@qq.com"));
        //教师测试
        DataHandler.getTeachers().add(new Teacher("潘子","admin",TeacherType.ADMIN));
        DataHandler.getTeachers().add(new Teacher("老寒雪","123456",TeacherType.TEACHER));
    }

    private void login(){
        boolean isLogined = false;
        while (!isLogined) {
            System.out.println("请输入账号：");
            String account = sc.next();

            //如果输入0，退出系统
            if ("0".equals(account)) {break;}

            System.out.println("请输入密码：");
            String password = sc.next();
            String decodedPassword = "";

            //通过id先获取一个对象
            Object person = Utils.getPersonById(account);

            if (person != null){
                //教师登录
                if (person instanceof Teacher teacher){
                    try{
                        //将输入的密码用MD5算法转换为字符串
                        decodedPassword = Utils.getMD5(password);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    //MD5密码校验
                    if (Objects.equals(decodedPassword, teacher.getTeacherPassword())){
                        System.out.println("登录成功！");
                        isLogined = true;
                        if(teacher.getTeacherType() == TeacherType.ADMIN){
                            enterAdminFrame(teacher);
                        } else {
                            enterTeacherFrame(teacher);
                        }

                    } else {
                        System.out.println("工号或密码不正确，请重试！");
                    }
                }
                //学生登录
                if (person instanceof Student student){
                        try{
                            decodedPassword = Utils.getMD5(password);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        //MD5密码校验
                        if (Objects.equals(decodedPassword, student.getStudentPassword())){
                            System.out.println("登录成功！");
                            isLogined = true;
                            enterStudentFrame(student);
                        } else {
                            System.out.println("学号或密码不正确，请重试！");
                        }

                    }
                } else{
                System.out.println("该用户不存在！请重新输入。");
            }
        }

    }

    private void enterAdminFrame(Teacher teacher) {
        if (adminServiceFrame == null){
            adminServiceFrame = new AdminServiceFrame(teacher);
        }
        adminServiceFrame.start();
    }

    private void enterStudentFrame(Student student){
        if (studentServiceFrame == null){
            studentServiceFrame = new StudentServiceFrame(student);
        }
        studentServiceFrame.start();
    }

    private void enterTeacherFrame(Teacher teacher){
        if (teacherServiceFrame == null){
            teacherServiceFrame = new TeacherServiceFrame(teacher);
        }
        teacherServiceFrame.start();
    }
}
