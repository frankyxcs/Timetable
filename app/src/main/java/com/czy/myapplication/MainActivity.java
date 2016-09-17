package com.czy.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        TimetableView timetableView = (TimetableView) findViewById(R.id.time);
        List<Course> courseList = new ArrayList<>();

        Course course = new Course("黄浩川702 陈老师 1-16周", 1, 2);
        Course course1 = new Course("大学英语四级 北主楼1105 陈老师 1-18周", 2, 2);
        Course course2 = new Course("陈老师", 6, 4);
        Course course3 = new Course("陈老师", 1, 4);
        Course course4 = new Course("陈老师", 5, 3);
        Course course5 = new Course("陈老师", 3, 1);

        courseList.add(course);
        courseList.add(course1);
        courseList.add(course2);
        courseList.add(course3);
        courseList.add(course4);
        courseList.add(course5);

        timetableView.setCourseList(courseList);
    }
}
