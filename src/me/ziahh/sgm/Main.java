package me.ziahh.sgm;

import me.ziahh.sgm.module.DataHandler;
import me.ziahh.sgm.module.LoginFrame;
import me.ziahh.sgm.util.Utils;

public class Main {

    public static void main(String[] args){
        DataHandler.readAllData();

        LoginFrame loginFrame = new LoginFrame();
        loginFrame.run();
    }

}
