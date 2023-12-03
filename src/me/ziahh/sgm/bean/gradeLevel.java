package me.ziahh.sgm.bean;

public enum gradeLevel {
    //100-90分
    EXCELLENT("优秀"),
    //89-80分
    GOOD("良好"),
    //79-60分
    PASS("及格"),
    //60分以下
    FAIL("不及格");

    private final String name;

    gradeLevel(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
