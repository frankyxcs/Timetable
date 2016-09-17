package com.czy.myapplication;

/**
 * 课程实体，在查询课表时使用
 * Created by ZY on 2016/9/8.
 */
public class Course {

    /**
     * 科目、周数、地点、老师等信息
     */
    private String courseIntroduce;

    /**
     * 星期几
     */
    private int week;

    /**
     * 第几节课
     */
    private int rank;

    public Course(String courseIntroduce, int week, int rank) {
        this.courseIntroduce = courseIntroduce;
        this.week = week;
        this.rank = rank;
    }

    public String getCourseIntroduce() {
        return courseIntroduce;
    }

    public int getWeek() {
        return week;
    }

    public int getRank() {
        return rank;
    }

}
