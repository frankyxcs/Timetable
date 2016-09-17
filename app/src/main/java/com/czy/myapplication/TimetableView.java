package com.czy.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/**
 * 课程表控件
 * Created by ZY on 2016/9/17.
 */
public class TimetableView extends LinearLayout {

    private Context context;

    private List<Course> courseList;

    private final String[] days = {"一", "二", "三", "四", "五", "六", "日"};

    private final String[] ranks = {"第一大节", "第二大节", "第三大节", "第四大节", "晚上"};

    private final int[] colors = {Color.parseColor("#4AC6FC"), Color.parseColor("#00BCD4"),
            Color.parseColor("#009688"), Color.parseColor("#6D80BA"),
            Color.parseColor("#FF5722"), Color.parseColor("#A39709"),
            Color.parseColor("#6ABD6C")};

    //表示星期几的第一行文本的高度
    private final int DAY_TEXT_HEIGHT = 35;

    //表示课程节数的第一列文本的宽度
    private final int RANK_TEXT_WIDTH = 24;

    //代表每门课程的View的高度
    private final int TIMETABLE_HEIGHT = 120;

    //一周要显示几天
    private final int DAY_TOTAL = 7;

    //每天课程最大节数
    private final int COURSE_TOTAL = 5;

    //分界线的直径
    private final int LINE_DIAMETER = 1;

    public TimetableView(Context context) {
        super(context);
        this.context = context;
    }

    public TimetableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public TimetableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
        removeAllViews();
        initView();
        invalidate();
    }

    public void initView() {
        //主布局
        LinearLayout timetableLinearLayout = new LinearLayout(context);
        timetableLinearLayout.setOrientation(HORIZONTAL);

        //先来绘制第一列代表节数的LinearLayout
        LinearLayout firstLayout = new LinearLayout(context);
        firstLayout.setOrientation(VERTICAL);
        LayoutParams params = new LayoutParams(dip2px(RANK_TEXT_WIDTH), ViewGroup.LayoutParams.WRAP_CONTENT);
        firstLayout.setLayoutParams(params);

        //第一个空白格
        TextView tv_blankText = new TextView(context);
        tv_blankText.setWidth(dip2px(RANK_TEXT_WIDTH));
        tv_blankText.setHeight(dip2px(DAY_TEXT_HEIGHT));
        firstLayout.addView(tv_blankText);
        //添加一条横向分隔线
        firstLayout.addView(getTransverseLine(dip2px(RANK_TEXT_WIDTH)));
        //添加代表节数的文本
        for (int i = 0; i < COURSE_TOTAL; i++) {
            TextView tv_rankText = new TextView(context);
            tv_rankText.setWidth(dip2px(RANK_TEXT_WIDTH));
            tv_rankText.setHeight(dip2px(TIMETABLE_HEIGHT));
            tv_rankText.setGravity(Gravity.CENTER);
            tv_rankText.setText(ranks[i]);
            firstLayout.addView(tv_rankText);
            firstLayout.addView(getTransverseLine(dip2px(RANK_TEXT_WIDTH)));
        }
        //向主布局添加第一列
        timetableLinearLayout.addView(firstLayout);

        //继续添加星期一到星期日的每一列
        LinearLayout layout;
        TextView tv_day;
        Random random = new Random();
        LayoutParams layoutParams = new LayoutParams(getViewWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < DAY_TOTAL; i++) {
            layout = new LinearLayout(context);
            layout.setOrientation(VERTICAL);
            layout.setLayoutParams(layoutParams);
            //第一行代表星期几的内容
            tv_day = new TextView(context);
            tv_day.setWidth(getViewWidth());
            tv_day.setHeight(dip2px(DAY_TEXT_HEIGHT));
            tv_day.setGravity(Gravity.CENTER);
            tv_day.setText(days[i]);

            layout.addView(tv_day);
            layout.addView(getTransverseLine(getViewWidth()));

            for (int j = 0; j < COURSE_TOTAL; j++) {
                TextView tv_timetable = new TextView(context);
                tv_timetable.setWidth(getViewWidth());
                tv_timetable.setHeight(dip2px(TIMETABLE_HEIGHT));
                tv_timetable.setGravity(Gravity.CENTER);
                tv_timetable.setTextColor(Color.WHITE);
                tv_timetable.setBackgroundColor(Color.parseColor("#E1DFE0"));
                for (Course course : courseList) {
                    if (course.getWeek() == i + 1 && course.getRank() == j + 1) {
                        tv_timetable.setText(course.getCourseIntroduce());
                        tv_timetable.setBackgroundColor(colors[random.nextInt(colors.length)]);
                    }
                }
                layout.addView(tv_timetable);
                layout.addView(getTransverseLine(getViewWidth()));
            }
            timetableLinearLayout.addView(getVerticalLine(dip2px(COURSE_TOTAL * TIMETABLE_HEIGHT + DAY_TEXT_HEIGHT + LINE_DIAMETER * (COURSE_TOTAL + 1))));
            timetableLinearLayout.addView(layout);
        }
        addView(timetableLinearLayout);
    }


    /**
     * 将dp单位转换为px单位
     *
     * @param value dp值
     * @return px值
     */
    public int dip2px(float value) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (value * scale + 0.5f);
    }

    /**
     * 计算每个包含课程信息的TextView的宽度
     *
     * @return TextView的宽度
     */
    private int getViewWidth() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return (point.x - dip2px(RANK_TEXT_WIDTH + DAY_TOTAL * LINE_DIAMETER)) / DAY_TOTAL;
    }

    /**
     * 获取横向分界线
     *
     * @param length 分界线长度
     * @return 分界线
     */
    private View getTransverseLine(int length) {
        TextView line = new TextView(context);
        line.setWidth(length);
        line.setHeight(dip2px(LINE_DIAMETER));
        line.setBackgroundColor(Color.parseColor("#54474e"));
        return line;
    }

    /**
     * 获取竖向分界线
     *
     * @param length 分界线长度
     * @return 分界线
     */
    private View getVerticalLine(int length) {
        TextView line = new TextView(context);
        line.setWidth(dip2px(LINE_DIAMETER));
        line.setHeight(length);
        line.setBackgroundColor(Color.parseColor("#54474e"));
        return line;
    }

}
