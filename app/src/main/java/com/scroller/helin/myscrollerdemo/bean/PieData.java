package com.scroller.helin.myscrollerdemo.bean;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by helin on 2016/9/7 11:12.
 */
public class PieData {
    //名字
    private String name;
    //数值
    private float  value;
    //百分比
    private float percentage;
    //颜色
    private int color=0;

    //角度
    private float angle = 0;

    public ArrayList<String> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<String> datas) {
        this.datas = datas;
    }

    private ArrayList<String>  datas;

    //动画角度
    private float animAngle =0;

    public float getAnimAngle() {
        return animAngle;
    }

    public void setAnimAngle(float animAngle) {
        this.animAngle = animAngle;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }


    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }


    public PieData(@NonNull String name, @NonNull float value) {
        this.name = name;
        this.value = value;
    }

}
