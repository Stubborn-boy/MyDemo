package com.example.mydemo.dingdingcontact;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
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

public class ContactsSelectedActivity extends BaseActivity implements CompanyFragment.OrganizationCallBack, FriendSelectedFragment.FriendSelectedCallBack {

    private LinearLayout ll_company;
    private LinearLayout ll_person;
    private TextView tv_selected_user;
    private TextView btn_finish;

    private CompanyFragment companyFragment;
    private FriendSelectedFragment friendSelectedFragment;
    private List<BaseUserVo> previousEmpList = new ArrayList<>();
    private List<EmpUserVo> tmpSelectEmpList = new ArrayList<>();
    private List<OrgVo> tmpSelectOrgList = new ArrayList<>();
    private boolean isSelected = false;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_contacts_selected;
    }

    @Override
    protected void initView() {
        ll_company = (LinearLayout) findViewById(R.id.ll_compny);
        ll_person = (LinearLayout) findViewById(R.id.ll_person);
        tv_selected_user = (TextView) findViewById(R.id.tv_selected_user);
        btn_finish = (TextView) findViewById(R.id.btn_finish);
        //companyFragment = new CompanyFragment();
//        EmpUserVo empUserVo = new EmpUserVo();
//        empUserVo.setUid(MarketUtils.getUserInfo(context).getUid());
//        empUserVo.setAccount(MarketUtils.getUserInfo(context).getAccount());
//        previousEmpList.add(empUserVo);
        //companyFragment.setPreviousEmpList(previousEmpList);
        //getSupportFragmentManager().beginTransaction().add(R.id.container, companyFragment).commit();
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

        ll_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyFragment = CompanyFragment.getInstance();
                companyFragment.setPreviousEmpList(previousEmpList);
                companyFragment.setSelectEmpList(tmpSelectEmpList);
                showFragment(companyFragment);
            }
        });

        ll_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendSelectedFragment = FriendSelectedFragment.getInstance();
                friendSelectedFragment.setPreviousEmpList(previousEmpList);
                friendSelectedFragment.setSelectEmpList(tmpSelectEmpList);
                showFragment(friendSelectedFragment);
            }
        });
    }

    public void showFragment(Fragment fragment){
        if(!fragment.isAdded()){
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
        }
        getSupportFragmentManager().beginTransaction().show(fragment).commit();
    }


    public void hideFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().hide(fragment).commit();
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
        tmpSelectOrgList = selectOrgList;
//        if (selectEmpList.size() >= 1) {
//            isSelected = true;
//        } else {
//            isSelected = false;
//        }
//        if (selectEmpList.size() > 0 && selectOrgList.size() == 0) {
//            tv_selected_user.setText("已选择:" + selectEmpList.size() + "人");
//        } else if (selectOrgList.size() > 0 && selectEmpList.size() == 0 || selectEmpList.size() > 0 && selectOrgList.size() > 0) {
//            tv_selected_user.setText("已选择:" + selectEmpList.size() + "人," + "其中有" + selectOrgList.size() + "个部门(含子部门)");
//        } else {
//            tv_selected_user.setText("已选择:" );
//        }
//        if (selectEmpList.size() >= 0)
//            btn_finish.setText("确定" + "(" + selectEmpList.size() + "/500)");

        updateText(tmpSelectOrgList, tmpSelectEmpList);
    }

    @Override
    public void selectFriendEmpUser(List<EmpUserVo> selectEmpList) {
        if (selectEmpList == null) {
            return;
        }
        if (selectEmpList.size() > 500) {
            Toast.makeText(context, "人数最多只能选择500人!", Toast.LENGTH_SHORT).show();
            return;
        }
        tmpSelectEmpList = selectEmpList;
        updateText(tmpSelectOrgList, tmpSelectEmpList);
    }

    private void updateText(List<OrgVo> selectOrgList, List<EmpUserVo> selectEmpList){
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
