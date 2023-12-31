package me.ziahh.sgm.util;

import me.ziahh.sgm.bean.Course;
import me.ziahh.sgm.bean.GradeLevel;
import me.ziahh.sgm.bean.Student;
import me.ziahh.sgm.bean.Teacher;
import me.ziahh.sgm.module.DataHandler;
import org.junit.Test;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
    private static Scanner sc = new Scanner(System.in);

    /**
     * 对字符串md5加密
     * @param str 要加密的字符串
     * @return
     */
    public static String getMD5(String str){
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 根据学生id，返回该学生的专业名称，id有误或专业代码不存在则返回null
     * @param id 学生id
     * @return 学生专业名称
     */
    public static String getStudentMajorById(String id){
        //id长度不是12，说明这串id不是学生学号，返回null
        if (id.length() != 12){
            return null;
        }
        String studentMajorId = id.substring(4,8);
        try(
            FileInputStream majorStream = new FileInputStream("src/major.txt");
            )
        {
            //读入所有专业信息
            byte[] data = majorStream.readAllBytes();
            //转换为一串字符串，再分割为字符串数组，每一项代表一个专业
            String[] majors = new String(data).split("\n");

            //遍历所有专业，找出符合条件的专业名称，否则返回null
            for(String str:majors){
                //通过四个空格分割
                String[] subStr = str.split("    ");
                //分割的文本可能含有换行符，导致equals永远不能为true,要截取前四位字符
                if (studentMajorId.equals(subStr[1].substring(0,4))){
                    return subStr[0];
                }
            }
            return null;
        }
        catch (Exception e)
        {
            System.out.println("专业代码文件缺失！");
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 根据学生id，返回该学生的性别，id有误或性别判别字符不存在则返回空格' '
     * @param id 学生id
     * @return 学生性别
     */
    public static char getStudentGenderById(String id){
        //id长度不是12，说明这串id不是学生学号，返回null
        if (id.length() != 12){
            return ' ';
        }

        String key = id.substring(9,10);

        if ("0".equals(key)){
            return '女';
        } else if ("1".equals(key)){
            return '男';
        } else {
            return ' ';
        }
    }

    /***
     * 根据id，返回该学生或老师对象，id有误则返回null
     * @param id id
     * @return 学生或老师对象
     */
    public static Object getPersonById(String id){
        //通过id长度判断是老师还是学生
        if (id.length() == DataHandler.TEACHER_IN_LENGTH){
            for (Teacher teacher:DataHandler.getTeachers()){
                if(teacher.getTeacherId().equals(id)){
                    return teacher;
                }
            }
            return null;
        }
        else if (id.length() == DataHandler.STUDENT_ID_LENGTH){
            for(Student student:DataHandler.getStudents()){
                if (student.getStudentId().equals(id)){
                    return student;
                }
            }
        } else {
            return null;
        }
        return null;
    }

    /***
     * 根据课程id，返回该课程对象，id有误则返回null
     * @param id 课程id
     * @return 课程对象
     */
    public static Course getCourseById(String id){
        for(Course c: DataHandler.getCourses()){
            if (c.getCourseId().equals(id)){
                return c;
            }
        }
        return null;
    }

    public static boolean isLegalGender(String in){
        char c = in.charAt(0);
        if(c == '男' || c =='女'){
            return true;
        }
        return false;
    }

    /**
     * 判断输入的邮箱格式是否正确
     * @param str 输入的邮箱地址
     * @return 返回邮箱地址是否正确
     */
    public static boolean isMail(String str) {
        boolean flag = false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(str);
        if(m.matches()){
            flag = true;
        }
        return flag;
    }

    /**
     * 获取现在的日期时间
     *
     * @return 日期时间
     */
    public static LocalDateTime getDateTimeNow(){
        LocalDateTime time = LocalDateTime.now();
        return time;
    }

    /**
     * 以字符串的形式获取现在的日期时间
     *
     * @return 日期时间的字符串
     */
    public static String getStringOfDateTime(LocalDateTime ldt){
        return dtf.format(ldt);
    }

    public static boolean isLegalScore(String score){
        double i;
        try{
            i = Double.parseDouble(score);
        } catch (Exception e){
            return false;
        }
        return i >= 0 && i <= 100;
    }
    /**
     * 校验字符串是否为正确的学生学号
     * @param id 学生学号
     * @return 学号格式无误返回真，否则返回假
     */
    public static boolean isValidStudentId(String id){
        //如果id长度不等于12，说明不是学生的学号
        if (id.length() != 12){
            return false;
        }
        //获取不到专业名称，说明学号不正确
        if (getStudentMajorById(id) == null){
            return false;
        }
        //获取不到性别，说明学号不正确
        if (getStudentGenderById(id) == ' '){
            return false;
        }
        return true;
    }

    /**
     * 验证密码是否安全
     * @param pwd 密码
     * @return 安全返回真，否则返回假
     */
    public static boolean isSafePassword(String pwd){
        String regex = "^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]{8,18}$";
        if (pwd.matches(regex)){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isStudentChosenSpecificCourse(Student stu,Course cur){
        return false;
    }


    /**
     * 分页查询，特别支持课程表，学生表的分页查询
     * @param list DataHandler里的任一存储对象的ArrayList
     * @param size 每一页展示的数量
     * @param page 选择第几页+
     * @return
     */
    public static String[] pagedQuery(ArrayList<?> list, int size, int page){
        if (list.isEmpty()){ return null;}

        int maxPage = 0;
        if (list.size() % size == 0){
            maxPage = list.size() / size;
        } else {
            maxPage = (list.size() / size) + 1;
        }

        //如果页数超过最大页数，返回最大页数的内容
        if (page > maxPage){return pagedQuery(list,size,maxPage);}

        //如果页数小于1，返回最大页数的内容
        if (page < 1){return pagedQuery(list,size,1);}

        //如果查询的是最后一页，对字符串数组的大小作特殊处理
        String[] results = null;
        if (page == maxPage){
            if (list.size() % size == 0){
                results = new String[size];
            } else {
                results = new String[list.size() % size];
            }

        } else {
            results = new String[size];
        }
        //如果是查询教师
        if (list.get(0) instanceof Teacher){
            int i = (page - 1) * size; // 10个 size 3
            for (int j = 0; j < size; j++) {
                if (i == list.size()){
                    break;
                } else {
                    Teacher c = (Teacher) list.get(i);
                    results[j] = c.toStringLine();
                    i++;
                }
            }
            return results;
        }

        //如果是学生分页查询
        if (list.get(0) instanceof Student){
            int i = (page - 1) * size; // 10个 size 3
            for (int j = 0; j < size; j++) {
                if (i == list.size()){
                    break;
                } else {
                    Student c = (Student) list.get(i);
                    results[j] = c.toStringLine();
                    i++;
                }
            }
            return results;
        }

        //如果是课程分页查询
        if (list.get(0) instanceof Course){
            int i = (page - 1) * size; // 10个 size 3
            for (int j = 0; j < size; j++) {
                if (i == list.size()){
                    break;
                } else {
                    Course c = (Course) list.get(i);
                    results[j] = c.toStringLine();
                    i++;
                }
            }
            return results;
        }
        return null;
    }

    public static Teacher userChooseATeacher(){
        ArrayList<Teacher> teachers = DataHandler.getTeachers();
        ArrayList<Teacher> pagedTeachers = new ArrayList<>();
        int page = 1;
        //int command = -1;
        while (true){
            System.out.println();
            System.out.println("-------------------- 第" + page + "页 --------------------");
            //读取教师信息的字符串数组
            String[] r = Utils.pagedQuery(teachers,5,page);
            if (r != null) {
                //逐一输出
                int i = 1;
                for (String s : r) {
                    pagedTeachers.add((Teacher)Utils.getPersonById(s.substring(0,4)));
                    System.out.println(i + ". " + s);
                    i++;
                }
            }
            System.out.println("-------------------- 第" + page + "页 --------------------");
            System.out.println("输入 a 返回上一页 | 输入 d 进入下一页 | 输入 0 退出查询");
            System.out.println("请输入你选择的教师的序号");
            String teacherId = sc.next();
            if (teacherId.equals("0")){
                System.out.println("退出查询......");
                break;
            }
            Teacher s = null;
            //如果输入的是翻页指令，直接跳过下面的代码
            if (teacherId.equals("d") && page < (teachers.size() / 5) + 1){
                page++;
                continue;
            }
            //如果输入的是翻页指令，直接跳过下面的代码
            if (teacherId.equals("a") && page > 1){
                page--;
                continue;
            }
            try{
                s = pagedTeachers.get(Integer.parseInt((teacherId))-1);
            } catch (Exception e){
                System.out.println("你查找的教师不存在!");
                continue;
            }
            if (s == null){
                System.out.println("你查找的教师不存在！");
            } else {
                return s;
            }
        }
        return null;
    }

    public static Course userChooseCourse(){
        ArrayList<Course> courses = DataHandler.getCourses();
        ArrayList<Course> pagedCourses = new ArrayList<>();
        int page = 1;
        //int command = -1;
        while (true){
            System.out.println();
            System.out.println("--------------------- 第" + page + "页 ---------------------");
            //读取课程信息的字符串数组
            String[] r = Utils.pagedQuery(courses,5,page);
            if (r != null) {
                //逐一输出
                int i = 1;
                for (String s : r) {
                    pagedCourses.add(Utils.getCourseById(s.substring(0,6)));
                    System.out.println(i + ". " + s);
                    i++;
                }
            }
            System.out.println("--------------------- 第" + page + "页 ---------------------");
            System.out.println("输入 a 返回上一页 | 输入 d 进入下一页 | 输入 0 退出查询");
            System.out.println("请输入你选择的课程的序号：");
            String courseId = sc.next();
            if (courseId.equals("d") && page < (courses.size() / 5) + 1){
                page++;
                continue;//如果输入的是翻页指令，直接跳过下面的代码
            }
            if (courseId.equals("a") && page > 1){
                page--;
                continue;//如果输入的是翻页指令，直接跳过下面的代码
            }
            if (courseId.equals("0")){
                System.out.println("退出查询......");
                break;
            }
            Course c = null;
            try{
                c = pagedCourses.get(Integer.parseInt((courseId))-1);
            } catch (Exception e){
                System.out.println("你选择的课程不存在!");
                continue;
            }
            if (c == null){
                System.out.println("你选择的课程不存在！");
            } else {
                return c;
            }
        }
        return null;
    }

    public static Student userChooseAStudent(){
        ArrayList<Student> students = DataHandler.getStudents();
        ArrayList<Student> pagedStudents = new ArrayList<>();
        int page = 1;
        //int command = -1;
        while (true){
            System.out.println();
            System.out.println("--------------------------- 第" + page + "页 ----------------------------");
            //读取学生信息的字符串数组
            String[] r = Utils.pagedQuery(students,5,page);
            if (r != null) {
                //逐一输出
                int i = 1;
                for (String s : r) {
                    pagedStudents.add((Student)Utils.getPersonById(s.substring(0,12)));
                    System.out.println(i + ". " + s);
                    i++;
                }
            }
            System.out.println("---------------------------- 第" + page + "页 ----------------------------");
            System.out.println("输入 a 返回上一页 | 输入 d 进入下一页 | 输入 0 退出查询");
            System.out.println("请输入你选择的学生的序号：");
            String studentID = sc.next();
            if (studentID.equals("d") && page < (students.size() / 5) + 1){
                page++;
                continue;//如果输入的是翻页指令，直接跳过下面的代码
            }
            if (studentID.equals("a") && page > 1){
                page--;
                continue;//如果输入的是翻页指令，直接跳过下面的代码
            }
            if (studentID.equals("0")){
                System.out.println("退出查询......");
                break;
            }
            Student s = null;
            try{
                s = pagedStudents.get(Integer.parseInt(studentID)-1);
            } catch (Exception e){
                System.out.println("你查找的学生不存在!");
                continue;
            }
            if (s == null){
                System.out.println("你查找的学生不存在！");
            } else {
                return s;
            }
        }
        return null;
    }

    /**
     * 获取实体中含有关键词的实体合集
     * @param keyword 关键词
     * @param type 代表种类，1为学生，2为课程，3为教师
     * @return 所有含有关键词的实体对象的合集
     */
    public static ArrayList<Object> getFuzzySearchResultSet(String keyword,int type) {
        ArrayList<Object> results = new ArrayList<>();
        // 1. 学生
        if (type == 1){
            //输出时的序号
            int i = 1;
            //通过反射，获取student的全部变量
            Field[] fields = Student.class.getDeclaredFields();
            for(Student s: DataHandler.getStudents()){
                for (Field f : fields){
                    //不允许关键词匹配密码
                    if (f.getName().equals("studentPassword")){
                        continue;
                    }
                    // 检查字段的类型是否与要搜索的关键词类型匹配
                    if (f.getType().equals(keyword.getClass())) {
                        // 检查字段的值是否包含关键词
                        try {
                            //设置字段允许访问
                            f.setAccessible(true);
                            Object fieldValue = f.get(s);
                            if (fieldValue != null && fieldValue.toString().contains(keyword)) {
                                // 如果找到匹配的字段，且对象不重复，则将该对象添加到匹配对象列表中
                                if (!results.contains(s)){
                                    results.add((Student)s);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        else if (type == 2){
            //输出时的序号
            int i = 1;
            //通过反射，获取course的全部变量
            Field[] fields = Course.class.getDeclaredFields();
            for(Course s: DataHandler.getCourses()){
                for (Field f : fields){
                    //不允许匹配教师的密码
                    if(f.getName().equals("teacherPassword")){
                        continue;
                    }
                    // 检查字段的类型是否与要搜索的关键词类型匹配
                    if (f.getType().equals(keyword.getClass())) {
                        // 检查字段的值是否包含关键词
                        try {
                            //设置字段允许访问
                            f.setAccessible(true);
                            Object fieldValue = f.get(s);
                            if (fieldValue != null && fieldValue.toString().contains(keyword)) {
                                // 如果找到匹配的字段，且对象不重复，则将该对象添加到匹配对象列表中
                                if (!results.contains(s)){
                                    results.add((Course)s);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        else if (type == 3){
            //输出时的序号
            int i = 1;
            //通过反射，获取teacher的全部变量
            Field[] fields = Teacher.class.getDeclaredFields();
            for(Teacher s: DataHandler.getTeachers()){
                for (Field f : fields){
                    // 检查字段的类型是否与要搜索的关键词类型匹配
                    if (f.getType().equals(keyword.getClass())) {
                        // 检查字段的值是否包含关键词
                        try {
                            //设置字段允许访问
                            f.setAccessible(true);
                            Object fieldValue = f.get(s);
                            if (fieldValue != null && fieldValue.toString().contains(keyword)) {
                                // 如果找到匹配的字段，且对象不重复，则将该对象添加到匹配对象列表中
                                if (!results.contains(s)){
                                    results.add((Teacher)s);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return results;
    }

    @Test
    public static <T> ArrayList<T> Test(T t){
        Class<Student> studentClass = Student.class;
        Type stu = studentClass.getGenericSuperclass();
        if(t.getClass().equals(Student.class)){
            System.out.println("传入了学生");
        } else if(t.getClass().equals(Teacher.class)){
            System.out.println("传入了教师");
        }
        return null;
    }

    /**
     * 获取按绩点排序的学生合集
     *
     * @param condition 字符串，只含有0或1，代表排序条件
     * @return 返回符合条件的学生合集，如果返回null说明条件condition有误。
     */
    public static ArrayList<Student> getFilteredStudentByGPA(String condition){
        ArrayList<Student> filteredStudents = DataHandler.getStudents();
        //正序
        if (condition.equals("1")){
            filteredStudents.sort((o1,o2) -> Double.compare(o1.getGPA(),o2.getGPA()));

        } //倒序
        else if (condition.equals("2")) {
            filteredStudents.sort((o1,o2) -> Double.compare(o2.getGPA(),o1.getGPA()));
        } else {
            filteredStudents = null;
        }
        return filteredStudents;
    }

    /**
     * 获取按建档时间排序的学生合集
     *
     * @param condition 字符串，只含有0或1，代表排序条件
     * @return 返回符合条件的学生合集，如果返回null说明条件condition有误。
     */
    public static ArrayList<Student> getFilteredStudentByBuildTime(String condition){
        ArrayList<Student> filteredStudents = DataHandler.getStudents();
        //正序
        if (condition.equals("1")){
            filteredStudents.sort((o1,o2) -> o1.getBuildTime().compareTo(o2.getBuildTime()));

        } //倒序
        else if (condition.equals("2")) {
            filteredStudents.sort((o1,o2) -> o2.getBuildTime().compareTo(o1.getBuildTime()));
        } else {
            filteredStudents = null;
        }
        return filteredStudents;
    }
}
