package me.ziahh.sgm.bean;

public enum TeacherType {
    //区分教师账号的权限
    TEACHER("任课教师"),
    ADMIN("管理员");

    private final String name;

    TeacherType(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
