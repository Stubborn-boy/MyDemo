package com.example.mydemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydemo.R;
import com.example.mydemo.entity.BaseUserVo;
import com.example.mydemo.entity.EmpUserVo;
import com.example.mydemo.entity.OrgVo;
import com.example.mydemo.fragment.OrganizationFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZTH-003 on 2017/5/22.
 */

public class OrganizationSelectedActivity extends AppCompatActivity implements OrganizationFragment.OrganizationCallBack {

    List<BaseUserVo> previousEmpList = new ArrayList<>();
    private List<EmpUserVo> tmpSelectEmpList;
    private TextView tv_selected_user;
    private TextView btn_finish;
    private boolean isSelected = false;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);
        context = this;
        initView();
        initData();
    }

    protected void initView() {
        tv_selected_user = (TextView) findViewById(R.id.tv_selected_user);
        btn_finish = (TextView) findViewById(R.id.btn_finish);
        OrganizationFragment organizationFragment = new OrganizationFragment();
//        EmpUserVo empUserVo = new EmpUserVo();
//        empUserVo.setUid(MarketUtils.getUserInfo(context).getUid());
//        empUserVo.setAccount(MarketUtils.getUserInfo(context).getAccount());
//        previousEmpList.add(empUserVo);
        organizationFragment.setPreviousEmpList(previousEmpList);
        getSupportFragmentManager().beginTransaction().add(R.id.container, organizationFragment).commit();
    }

    protected void initData() {
        btn_finish.setText("确定(0/500)");
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelected) {
//                    if(EmailReplyActivity.handler != null){
//                        Message msg = Message.obtain();
//                        msg.obj = tmpSelectEmpList;
//                        msg.what = MarketApp.HANDLER_MESS_ZERO;
//                        EmailReplyActivity.handler.sendMessage(msg);
//                    }
//                    finish();
                } else {
                    Toast.makeText(context, "未选择任何联系人!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void selectOrgEmpUser(List<OrgVo> selectOrgList, List<EmpUserVo> selectEmpList) {
        if (selectEmpList == null) {
            return;
        }
        if (selectEmpList.size() > 500) {
            Toast.makeText(context, "人数最多只能选择500人!", Toast.LENGTH_SHORT).show();
            return;
        }
        tmpSelectEmpList = selectEmpList;
        if (selectEmpList.size() >= 1) {
            isSelected = true;
        } else {
            isSelected = false;
        }
        if (selectEmpList.size() > 0 && selectOrgList.size() == 0) {
            tv_selected_user.setText("已选择:" + selectEmpList.size() + "人");
        } else if (selectOrgList.size() > 0 && selectEmpList.size() == 0 || selectEmpList.size() > 0 && selectOrgList.size() > 0) {
            tv_selected_user.setText("已选择:" + selectEmpList.size() + "人," + "其中有" + selectOrgList.size() + "个部门(含子部门)");
        } else {
            tv_selected_user.setText("已选择:" );
        }
        if (selectEmpList.size() >= 0)
            btn_finish.setText("确定" + "(" + selectEmpList.size() + "/500)");
    }
}
