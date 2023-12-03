package me.ziahh.sgm.module;

import me.ziahh.sgm.bean.Student;
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
    }

    private void login(){
        //记得查空
        //System.out.println("还没有任何管理员账号，请注册！");
        boolean isLogined = false;
        while (!isLogined) {
            System.out.println("请输入账号：");
            String account = sc.next();

            //如果输入0，退出系统
            if ("0".equals(account)) {break;}

            System.out.println("请输入密码：");
            String password = sc.next();
            String decodedPassword = "";

            //学生登录
            if (account.length() == 12){
                Student student = Utils.getStudentById(account);
                if (student!=null){
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

                } else {
                    //找不到此学生
                    System.out.println("学号或密码不正确，请重试！");
                }
            }


            if(isLogined){
                isLogined = false;
                break;
            }
        }
    }

    private void enterStudentFrame(Student student){
        if (studentServiceFrame == null){
            studentServiceFrame = new StudentServiceFrame(student);
        }
        studentServiceFrame.start();
    }
}
