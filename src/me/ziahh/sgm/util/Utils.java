package me.ziahh.sgm.util;

import me.ziahh.sgm.bean.Course;
import me.ziahh.sgm.bean.Student;
import me.ziahh.sgm.bean.Teacher;
import me.ziahh.sgm.module.DataHandler;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");

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

    public static String getStringOfDateTime(LocalDateTime ldt){
        return dtf.format(ldt);
    }
    /**
     * 校验字符串是否为正确的学生学号
     * @param id 学生学号
     * @return 学号格式无误返回真，否则返回假
     */
    public static boolean isValidStudentId(String id){
        //以后尝试使用正则表达式

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

}
