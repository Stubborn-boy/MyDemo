package com.example.mydemo.activity;

import android.Manifest;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.baselibrary.base.BaseActivity;
import com.example.mydemo.MainItemAdapter;
import com.example.mydemo.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 *
 * https://github.com/ZhongXiaoHong/superFileView
 */
public class OpenFileActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    RecyclerView mRecyclerView;
    private MainItemAdapter adapter;
    private String filePath;
    private List<String> datas = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_open_file;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainItemAdapter();
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        datas.add("打开本地doc文件");
        datas.add("打开本地txt文件");
        datas.add("打开本地excel文件");
        datas.add("打开本地ppt文件");
        datas.add("打开本地pdf文件");
        adapter.setNewData(datas);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String[] perms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        filePath = getFilePath(position);
//        if (!EasyPermissions.hasPermissions(OpenFileActivity.this, perms)) {
//            EasyPermissions.requestPermissions(OpenFileActivity.this, "需要访问手机存储权限！", 10086, perms);
//        } else {
//            FileDisplayActivity.show(OpenFileActivity.this, filePath);
//        }

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            // 权限通过后执行
                            FileDisplayActivity.start(OpenFileActivity.this, filePath);
                        } else {
                            // 拒绝权限后执行
                        }

                    }
                });
    }

    //https://github.com/ZhongXiaoHong/superFileView
    //现附上两个在线预览office文档的地址：(浏览器都需要chrome的内核)
    //1 google：https://docs.google.com/viewer?url=（输入你的文档在服务器中的地址）；
    //2 微软：https://view.officeapps.live.com/op/view.aspx?src=(输入你的文档在服务器中的地址)；
    //path = "http://www.hrssgz.gov.cn/bgxz/sydwrybgxz/201101/P020110110748901718161.doc";
    private String getFilePath(int position) {
        String path = null;
        switch (position) {
            case 0:
                path = Environment.getExternalStorageDirectory()+"/test/test.docx";
                break;
            case 1:
                path = "/storage/emulated/0/test/test.txt";
                break;
            case 2:
                path = "/storage/emulated/0/test/test.xlsx";
                break;
            case 3:
                path = "/storage/emulated/0/test/test.pptx";
                break;
            case 4:
                path = "/storage/emulated/0/test/test.pdf";
                break;
        }
        return path;
    }


}
