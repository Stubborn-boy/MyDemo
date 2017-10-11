package com.example.mydemo;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.baselibrary.base.BaseActivity;
import com.example.mydemo.activity.OpenFileActivity;
import com.example.mydemo.activity.PswActivity;
import com.example.mydemo.activity.WaveActivity;
import com.example.mydemo.dingdingcontact.ContactsSelectedActivity;
import com.example.mydemo.dingdingcontact.OrganizationSelectedActivity;
import com.example.mydemo.loginsmooth.LoginSmoothActivity;
import com.example.mydemo.view.RecyclerViewItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MainItemAdapter adapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
                //.color(Color.RED)
                .color("#eeeeee")
//                .dashWidth(8)
//                .dashGap(5)
                .thickness(1)
                //.drawableID(R.drawable.diver)
                //.paddingStart(20)
                //.paddingEnd(10)
                //.firstLineVisible(true)
                .lastLineVisible(true)
                .create());

        adapter = new MainItemAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        list.add("demo1");
        list.add("demo2");
        list.add("demo3");
        list.add("demo4");
        list.add("demo5");
        list.add("demo6");
        adapter.setNewData(list);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent;
        switch (position){
            case 0:
                intent = new Intent(MainActivity.this, OrganizationSelectedActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(MainActivity.this, ContactsSelectedActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(MainActivity.this, LoginSmoothActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(MainActivity.this, PswActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(MainActivity.this, WaveActivity.class);
                startActivity(intent);
                break;
            case 5:
                intent = new Intent(MainActivity.this, OpenFileActivity.class);
                startActivity(intent);
                break;
        }
    }
}
