package me.ziahh.sgm;

import me.ziahh.sgm.module.DataHandler;
import me.ziahh.sgm.module.LoginFrame;
import me.ziahh.sgm.util.Utils;

public class Main {

    public static void main(String[] args){
        DataHandler dataHandler = new DataHandler();
        //dataHandler.readAllData();
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.run();

        test();
    }

    private static void test(){
        System.out.println(
                "202000014126\t2020级\t信息系统与信息管理\t4班\t张三\n" +
                "202000064127\t2020级\t电子信息工程\t4班\t李四\t男\n" +
                "202000054031\t2020级\t数据科学与大数据技术\t4班\t小云\t女\n"
        );
    }

}
