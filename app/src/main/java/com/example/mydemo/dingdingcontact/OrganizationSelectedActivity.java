package com.example.mydemo.dingdingcontact;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baselibrary.base.BaseActivity;
import com.example.mydemo.R;
import com.example.mydemo.dingdingcontact.entity.BaseUserVo;
import com.example.mydemo.dingdingcontact.entity.EmpUserVo;
import com.example.mydemo.dingdingcontact.entity.OrgVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZTH-003 on 2017/5/22.
 */

public class OrganizationSelectedActivity extends BaseActivity implements OrganizationFragment.OrganizationCallBack {

    List<BaseUserVo> previousEmpList = new ArrayList<>();
    private List<EmpUserVo> tmpSelectEmpList;
    private TextView tv_selected_user;
    private TextView btn_finish;
    private boolean isSelected = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_organization;
    }

    private void initToobar() {
        setTitle("Demo1");
    }

    @Override
    protected void initView() {
        initToobar();
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

    @Override
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
