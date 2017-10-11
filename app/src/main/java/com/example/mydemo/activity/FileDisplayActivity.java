package com.example.mydemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.baselibrary.base.BaseActivity;
import com.example.mydemo.R;
import com.example.mydemo.view.SuperFileView;

import java.io.File;


public class FileDisplayActivity extends BaseActivity {

    private String TAG = "FileDisplayActivity";
    SuperFileView mSuperFileView;
    String filePath;

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, FileDisplayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("path", url);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_file_display;
    }

    @Override
    protected void initView() {
        mSuperFileView = (SuperFileView) findViewById(R.id.mSuperFileView);
        mSuperFileView.setOnGetFilePathListener(new SuperFileView.OnGetFilePathListener() {
            @Override
            public void onGetFilePath(SuperFileView mSuperFileView2) {
                getFilePathAndShowFile(mSuperFileView2);
            }
        });

        Intent intent = this.getIntent();
        String path = (String) intent.getSerializableExtra("path");
        if (!TextUtils.isEmpty(path)) {
            setFilePath(path);
        }
        mSuperFileView.show();
    }

    @Override
    protected void initData() {

    }

    private void getFilePathAndShowFile(SuperFileView mSuperFileView) {
        mSuperFileView.displayFile(new File(getFilePath()));
    }

    public void setFilePath(String fileUrl) {
        this.filePath = fileUrl;
    }

    private String getFilePath() {
        return filePath;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSuperFileView != null) {
            mSuperFileView.onStopDisplay();
        }
    }
}
