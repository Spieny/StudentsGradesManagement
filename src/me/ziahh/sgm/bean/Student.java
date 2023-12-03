package me.ziahh.sgm.bean;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Student {

    private String studentName;
    private String studentId;
    private String studentPassword;
    private char studentGender;
    private String studentClass;
    private String studentEmail;

    //档案建立时间
    private LocalDateTime buildTime;
    //最后修改时间
    private LocalDateTime lastModifiedTime;
    //成绩表
    private ArrayList<Grade> grades;
}
