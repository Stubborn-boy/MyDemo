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
import android.text.TextUtils;
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
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.baselibrary.utils.LogUtils;
import com.example.mydemo.R;
import com.example.mydemo.dingdingcontact.entity.BaseUserVo;
import com.example.mydemo.dingdingcontact.entity.EmpUserVo;
import com.example.mydemo.dingdingcontact.entity.FriendMesVo;
import com.example.mydemo.dingdingcontact.entity.OrgVo;
import com.example.mydemo.dingdingcontact.entity.PageDateVo;
import com.example.mydemo.dingdingcontact.entity.ResultVo;
import com.example.mydemo.utils.SizeUtils;
import com.example.mydemo.view.RecyclerViewItemDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by 71541 on 2017/5/22.
 */

public class FriendSelectedFragment extends Fragment implements BaseQuickAdapter.OnItemClickListener {

    HorizontalScrollView horizontalScrollView;
    LinearLayout ll_shortcut;
    RecyclerView recyclerView;

    private Context context;
    private LinearLayoutManager linearLayoutManager;
    private FriendContactAdapter adapter;
    private List<MultiItemEntity> list = new ArrayList<>();
    private List<EmpUserVo> empList = new ArrayList<>();
    private List<EmpUserVo> selectEmpList = new ArrayList<>();
    private List<BaseUserVo> previousEmpList = new ArrayList<>();
    private FriendSelectedCallBack friendSelectedCallBack;

    private static FriendSelectedFragment instance;
    public static FriendSelectedFragment getInstance(){
        if(instance==null){
            instance = new FriendSelectedFragment();
        }
        return instance;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        friendSelectedCallBack = (FriendSelectedCallBack)activity;
    }

    public interface FriendSelectedCallBack{
        void selectFriendEmpUser(List<EmpUserVo> selectEmpList);
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
        adapter = new FriendContactAdapter(list, previousEmpList, selectEmpList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        OrgVo vo = new OrgVo();
        vo.setId("contact");
        vo.setName("联系人");
        addView2HorizontalScrollView(vo);
    }

    protected void initDate() {
        getOrgContacts("");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            getOrgContacts("");
        }
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
        if(orgVo.getId().equals("contact")){
            textView.setTextColor(Color.parseColor("#00A0E9"));
            textView.setEnabled(true);
        }
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
        if(!orgVo.getId().equals("contact")) {
            getOrgContacts(orgVo.getId());
        } else{
            tv.setTextColor(Color.parseColor("#00A0E9"));
            tv.setEnabled(true);
            list.clear();
            adapter.setNewData(list);
            ((ContactsSelectedActivity)getActivity()).hideFragment(this);
        }
    }

    private void getOrgContacts(String code){
        list.clear();
        empList.clear();
        adapter.notifyDataSetChanged();
        String url = "http://101.201.108.172:8280/masCustomer/service/UserFriendService/userFriendList";
        OkGo.post(url)
                .tag(this)
                .params("uid", "1213")
                .params("currentPageNO", "1")
                .params("pageSize", "5000")
                .params("token", "311f4f508d776b115827e76bd11ae724")
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        LogUtils.e(e.toString());
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        ResultVo resultVo = gson.fromJson(s, ResultVo.class);
                        String json = gson.toJson(resultVo.getMsg());
                        if(TextUtils.isEmpty(resultVo.getMsg().toString())){
                            json = "{}";
                        }
                        TypeToken<PageDateVo<FriendMesVo>> typeToken = new TypeToken<PageDateVo<FriendMesVo>>() {};
                        PageDateVo<FriendMesVo> pageDataVo = gson.fromJson(json, typeToken.getType());
                        ArrayList<FriendMesVo> dataList = null;
                        if(pageDataVo!=null){
                            dataList = pageDataVo.getDateList();
                            if(dataList!=null){
                                if(ll_shortcut.getChildCount()==1) {
                                    OrgVo vo = new OrgVo();
                                    vo.setId("1");
                                    vo.setName("我的联系人");
                                    addView2HorizontalScrollView(vo);
                                }
                                for(FriendMesVo vo : dataList){
                                    EmpUserVo empUserVo = new EmpUserVo();
                                    empUserVo.setUid(vo.getFriendId());
                                    empUserVo.setAccount(vo.getFriendAccount());
                                    empUserVo.setUserName(vo.getFriendName());
                                    empUserVo.setPicture(vo.getPicture());
                                    empList.add(empUserVo);
                                }
                                handleData();
                            }
                        }
                    }
                });
    }

    private void handleData() {
        list.addAll(empList);
        adapter.setNewData(list);
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
                }
                refreshSelectText();
                break;
        }
    }

    private void refreshSelectText() {
        if(friendSelectedCallBack!=null){
            friendSelectedCallBack.selectFriendEmpUser(selectEmpList);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 设置之前已经选择的
     * @param previousEmpList
     */
    public void setPreviousEmpList(List<BaseUserVo> previousEmpList){
        this.previousEmpList = previousEmpList;
    }

    /**
     * 设置之前已经选择的
     * @param tmpSelectEmpList
     */
    public void setSelectEmpList(List<EmpUserVo> tmpSelectEmpList){
        this.selectEmpList = tmpSelectEmpList;
    }
}
