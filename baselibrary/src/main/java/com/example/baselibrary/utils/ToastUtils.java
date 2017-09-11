package com.example.baselibrary.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

/** 
 * Toast统一管理类 
 */ 
public class ToastUtils {

    private static Context context;
    public static boolean isShow = true;
    private static Toast mToast;
    private static Handler mHandler;

    public static void init(Context mContext) {
        context = mContext;
    }

    /**
     * 自定义显示Toast时间
     *
     * @param text
     * @param duration
     */
    public static void showToast(final CharSequence text, final int duration) {
        if(context == null){
            return;
        }
        if(Looper.myLooper()!=Looper.getMainLooper()){
            if (mHandler == null) {
                mHandler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        String str = (String) msg.obj;
                        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                        super.handleMessage(msg);
                    }
                };
            }
            Message msg = mHandler.obtainMessage();
            msg.obj = text;
            msg.sendToTarget();
        }else {
            if(mToast == null){
                mToast = Toast.makeText(context, text, duration);
            }else {
                mToast.setText(text);
                mToast.setDuration(duration);
            }
            mToast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param resid
     * @param duration
     */
    public static void showToast(final int resid, final int duration) {
        if (null == context) {
			return;
		}
        String text = context.getResources().getString(resid);
        if(!TextUtils.isEmpty(text)) {
            showToast(text, duration);
        }
    }


    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        if (isShow)
            //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            showToast(message, Toast.LENGTH_SHORT);

    } 
 
    /** 
     * 短时间显示Toast 
     *
     * @param message 
     */ 
    public static void showShort(int message) {
        if (isShow)
            //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            showToast(message, Toast.LENGTH_SHORT);
    } 
 
    /** 
     * 长时间显示Toast 
     *
     * @param message 
     */ 
    public static void showLong(CharSequence message) {
        if (isShow)
            //Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            showToast(message, Toast.LENGTH_LONG);
    } 
 
    /** 
     * 长时间显示Toast 
     *
     * @param message 
     */ 
    public static void showLong(int message) {
        if (isShow)
            //Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            showToast(message, Toast.LENGTH_LONG);
    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}