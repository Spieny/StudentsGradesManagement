package me.ziahh.sgm.module;

import me.ziahh.sgm.bean.*;
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
        DataHandler.saveAllData();
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
                    studentOperateMenu();
                    break;
                case "2":
                    courseOperateMenu();
                    break;
                case "3":
                    teacherOperateMenu();
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

        while (true){
            Course c = Utils.userChooseCourse();
            if (c == null){
                break;
            }
                //分页查询学生信息，选择要添加的学生并添加成绩
                Student s = Utils.userChooseAStudent();

                //防止爆数组
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

                        //如果成绩已存在，替换原有的成绩
                        Grade oldGrade= s.getSpecificGradeOfStudent(c.getCourseId());
                        if (oldGrade != null){
                            DataHandler.getGrades().remove(oldGrade);
                            System.out.println("修改学生的分数为：" + realScore);
                        }

                        Grade grade = new Grade(c.getCourseId(),s.getStudentId(),realScore,Grade.getGradeLevelByScore(realScore));
                        //System.out.println(grade);
                        DataHandler.getGrades().add(grade);
                        break;
                    } else {
                        System.out.println("分数不合法！请重新输入。");
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
        while (true){
            System.out.println("-----------搜索-----------");
            System.out.println("a. 模糊查找");
            System.out.println("b. 条件查找");
            System.out.println("c. 退出");
            System.out.println("-------------------------");
            String command = sc.next();
            switch (command){
                case "a":
                    //模糊查找
                    fuzzySearch();
                    break;
                case "b":
                    conditionalSearch();
                    break;
                    //条件查找
                default:
                    System.out.println("指令有误，请重新输入。");
            }

        }

    }

    private void conditionalSearch() {
    }

    private void fuzzySearch(){
            System.out.println("选择搜索种类:");
            System.out.println("1. 学生");
            System.out.println("2. 课程");
            System.out.println("3. 教师");
            System.out.println("0. 退出");
            String type = sc.next();
            String keyword;
            switch (type){
                case "1":
                    System.out.println("请输入搜索学生的关键字：");
                    keyword = sc.next();
                    showSearchResults(Utils.getFuzzySearchResultSet(keyword,1));
                    break;
                case "2":
                    System.out.println("请输入搜索课程关键字：");
                    keyword = sc.next();
                    showSearchResults(Utils.getFuzzySearchResultSet(keyword,2));
                    break;
                case "3":
                    System.out.println("请输入搜索教师关键字：");
                    keyword = sc.next();
                    showSearchResults(Utils.getFuzzySearchResultSet(keyword,3));
                    break;
                case "0":
                    System.out.println("退出操作");
                    break;
                default:
                    System.out.println("该种类不存在！");
                    break;
            }
    }

    private void showSearchResults(ArrayList<Object> results){
        System.out.println("------------------搜索结果------------------");
        //如果搜索结果为空
        if (results.isEmpty()){
            System.out.println();
            System.out.println("搜索结果为空");
            System.out.println();
            System.out.println("-------------------------------------------");
            return;
        }
        //如果是学生
        if (results.get(0) instanceof Student){
            System.out.println("> 查找到包含关键字的学生如下：");
            for (Object obj : results){
                Student stu = (Student) obj;
                System.out.println(stu.toStringLine());
            }
        }
        //如果是教师
        if (results.get(0) instanceof Teacher){
            System.out.println("> 查找到包含关键字的教师如下：");
            for (Object obj : results){
                Teacher tea = (Teacher) obj;
                System.out.println(tea.toStringLine());
            }
        }
        //如果是课程
        if (results.get(0) instanceof Course){
            System.out.println("> 查找到包含关键字的课程如下：");
            for (Object obj : results){
                Course stu = (Course) obj;
                System.out.println(stu.toStringLine());
            }
        }

        System.out.println("-------------------------------------------");

    }


    /*      teacher field start        */

    private void teacherOperateMenu() {
        boolean flag = false;
        while (!flag){
            System.out.println("====> 管理员界面 教师子菜单 <====");
            System.out.println("         a. 添加教师");
            System.out.println("         b. 删除教师");
            System.out.println("         c. 修改教师");
            System.out.println("         d. 查询教师");
            System.out.println("         e. 教师选课退课");
            System.out.println("         0. 退出系统");
            System.out.println("=============================");
            System.out.println(" 请输入指令：");
            String in = sc.next();
            switch (in){
                case "a":
                    addTeacher();
                    break;
                case "b":
                    deleteTeacher();
                    break;
                case "c":
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
        while (true){
            Teacher teacher = Utils.userChooseATeacher();
            //返回null说明用户选择退出
            if (teacher==null){
                break;}
            System.out.println(teacher);
            //是否已经修改了信息
            boolean isEdited = false;
            //是否退出
            boolean isQuit = false;
            while (true){
                if(isQuit){
                    return;
                }
                String command;
                String value;
                System.out.println("输入 name 修改教师姓名");
                System.out.println("输入 pwd 修改教师密码");
                System.out.println("输入 type 修改教师权限类型");
                System.out.println("输入 unban 解冻账号");
                System.out.println("输入 quit 退出修改");
                command = sc.next();
                switch (command){
                    case "unban":
                        teacher.setFailToLoginTimes(0);
                        System.out.println("解冻账号成功！");
                        break;
                    case "quit":
                        isQuit = true;
                        break;
                    case "type":
                        while (true){
                            boolean isOk = false;
                            if (teacher.getTeacherType() == TeacherType.TEACHER){
                                System.out.println("将账号权限修改为：" + TeacherType.ADMIN + "吗？(y/n)");
                            } else {
                                System.out.println("将账号权限修改为：" + TeacherType.TEACHER + "吗？(y/n)");
                            }
                            value = sc.next();
                            switch (value){
                                case "y":
                                    isEdited = true;
                                    isOk = true;
                                    if (teacher.getTeacherType() == TeacherType.TEACHER){
                                        teacher.setTeacherType(TeacherType.ADMIN);
                                    } else {
                                        teacher.setTeacherType(TeacherType.TEACHER);
                                    }
                                    break;
                                case "n":
                                    isOk = true;
                                    System.out.println("取消操作");
                                    break;
                                default:
                                    System.out.println("指令错误！");
                            }
                            if (isOk){break;}
                        }
                    case "pwd":
                        isEdited = true;
                        while (true){
                            System.out.println("将密码修改为：");
                            value = sc.next();
                            if (Utils.isSafePassword(value)){
                                //设置密码，Student对象会自动转换为MD5文本
                                teacher.setTeacherPassword(value);
                                break;
                            } else {
                                System.out.println("密码不安全，请重试！");
                            }
                        }
                        break;
                    default:
                        System.out.println("未知指令，请重试！");
                }
                if (isEdited){
                    System.out.println("修改完成！教师信息如下：");
                    System.out.println(teacher);
                }
            }
        }
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
            if(Utils.isSafePassword(password)){
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

    private void courseOperateMenu() {
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
                    addCourse();
                    break;
                case "b":
                    deleteCourse();
                    break;
                case "c":
                    modifyCourse();
                    break;
                case "d":
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
    }

    private void queryCourse() {
            Course c = Utils.userChooseCourse();
            if (c == null){
                System.out.println("你选择的课程不存在！");
            } else {
                System.out.println(c);
                System.out.println("输入任意字符继续......");
                sc.next();
            }
    }

    private void modifyCourse() {
        while (true){
            Course course = Utils.userChooseCourse();
            if (course == null){break;}

            System.out.println(course);
            //是否被修改
            boolean isEdited = false;
            //是否退出
            boolean isQuit = false;
            while (true){
                if(isQuit){
                    return;
                }
                String command;
                //输入的值
                String value;
                System.out.println("输入 name 修改课程姓名");
                System.out.println("输入 time 修改课程学时");
                System.out.println("输入 score 修改课程学分");
                System.out.println("输入 quit 退出修改");
                command = sc.next();
                switch (command){
                    case "quit":
                        isQuit = true;
                        break;
                    case "name":
                        isEdited = true;
                        System.out.print("将课程名称修改为：");
                        value = sc.next();
                        course.setCourseName(value);
                        break;
                    case "score":
                        isEdited = true;
                        while (true){
                            System.out.print("将课程学分修改为：");
                            value = sc.next();
                            if (Utils.isLegalScore(value)){
                                //设置密码，Student对象会自动转换为MD5文本
                                course.setCourseScore(Double.parseDouble(value));
                                break;
                            } else {
                                System.out.println("你输入的分数不合法，请重试！");
                            }
                        }
                        break;
                    case "time":
                        isEdited = true;
                        while (true){
                            System.out.print("将课程学时修改为：");
                            value = sc.next();
                            if (Utils.isLegalScore(value)){
                                //设置密码，Student对象会自动转换为MD5文本
                                course.setCourseDuration(Double.parseDouble(value));
                                break;
                            } else {
                                System.out.println("你输入的时长不合法，请重试！");
                            }
                        }
                        break;
                    default:
                        System.out.println("未知指令，请重试！");
                }
                if (isEdited){
                    System.out.println("修改完成！课程信息如下：");
                    System.out.println(course);
                    System.out.println("输入任何字符继续......");
                    sc.next();
                }
            }
        }

    }

    private void deleteCourse() {
        while (true){
            Course course = Utils.userChooseCourse();
            if (course == null){break;}

            System.out.print("请确认是否删除该课程（y/n）");
            String confirm = sc.next();
            switch (confirm){
                case "y":
                    DataHandler.getCourses().remove(course);
                    System.out.println("已删除课程：" + course.getCourseName());
                    break;
                case "n":
                    System.out.println("取消操作");
                    break;
                default:
                    System.out.println("未知指令，请重试！");
            }
        }
    }

    private void addCourse() {
        String courseName;
        double courseDuration,courseScore;
        System.out.println("请输入课程名：");
        courseName = sc.next();
        while (true){
            System.out.print("请输入课程学时：");
            String temp = sc.next();
            if (Utils.isLegalScore(temp)){
                courseDuration = Double.parseDouble(temp);
                break;
            } else {
                System.out.println("你输入的时长不合法，请重试！");
            }
        }
        while (true){
            System.out.print("请输入课程学分：");
            String temp = sc.next();
            if (Utils.isLegalScore(temp)){
                courseScore = Double.parseDouble(temp);
                break;
            } else {
                System.out.println("你输入的时长不合法，请重试！");
            }
        }
        System.out.println("课程添加成功！");
        Course course = new Course(courseName,courseScore,courseDuration);
        DataHandler.getCourses().add(course);
    }

    /*      course field end        */

    private void studentOperateMenu() {
        while (true) {
            System.out.println("====> 管理员界面 学生子菜单 <====");
            System.out.println("         a. 添加学生");
            System.out.println("         b. 删除学生");
            System.out.println("         c. 修改学生");
            System.out.println("         d. 查询学生");
            System.out.println("         0. 退出系统");
            System.out.println("=============================");
            System.out.println(" 请输入指令：");
            String in = sc.next();
            switch (in) {
                case "a":
                    addStudent();
                case "b":
                    deleteStudent();
                    break;
                case "c":
                    modifyStudent();
                    break;
                case "d":
                    queryStudent();
                    break;
                case "0":
                    return;

            }
        }
    }

    private void queryStudent() {
        while (true){
            Student student = Utils.userChooseAStudent();
            //返回null说明用户选择退出
            if (student==null){
                break;}
            System.out.println(student.toBasicInformation());
            System.out.println();
            System.out.println(student.toGradesList());
            System.out.println("输入任意字符继续......");
            sc.next();
        }
    }

    private void modifyStudent() {
        while (true){
            Student student = Utils.userChooseAStudent();
            //返回null说明用户选择退出
            if (student==null){
                break;}
            System.out.println(student.toBasicInformation());
            //是否已经修改了信息
            boolean isEdited = false;
            //是否退出
            boolean isQuit = false;
            while (true){
                if(isQuit){
                    return;
                }
                String command;
                String value;
                System.out.println("输入 name 修改学生姓名");
                System.out.println("输入 pwd 修改学生密码");
                System.out.println("输入 gender 修改学生性别");
                System.out.println("输入 email 修改学生邮箱");
                System.out.println("输入 unban 解冻学生");
                System.out.println("输入 quit 退出修改");
                command = sc.next();
                switch (command){
                    case "unban":
                        student.setFailToLoginTimes(0);
                        System.out.println("解冻学生成功！");
                        break;
                    case "quit":
                        isQuit = true;
                        break;
                    case "name":
                        isEdited = true;
                        System.out.println("将学生姓名修改为：");
                        value = sc.next();
                        student.setStudentName(value);
                        break;
                    case "gender":
                        isEdited = true;
                        while (true){
                            System.out.println("将学生性别修改为：");
                            value = sc.next();
                            if (Utils.isLegalGender(value)){
                                student.setStudentGender(value.charAt(0));
                                break;
                            } else {
                                System.out.println("性别不合法，请重试！");
                            }
                        }
                        break;
                    case "email":
                        isEdited = true;
                        while (true){
                            System.out.println("将学生邮箱修改为：");
                            value = sc.next();
                            if (Utils.isMail(value)){
                                student.setStudentEmail(value);
                                break;
                            } else {
                                System.out.println("邮箱格式错误，请重试！");
                            }
                        }
                        break;
                    case "pwd":
                        isEdited = true;
                        while (true){
                            System.out.println("将学生密码修改为：");
                            value = sc.next();
                            if (Utils.isSafePassword(value)){
                                //设置密码，Student对象会自动转换为MD5文本
                                student.setStudentPassword(value);
                                break;
                            } else {
                                System.out.println("密码不安全，请重试！");
                            }
                        }
                        break;
                    default:
                        System.out.println("未知指令，请重试！");
                }
                if (isEdited){
                    System.out.println("修改完成！学生信息如下：");
                    System.out.println(student.toBasicInformation());
                }
            }
        }
    }

    private void deleteStudent() {
        while (true){
            Student student = Utils.userChooseAStudent();
            //返回null说明用户选择退出
            if (student==null){
                break;}
            System.out.println("你确定要删除学生：" + student.getStudentName() + "吗？");
            //二次确认操作
            while (true){
                String command = sc.next();
                if (command.equals("y")){
                    DataHandler.getStudents().remove(student);
                    System.out.println("删除成功！");
                    break;
                } else if (command.equals("n")){
                    System.out.println("已取消操作。");
                    break;
                } else {
                    System.out.println("指令错误，请重新输入。");
                }
            }
        }
    }

    private void addStudent() {
        String id;
        String email;
        System.out.println("请输入学生的姓名：(输入0退出)");
        String name = sc.next();

        if (name.equals("0")){
            System.out.println("退出操作");
            return;
        }

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
        System.out.println("添加学生成功！\n" + student.toBasicInformation());
    }

}
