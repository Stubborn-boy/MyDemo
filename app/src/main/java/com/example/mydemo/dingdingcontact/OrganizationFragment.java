package com.example.mydemo.dingdingcontact;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.baselibrary.utils.LogUtils;
import com.example.baselibrary.utils.SizeUtils;
import com.example.mydemo.R;
import com.example.mydemo.dingdingcontact.entity.AllVo;
import com.example.mydemo.dingdingcontact.entity.BaseUserVo;
import com.example.mydemo.dingdingcontact.entity.EmpUserVo;
import com.example.mydemo.dingdingcontact.entity.OrgVo;
import com.example.mydemo.dingdingcontact.entity.ResultVo;
import com.example.mydemo.http.RetrofitClient;
import com.example.mydemo.view.RecyclerViewItemDecoration;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;


/**
 * Created by 71541 on 2017/5/22.
 */

public class OrganizationFragment extends Fragment implements BaseQuickAdapter.OnItemClickListener, OrgContactAdapter.OnSubordinateClickListener{

    HorizontalScrollView horizontalScrollView;
    LinearLayout ll_shortcut;
    RecyclerView recyclerView;

    private Context context;
    private LinearLayoutManager linearLayoutManager;
    private OrgContactAdapter adapter;
    private List<MultiItemEntity> list = new ArrayList<>();
    private List<OrgVo> orgList = new ArrayList<>();
    private List<EmpUserVo> empList = new ArrayList<>();
    private List<AllVo> selectAllList = new ArrayList<>();
    private List<OrgVo> selectOrgList = new ArrayList<>();
    private List<EmpUserVo> selectEmpList = new ArrayList<>();
    private List<BaseUserVo> previousEmpList = new ArrayList<>();
    private AllVo curAllVo;
    private OrganizationCallBack organizationCallBack;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        organizationCallBack = (OrganizationCallBack)activity;
    }

    public interface OrganizationCallBack{
        void selectOrgEmpUser(List<OrgVo> selectOrgList, List<EmpUserVo> selectEmpList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_org_contact, null, false);
        horizontalScrollView = (HorizontalScrollView)view.findViewById(R.id.horizontalScrollView);
        ll_shortcut = (LinearLayout)view.findViewById(R.id.ll_shortcut);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        intview();
        initDate();
        return view;
    }

    protected void intview() {
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new RecyclerViewItemDecoration.Builder(context)
                .color("#eeeeee")
                .thickness(2)
                .lastLineVisible(true)
                .create());
        adapter = new OrgContactAdapter(list, previousEmpList, selectAllList, selectOrgList, selectEmpList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnSubordinateClickListener(this);
    }

    protected void initDate() {
        getOrgContacts("");
    }

    private void addView2HorizontalScrollView(OrgVo orgVo){
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams ll_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(ll_layoutParams);
        int count = ll_shortcut.getChildCount();
        if(count>0) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.common_arrow);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            imageView.setLayoutParams(layoutParams);
            linearLayout.addView(imageView);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(context);
        textView.setText(orgVo.getName());
        textView.setTextColor(Color.parseColor("#999999"));
        textView.setEnabled(false);
        if(count>0) {
            LinearLayout linearLayout1 = (LinearLayout) ll_shortcut.getChildAt(count-1);
            TextView textView1 = (TextView) linearLayout1.getChildAt(linearLayout1.getChildCount()-1);
            textView1.setTextColor(Color.parseColor("#00A0E9"));
            textView1.setEnabled(true);
        }
        int padding = SizeUtils.dp2px(context, 10);
        textView.setPadding(padding, padding, padding, padding);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);

        textView.setLayoutParams(layoutParams);
        textView.setTag(orgVo);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTextView((TextView) v);
            }
        });
        linearLayout.addView(textView);
        ll_shortcut.addView(linearLayout);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                horizontalScrollView.fullScroll(View.FOCUS_RIGHT);
            }
        });
    }

    private void clickTextView(TextView tv){
        tv.setTextColor(Color.parseColor("#999999"));
        tv.setEnabled(false);
        OrgVo orgVo = (OrgVo) tv.getTag();
        int index = ll_shortcut.indexOfChild((View) tv.getParent());
        int count  = ll_shortcut.getChildCount();
        ll_shortcut.removeViews(index+1,count-(index+1));
        getOrgContacts(orgVo.getId());
    }

    private void getOrgContacts(String code){
        list.clear();
        orgList.clear();
        empList.clear();
        adapter.notifyDataSetChanged();

        RetrofitClient.getInstance(context).getApiService()
                .getOrgContacts("", code, "1213", "311f4f508d776b115827e76bd11ae724")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceSubscriber<ResultVo>() {
                    @Override
                    public void onNext(ResultVo resultVo) {
                        Gson gson = new Gson();
                        String json = gson.toJson(resultVo.getMsg());
                        OrgVo orgVo = gson.fromJson(json, OrgVo.class);
                        if(orgVo!=null) {
                            if(ll_shortcut.getChildCount()==0) {
                                addView2HorizontalScrollView(orgVo);
                            }
                            handleData(orgVo);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.e(t.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void handleData(OrgVo orgVo) {
        curAllVo = new AllVo();
        curAllVo.setOrgId(orgVo.getId());
        list.add(curAllVo);
        orgList.addAll(orgVo.getOrgVOs());
        list.addAll(orgList);
        empList.addAll(orgVo.getUsers());
        list.addAll(empList);
        adapter.setNewData(list);

        if(selectAllList.contains(curAllVo)) {
            if(orgList.size()>0){
                if(!selectOrgList.contains(orgList.get(0))){
                    selectAllList.remove(curAllVo);
                }
            }else if(empList.size()>0){
                if(!selectEmpList.contains(empList.get(0))){
                    selectAllList.remove(curAllVo);
                }
            }
        }
    }

    /**
     * 条目点击事件
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter.getData().size() == 0 || position < 0) {
            return;
        }
        MultiItemEntity entity = (MultiItemEntity) adapter.getData().get(position);
        CheckBox checkbox = (CheckBox) view.findViewById(R.id.checkbox);
        checkbox.setChecked(!checkbox.isChecked());
        switch (entity.getItemType()){
            case OrgContactAdapter.ALL:
                AllVo allVo = (AllVo) entity;
                if(checkbox.isChecked()){
                    selectAllList.add(allVo);

                    for(OrgVo orgVo : orgList){
                        if(!selectOrgList.contains(orgVo)){
                            selectOrgList.add(orgVo);
                            selectAllUserByOrg(orgVo);
                        }
                    }

                    for(EmpUserVo empUserVo : empList){
                        if(!selectEmpList.contains(empUserVo)&&!previousEmpList.contains(empUserVo)){
                            selectEmpList.add(empUserVo);
                        }
                    }
                }else{
                    selectAllList.remove(allVo);

                    for(OrgVo orgVo : orgList){
                        if(selectOrgList.contains(orgVo)){
                            selectOrgList.remove(orgVo);
                            unSelectAllUserByOrg(orgVo);
                        }
                    }

                    for(EmpUserVo empUserVo : empList){
                        if(selectEmpList.contains(empUserVo)){
                            selectEmpList.remove(empUserVo);
                        }
                    }
                }
                refreshSelectText();
                break;

            case OrgContactAdapter.ORG:
                OrgVo orgVo = (OrgVo) entity;
                if(checkbox.isChecked()){
                    if(!selectOrgList.contains(orgVo)) {
                        selectOrgList.add(orgVo);
                        selectAllUserByOrg(orgVo);
                    }
                }else{
                    if(selectOrgList.contains(orgVo)) {
                        selectOrgList.remove(orgVo);
                        unSelectAllUserByOrg(orgVo);
                    }
                    if(selectAllList.contains(curAllVo)) {
                        selectAllList.remove(curAllVo);
                    }
                }
                refreshSelectText();
                break;

            case OrgContactAdapter.EMP:
                EmpUserVo empUserVo = (EmpUserVo) entity;
                if(checkbox.isChecked()){
                    if(!selectEmpList.contains(empUserVo)&&!previousEmpList.contains(empUserVo)) {
                        selectEmpList.add(empUserVo);
                    }
                }else{
                    if(selectEmpList.contains(empUserVo)) {
                        selectEmpList.remove(empUserVo);
                    }
                    if(selectAllList.contains(curAllVo)) {
                        selectAllList.remove(curAllVo);
                    }
                }
                refreshSelectText();
                break;
        }
    }

    private void refreshSelectText() {
        if(organizationCallBack!=null){
            organizationCallBack.selectOrgEmpUser(selectOrgList, selectEmpList);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 点击下级响应事件
     * @param helper
     * @param v
     * @param position
     */
    @Override
    public void onSubordinateClick(BaseViewHolder helper, View v, int position) {
        OrgVo orgVo = null;
        if (adapter.getData().size() > 0 && position >= 0) {
            orgVo = (OrgVo) adapter.getData().get(position);
        }
        if (orgVo == null)
            return;
        addView2HorizontalScrollView(orgVo);
        getOrgContacts(orgVo.getId());
    }

    /**
     * 选择部门下所有人员
     * @param orgVo
     */
    private void selectAllUserByOrg(OrgVo orgVo){
        ArrayList<OrgVo> orgList = orgVo.getOrgVOs();
        ArrayList<EmpUserVo> userList = orgVo.getUsers();
        for(EmpUserVo empUserVo : userList){
            if(!selectEmpList.contains(empUserVo)&&!previousEmpList.contains(empUserVo)) {
                selectEmpList.add(empUserVo);
            }
        }
        for(OrgVo vo : orgList){
            selectAllUserByOrg(vo);
        }
    }

    /**
     * 取消选择部门下所有人员
     * @param orgVo
     */
    private void unSelectAllUserByOrg(OrgVo orgVo){
        ArrayList<OrgVo> orgList = orgVo.getOrgVOs();
        ArrayList<EmpUserVo> userList = orgVo.getUsers();
        for(EmpUserVo empUserVo : userList){
            if(selectEmpList.contains(empUserVo)) {
                selectEmpList.remove(empUserVo);
            }
        }
        for(OrgVo vo : orgList){
            unSelectAllUserByOrg(vo);
            if(selectOrgList.contains(vo)) {
                selectOrgList.remove(vo);
            }
        }
    }

    /**
     * 设置之前已经选择的
     * @param previousEmpList
     */
    public void setPreviousEmpList(List<BaseUserVo> previousEmpList){
        this.previousEmpList = previousEmpList;
    }
}
