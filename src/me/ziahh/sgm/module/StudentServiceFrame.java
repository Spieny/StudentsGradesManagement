package me.ziahh.sgm.module;

import me.ziahh.sgm.bean.Student;
import me.ziahh.sgm.util.Utils;

import java.util.Scanner;

public class StudentServiceFrame {

    private Student currentLoginedStudent = null;
    private Scanner sc = new Scanner(System.in);

    public StudentServiceFrame(Student currentLoginedStudent) {
        this.currentLoginedStudent = currentLoginedStudent;
    }

    public void start(){
        boolean quitFlag = false;
        System.out.println("欢迎您，学生:" + currentLoginedStudent.getStudentName());
        while(!quitFlag){
            //DataWriter.writeAll();
            System.out.println("=======> 广东原友大学 学生界面 <========");
            System.out.println("      1. 查询基本信息");
            System.out.println("      2. 查询成绩");
            System.out.println("      3. 修改密码");
            System.out.println("      0. 退出系统");
            System.out.println("======================================");
            System.out.println(" 请输入指令：");
            String in = sc.next();
            switch (in){
                case "1":
                    System.out.println(currentLoginedStudent.toBasicInformation());
                    System.out.println();
                    System.out.println("输入任意字符继续......");
                    sc.next();
                    break;
                case "2":
                    System.out.println(currentLoginedStudent.toGradesList());
                    System.out.println();
                    System.out.println("输入任意字符继续......");
                    sc.next();
                    break;
                case "3":
                    editPassword();
                    break;
                case "0":
                    //退出的时候保存数据
                    DataHandler.saveAllData();
                    quitFlag = true;
                    break;
                default:
                    System.out.println("你的指令不正确！请重试一次。");
            }
        }
        quitFlag = false;
    }

    private void editPassword(){
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
            //确认密码和第一次输入的密码一致
            if (orignalPassword.equals(currentLoginedStudent.getStudentPassword())){
                if (confirmPassword.equals(newPassword)){
                    if (Utils.isSafePassword(confirmPassword)){
                        currentLoginedStudent.setStudentPassword(Utils.getMD5(newPassword));
                        System.out.println("修改密码成功！请牢记新密码！");
                        //这里保存
                        break;
                    } else {
                        System.out.println("你的密码不够安全，请设置8-16位，包含字母数字的密码。");
                    }
                } else {
                    System.out.println("两次输入的密码不一致！");
                }
            } else {
                System.out.println("你输入的原密码不正确！");
            }
        }
    }


}
