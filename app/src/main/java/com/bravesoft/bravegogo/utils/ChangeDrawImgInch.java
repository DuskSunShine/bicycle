package com.bravesoft.bravegogo.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Button;


/**
 * Created by SCY on 2017/3/14 9:48.
 */
public class ChangeDrawImgInch {
    /**
     * 改变button左的图片大小
     * @param context 上下文
     * @param id 要改变的图片id
     * @param v 那个button
     */
    public static void changeInch(Context context,int id, Button v){
        Drawable drawable =context.getResources().getDrawable(id);
        drawable.setBounds(0,0,50,50);
        v.setCompoundDrawables(drawable,null,null,null);
    }
    public static void changeInchRight(Context context,int id, Button v){
        Drawable drawable =context.getResources().getDrawable(id);
        drawable.setBounds(0,0,35,35);
        v.setCompoundDrawables(null,null,drawable,null);
    }
}
