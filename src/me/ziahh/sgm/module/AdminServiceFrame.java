package me.ziahh.sgm.module;

import me.ziahh.sgm.bean.Course;
import me.ziahh.sgm.bean.Grade;
import me.ziahh.sgm.bean.Student;
import me.ziahh.sgm.bean.Teacher;
import me.ziahh.sgm.util.Utils;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminServiceFrame {

    private Teacher currentLoginedTeacher = null;
    private Scanner sc = new Scanner(System.in);
    private boolean quitFlag = false;

    public AdminServiceFrame(Teacher teacher){
        currentLoginedTeacher = teacher;
    }

    public void test(){

    }

    public void start(){
        System.out.println("欢迎您，管理员:" + currentLoginedTeacher.getTeacherName());
        while(!quitFlag){
            //DataWriter.writeAll();
            System.out.println("=======> 广东原友大学 管理员界面 <========");
            System.out.println("      1. 学生操作     2. 课程操作");
            System.out.println("      3. 教师操作     4. 搜索界面");
            System.out.println("      5. 添加成绩     6. 修改密码");
            System.out.println("      0. 退出系统");
            System.out.println("======================================");
            System.out.println(" 请输入指令：");
            String in = sc.next();
            switch (in){
                case "1":
                    studentOpearateMenu();
                    break;
                case "2":
                    courseOpearateMenu();
                    break;
                case "3":
                    teacherOpearateMenu();
                    break;
                case "4":
                    searchMenu();
                    break;
                case "5":
                    addGrades();
                    break;
                case "6":
                    changePassword();
                    break;
                case "0":
                    System.out.println("===退出成绩管理系统===");
                    quitFlag = true;
                    break;
                default:
                    System.out.println("你的指令不正确！请重试一次。");
            }
        }
        quitFlag = false;
    }

    private void addGrades() {
        ArrayList<Course> courses = DataHandler.getCourses();
        ArrayList<Student> students = DataHandler.getStudents();
        ArrayList<Teacher> teachers = DataHandler.getTeachers();
        //三空检查
        if(courses.isEmpty()){
            System.out.println("还没有添加任何课程，请至少添加一个。");}
        if(students.isEmpty()){
            System.out.println("还没有添加任何学生，请至少添加一个。");}
        if(teachers.isEmpty()){
            System.out.println("还没有添加任何教师，请至少添加一个。");}
        //选择课程
        int page = 1;
        //int command = -1;
        while (true){
            System.out.println();
            System.out.println("----------------------- 请选择课程 -----------------------");
            //读取课程信息的字符串数组
            String[] r = Utils.pagedQuery(courses,5,page);
            if (r != null) {
                //逐一输出
                for (String s : r) {
                    System.out.println(s);
                }
            }
            System.out.println("-------------------- 第" + page + "页 --------------------");
            System.out.println("输入 1 返回上一页 | 输入 2 进入下一页 | 输入 0 退出查询");
            String courseId = sc.next();
            if (courseId.equals("2") && page < (courses.size() / 5) + 1){
                page++;
                continue;//如果输入的是翻页指令，直接跳过下面的代码
            }
            if (courseId.equals("1") && page > 1){
                page--;
                continue;//如果输入的是翻页指令，直接跳过下面的代码
            }
            if (courseId.equals("0")){
                System.out.println("退出查询......");
                break;
            }
            //往选择的课程里添加学生成绩
            Course c = (Course) Utils.getCourseById(courseId);
            if (c == null){
                System.out.println("你选择的课程不存在！请重试。");
                continue;
            }
            //分页查询学生信息，选择要添加的学生并添加成绩
            int page2nd = 1;
            //int command = -1;
            while (true){
                System.out.println();
                System.out.println("--------------------- "+c.getCourseName()+",请选择学生 ---------------------");
                //读取学生信息的字符串数组
                String[] st = Utils.pagedQuery(students,5,page2nd);
                if (st != null) {
                    //逐一输出
                    for (String s : st) {
                        System.out.println(s);
                    }
                }
                System.out.println("--------------------------- 第" + page2nd + "页 --------------------------------");
                System.out.println("输入 1 返回上一页 | 输入 2 进入下一页 | 输入 0 退出查询");
                String studentID = sc.next();
                if (studentID.equals("2") && page2nd < (students.size() / 5) + 1){
                    page2nd++;
                    continue;//如果输入的是翻页指令，直接跳过下面的代码
                }
                if (studentID.equals("1") && page2nd > 1){
                    page2nd--;
                    continue;//如果输入的是翻页指令，直接跳过下面的代码
                }
                if (studentID.equals("0")){
                    System.out.println("退出查询......");
                    break;
                }
                //要管理的学生对象s
                Student s = (Student) Utils.getPersonById(studentID);
                if (s == null){
                    System.out.println("你查找的学生不存在！");
                    continue;
                }
                //先输入分数，添加成绩
                while (true){
                    System.out.println("请输入该学生 "+ c.getCourseName() + " 的成绩");
                    String score = sc.next();
                    double realScore = 0;
                    if(Utils.isLegalScore(score)){
                        realScore = Double.parseDouble(score);
                        Grade grade = new Grade(c.getCourseId(),s.getStudentId(),realScore,Grade.getGradeLevelByScore(realScore));
                        System.out.println(grade);
                        DataHandler.getGrades().add(grade);
                        break;
                    } else {
                        System.out.println("分数不合法！请重新输入。");
                    }
                }
            }

        }

    }

    private void changePassword() {
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
            if (orignalPassword.equals(currentLoginedTeacher.getTeacherPassword())){
                if (confirmPassword.equals(newPassword)){
                    if (confirmPassword.matches("^[A-Za-z][0-9]{8,16}")){
                        currentLoginedTeacher.setTeacherPassword(Utils.getMD5(newPassword));
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

    private void searchMenu() {

    }

    /*      teacher field start        */

    private void teacherOpearateMenu() {
        boolean flag = false;
        while (!flag){
            System.out.println("====> 管理员界面 教师子菜单 <====");
            System.out.println("         a. 添加教师");
            System.out.println("         b. 删除教师");
            System.out.println("         c. 修改教师");
            System.out.println("         d. 查询教师");
            System.out.println("         e. 教师选课");
            System.out.println("         0. 退出系统");
            System.out.println("=============================");
            System.out.println(" 请输入指令：");
            String in = sc.next();
            switch (in){
                case "a":
                    //
                    addTeacher();
                case "b":
                    //
                    deleteTeacher();
                    break;
                case "c":
                    //
                    modifyTeacher();
                    break;
                case "d":
                    queryTeacher();
                    break;
                case "e":
                    teacherChooseCourse();
                    break;
                case "0":
                    flag = true;
                    break;

            }
            if (flag){
                break;
            }
        }
        flag = false;
    }

    //教师选课
    private void teacherChooseCourse() {
        while (true){
            Teacher teacher = Utils.userChooseATeacher();
            //如果返回的老师为null，说明用户选择退出
            if (teacher == null){
                System.out.println("退出选课");
                break;}
            while (true){
                Course course = Utils.userChooseCourse();
                //如果返回的课程为null，说明用户选择退出
                if (course == null){
                    System.out.println("返回到教师选择界面");
                    break;}
                //读取教师的课程表
                ArrayList<String> coursesIds = teacher.getTeachCoursesIds();
                //防止多选课程
                if (coursesIds.contains(course.getCourseId())){
                    System.out.println("你已经选择该课程！");
                    continue;
                }
                //添加并保存新课程表
                coursesIds.add(course.getCourseId());
                teacher.setTeachCoursesIds(coursesIds);
                System.out.println("你成功选择该课程！");
            }
        }
    }

    private void queryTeacher() {
        while (true){
            Teacher teacher = Utils.userChooseATeacher();
            if (teacher == null){
                break;}
            System.out.println(teacher);
            System.out.println("输入任意字符继续......");
            sc.next();
        }
    }

    private void modifyTeacher() {
    }

    private void deleteTeacher() {
        int page = 1;
        //int command = -1;
        while (true){
            ArrayList<Teacher> teachers = DataHandler.getTeachers();
            System.out.println();
            System.out.println("-------------------- 第" + page + "页 --------------------");
            String[] r = Utils.pagedQuery(teachers,5,page);
            if (r != null) {
                //逐一输出
                for (String s : r) {
                    System.out.println(s);
                }
            }
            System.out.println("-------------------- 第" + page + "页 --------------------");
            System.out.println("输入 1 返回上一页 | 输入 2 进入下一页 | 输入 0 退出界面");
            String courseId = sc.next();
            if (courseId.equals("2") && page < (teachers.size() / 5) + 1){
                page++;
                continue;//如果输入的是翻页指令，直接跳过下面的代码
            }
            if (courseId.equals("1") && page > 1){
                page--;
                continue;//如果输入的是翻页指令，直接跳过下面的代码
            }
            if (courseId.equals("0")){
                System.out.println("退出查询......");
                break;
            }
            Teacher c = (Teacher) Utils.getPersonById(courseId);
            if (c == null){
                System.out.println("你选择的教师不存在！");
            } else {
                System.out.println("已删除教师" + c.getTeacherName() + "(" + c.getTeacherId() + ")");
                DataHandler.getTeachers().remove(c);
            }
        }
    }

    private void addTeacher() {
        Teacher teacher = new Teacher();
        System.out.println("请输入教师的姓名：");
        teacher.setTeacherName(sc.next());
        //输入教师，使用正则表达式，要求密码大于等于6位，包含字母和数字
        while (true){
            System.out.println("请输入教师的账号密码：");
            String password = sc.next();
            //使用正则表达式验证密码安全性 字母数字组合，长度不低于8
            if(password.matches("^[A-Za-z][0-9]{8,16}")){
                teacher.setTeacherPassword(Utils.getMD5(password));
                break;
            } else {
                System.out.println("你的密码不够安全，请设置8-16位，包含字母数字的密码。");
            }
        }

        DataHandler.getTeachers().add(teacher);
        System.out.println("添加教师成功！\n" + teacher.toString());
    }

    /*      teacher field end        */

    /*      course field begin        */

    private void courseOpearateMenu() {
        boolean flag = false;
        while (!flag){
            System.out.println("====> 管理员界面 课程子菜单 <====");
            System.out.println("         a. 添加课程");
            System.out.println("         b. 删除课程");
            System.out.println("         c. 修改课程");
            System.out.println("         d. 查询课程");
            System.out.println("         0. 退出系统");
            System.out.println("=============================");
            System.out.println(" 请输入指令：");
            String in = sc.next();
            switch (in){
                case "a":
                    //
                    addCourse();
                case "b":
                    //
                    deleteCourse();
                    break;
                case "c":
                    //
                    modifyCourse();
                    break;
                case "d":
                    //
                    queryCourse();
                    break;
                case "0":
                    flag = true;
                    break;

            }
            if (flag){
                break;
            }
        }
        flag = false;
    }

    private void queryCourse() {
        ArrayList<Course> courses = DataHandler.getCourses();
        int page = 1;
        //int command = -1;
        while (true){
            System.out.println();
            System.out.println("-------------------- 第" + page + "页 --------------------");
            //读取课程信息的字符串数组
            String[] r = Utils.pagedQuery(courses,5,page);
            if (r != null) {
                //逐一输出
                int i = 1;
                for (String s : r) {
                    System.out.println(i + "." + s);
                    i++;
                }
            }
            System.out.println("-------------------- 第" + page + "页 --------------------");
            System.out.println("输入 1 返回上一页 | 输入 2 进入下一页 | 输入 0 退出查询");
            String courseId = sc.next();
            if (courseId.equals("2") && page < (courses.size() / 5) + 1){
                page++;
                continue;//如果输入的是翻页指令，直接跳过下面的代码
            }
            if (courseId.equals("1") && page > 1){
                page--;
                continue;//如果输入的是翻页指令，直接跳过下面的代码
            }
            if (courseId.equals("0")){
                System.out.println("退出查询......");
                break;
            }
            Course c = Utils.getCourseById(courseId);
            if (c == null){
                System.out.println("你选择的课程不存在！");
            } else {
                System.out.println(c);
            }
        }
    }

    private void modifyCourse() {
    }

    private void deleteCourse() {
    }

    private void addCourse() {

    }

    /*      course field end        */

    private void studentOpearateMenu() {
        boolean flag = false;
        while (!flag){
            System.out.println("====> 管理员界面 学生子菜单 <====");
            System.out.println("         a. 添加学生");
            System.out.println("         b. 删除学生");
            System.out.println("         c. 修改学生");
            System.out.println("         d. 查询学生");
            System.out.println("         0. 退出系统");
            System.out.println("=============================");
            System.out.println(" 请输入指令：");
            String in = sc.next();
            switch (in){
                case "a":
                    //
                    addStudent();
                case "b":
                    //
                    deleteStudent();
                    break;
                case "c":
                    //
                    modifyStudent();
                    break;
                case "d":
                    //
                    queryStudent();
                    break;
                case "0":
                    flag = true;
                    break;

            }
            if (flag){
                break;
            }
        }
        flag = false;
    }

    private void queryStudent() {
        ArrayList<Student> students = DataHandler.getStudents();
        int page = 1;
        //int command = -1;
        while (true){
            System.out.println();
            System.out.println("-------------------- 第" + page + "页 --------------------");
            //读取学生信息的字符串数组
            String[] r = Utils.pagedQuery(students,5,page);
            if (r != null) {
                //逐一输出
                int i = 1;
                for (String s : r) {
                    System.out.println(i + "." + s);
                    i++;
                }
            }
            System.out.println("-------------------- 第" + page + "页 --------------------");
            System.out.println("输入 1 返回上一页 | 输入 2 进入下一页 | 输入 0 退出查询");
            String studentID = sc.next();
            if (studentID.equals("2") && page < (students.size() / 5) + 1){
                page++;
                continue;//如果输入的是翻页指令，直接跳过下面的代码
            }
            if (studentID.equals("1") && page > 1){
                page--;
                continue;//如果输入的是翻页指令，直接跳过下面的代码
            }
            if (studentID.equals("0")){
                System.out.println("退出查询......");
                break;
            }
            Student s = (Student) Utils.getPersonById(studentID);
            if (s == null){
                System.out.println("你查找的学生不存在！");
            } else {
                System.out.println(s);
            }
        }
    }

    private void modifyStudent() {
    }

    private void deleteStudent() {
        int page = 1;
        //int command = -1;
        while (true){
            ArrayList<Student> students = DataHandler.getStudents();
            System.out.println();
            System.out.println("-------------------- 第" + page + "页 --------------------");
            String[] r = Utils.pagedQuery(students,5,page);
            if (r != null) {
                //逐一输出
                int i = 1;
                for (String s : r) {
                    System.out.println(i + "." + s);
                    i++;
                }
            }
            System.out.println("-------------------- 第" + page + "页 --------------------");
            System.out.println("输入 1 返回上一页 | 输入 2 进入下一页 | 输入 0 退出界面");
            System.out.println("输入学号（序号）删除学生");
            String courseId = sc.next();
            if (courseId.equals("2") && page < (students.size() / 5) + 1){
                page++;
                continue;//如果输入的是翻页指令，直接跳过下面的代码
            }
            if (courseId.equals("1") && page > 1){
                page--;
                continue;//如果输入的是翻页指令，直接跳过下面的代码
            }
            if (courseId.equals("0")){
                System.out.println("退出查询......");
                break;
            }
            Student c = (Student) Utils.getPersonById(courseId);
            if (c == null){
                System.out.println("你选择的教师不存在！");
            } else {
                System.out.println("已删除学生" + c.getStudentName() + "(" + c.getStudentId() + ")");
                DataHandler.getTeachers().remove(c);
            }
        }
    }

    private void addStudent() {
        String id;
        String email;
        System.out.println("请输入学生的姓名：");
        String name = sc.next();

        while (true){
            System.out.println("请输入学生的学号：");
            String next = sc.next();
            if (Utils.isValidStudentId(next)){
                id = next;
                break;
            } else {
                System.out.println("你输入的学号格式有误！请重新输入。");
            }
        }

        while (true){
            System.out.println("请输入学生的邮箱地址：");
            String next = sc.next();
            //使用工具类里的方法（正则表达式）验证邮箱格式
            if(Utils.isMail(next)){
                email = next;
                break;
            } else {
                System.out.println("你输入的邮箱格式有误！请重新输入。");
            }
        }
        //有参构造器创建基本学生对象，此构造器可以根据学号自动生成其他属性
        Student student = new Student(name,id,email);

        DataHandler.getStudents().add(student);
        System.out.println("添加学生成功！\n" + student.toString());
    }

}
