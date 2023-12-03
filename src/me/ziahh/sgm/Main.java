package me.ziahh.sgm;

import me.ziahh.sgm.module.LoginFrame;
import me.ziahh.sgm.util.Utils;

public class Main {

    public static void main(String[] args) throws Exception {
        LoginFrame lf = new LoginFrame();
        lf.run();
    }

    private static void test() throws Exception {
        System.out.println(Utils.getMD5("test"));
    }

}
