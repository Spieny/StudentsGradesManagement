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
    }

    private static void test(){
        System.out.println(Utils.getStudentGenderById("202000064126"));
    }

}
