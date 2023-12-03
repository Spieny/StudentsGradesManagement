package me.ziahh.sgm.util;

import me.ziahh.sgm.bean.Student;
import me.ziahh.sgm.bean.Teacher;
import me.ziahh.sgm.module.DataHandler;

import javax.xml.crypto.Data;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class Utils {

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
}
