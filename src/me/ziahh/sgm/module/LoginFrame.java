package me.ziahh.sgm.module;

import java.util.Scanner;

/**
 * @author Ziahh
 * 登陆界面
 * 根据账号前往不同的模块
 */
public class LoginFrame {

    private Scanner sc = new Scanner(System.in);

    public void run(){

    }

    private void login(){

        //记得查空
        //System.out.println("还没有任何管理员账号，请注册！");

        //
        boolean logined = false;
        System.out.println();
        System.out.println("=========> 广东原友大学成绩管理系统 <==========");
        System.out.println("1.登录账号");
        System.out.println("0.退出系统");
        System.out.println("===============> 欢迎使用 <=================");
        System.out.println();
        while (true) {
            System.out.println("请输入账号：");
            String account = sc.next();
            //如果输入0，退出系统
            if (account.equals("0")) {
                break;
            }
            System.out.println("请输入密码：");
            String password = sc.next();
            //待修改 链接数据库 and MD5校验
        }
    }
}
