package com.example.mydemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.mydemo.R;
import com.example.mydemo.view.StateButton;
import com.example.mydemo.view.WaveView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by demi on 2017/5/27.
 */

public class WaveActivity extends Activity {
    private ImageView head;
    private WaveView wave;
    private ScaleAnimation scaleAnimation;
    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        scaleAnimation = new ScaleAnimation(1.2f, 1f, 1.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setFillAfter(true);
        wave = (WaveView) findViewById(R.id.wave);
        head = (ImageView) findViewById(R.id.head);
        mPlayer = MediaPlayer.create(this, R.raw.water_wave);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wave.addWave();
                head.startAnimation(scaleAnimation);
                if(mPlayer.isPlaying()){
                    mPlayer.stop();
                    try {
                        mPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mPlayer.start();
            }
        });
        wave.start();

        StateButton btn_notify = (StateButton) findViewById(R.id.btn_notify);
        btn_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //http://www.jianshu.com/p/658c10bc8e3c
                //4.4以下并没有提过从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Intent intent = new Intent();
                    intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                    intent.putExtra("app_package", getPackageName());
                    intent.putExtra("app_uid", getApplicationInfo().uid);
                    startActivity(intent);
                } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                }
            }
        });
        StateButton btn_defail = (StateButton) findViewById(R.id.btn_defail);
        btn_defail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到应用详情页面
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 9) {
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    localIntent.setAction(Intent.ACTION_VIEW);
                    localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                    localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
                }
                startActivity(localIntent);
            }
        });

        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        StateButton btn_view2bitmap = (StateButton) findViewById(R.id.btn_view2bitmap);
        btn_view2bitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = getViewBitmap(scrollView);
                saveImg(bitmap);
            }
        });
    }

    //http://www.jianshu.com/p/7c6c43982d7a
    //view生成截图
    private Bitmap getViewBitmap(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap;
        // 获取scrollView实际高度,这里很重要
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    private void saveImg(Bitmap bitmap) {
        //把图片存储在哪个文件夹
        File file = new File(Environment.getExternalStorageDirectory(), "maozhua");
        if (!file.exists()) {
            file.mkdir();
        }
        //图片的名称
        String name = "mz.jpg";
        File file1 = new File(file, name);
        if (!file1.exists()){
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file1);
                //这个100表示压缩比,100说明不压缩,90说明压缩到原来的90%
                //注意:这是对于占用存储空间而言,不是说占用内存的大小
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //通知图库即使更新,否则不能看到图片
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file1.getAbsolutePath())));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        wave.setImageRadius(head.getWidth()/2);
    }

    public void click(View view) {
        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
    }


    //TextUtils.isEmpty(a)// 字符串是否为null或“”
    //TextUtils.isDigitsOnly(c)// 字符串是否全是数字
    //TextUtils.isGraphic(d)// 字符串是否含有可打印的字符 String d = " \n\t \b";
    //TextUtils.concat(a, b)// 拼接多个字符串
    //TextUtils.getTrimmedLength(b)// 去掉字符串前后两端空格(相当于trim())之后的长度
    //TextUtils.getReverse(b, 0, b.length())// 在字符串中，start和end之间字符的逆序
    //TextUtils.join("-", list)// 在数组中每个元素之间使用“-”来连接
    //TextUtils.split(e, "-");// 以"-"来分组
}
