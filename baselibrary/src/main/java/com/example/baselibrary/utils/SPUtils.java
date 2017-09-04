package com.example.baselibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Created by 71541 on 2016/10/27.
 */
public class SPUtils {

    private static Context context;

    /**
     * 保存在手机里面的文件名
     */
    public static final String name = "mydemo";

    public static void init(Context mContext) {
        context = mContext;
    }

    public static Boolean getBoolean(String key) {
        SharedPreferences sp = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);

        return sp.getBoolean(key, false);
    }

    public static Boolean getBoolean(String key, Boolean defaultValue) {

        SharedPreferences sp = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);

        return sp.getBoolean(key, defaultValue);
    }

    public static void putBoolean(String key, Boolean value) {

        SharedPreferences sp = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }

    public static int getInt(String key) {
        SharedPreferences sp = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);

        return sp.getInt(key, 0);
    }

    public static int getInt(String key, int defaultValue) {

        SharedPreferences sp = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);

        return sp.getInt(key, defaultValue);
    }

    public static void putInt(String key, int num) {

        SharedPreferences sp = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, num);
        editor.commit();

    }

    public static Long getLong(String key) {
        SharedPreferences sp = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);

        return sp.getLong(key,0);
    }

    public static Long getLong(String key, Long defaultValue) {

        SharedPreferences sp = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);

        return sp.getLong(key, defaultValue);
    }

    public static void putLong(String key, Long num) {

        SharedPreferences sp = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, num);
        editor.commit();

    }

    public static String getString(String key) {

        SharedPreferences sp = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);

        return sp.getString(key, "");
    }

    public static String getString(String key, String defaultValue) {

        SharedPreferences sp = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);

        return sp.getString(key, defaultValue);
    }

    public static void putString(String key, String str) {

        SharedPreferences sp = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, str);
        editor.commit();
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String key)
    {
        SharedPreferences sp = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除所有数据
     *
     */
    public static void clear()
    {
        SharedPreferences sp = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean contains(String key)
    {
        SharedPreferences sp = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public static Map<String, ?> getAll()
    {
        SharedPreferences sp = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }
}
