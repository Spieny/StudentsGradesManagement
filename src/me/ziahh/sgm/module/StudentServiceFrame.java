package me.ziahh.sgm.module;

import me.ziahh.sgm.bean.Student;

public class StudentServiceFrame {

    private Student currentLoginedStudent = null;

    public StudentServiceFrame(Student currentLoginedStudent) {
        this.currentLoginedStudent = currentLoginedStudent;
        test();
    }

    private void test(){
        System.out.println("ssf");
    }

    public void start(){
        System.out.println("start");
    }


}
