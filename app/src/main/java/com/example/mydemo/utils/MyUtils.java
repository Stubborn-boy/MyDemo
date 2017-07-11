package com.example.mydemo.utils;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public final class MyUtils {
    /**
     * 打开微信二维码扫描
     * @param context
     */
    public static void toWeChatScanDirect(Context context) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI"));
            intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
            intent.setFlags(335544320);
            intent.setAction("android.intent.action.VIEW");
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }
}
