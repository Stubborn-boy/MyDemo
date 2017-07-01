package com.example.mydemo.dingdingcontact;

import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.mydemo.R;
import com.example.mydemo.dingdingcontact.entity.AllVo;
import com.example.mydemo.dingdingcontact.entity.BaseUserVo;
import com.example.mydemo.dingdingcontact.entity.EmpUserVo;
import com.example.mydemo.dingdingcontact.entity.OrgVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 2017/5/21.
 */

public class FriendContactAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int ALL = 1;
    public static final int EMP = 3;

    private List<EmpUserVo> selectEmpList;
    private List<BaseUserVo> previousEmpList;

    public FriendContactAdapter(List data, List<BaseUserVo> previousEmpList, List<EmpUserVo> selectEmpList){
        super(data);
        this.previousEmpList = previousEmpList;
        this.selectEmpList = selectEmpList;
        addItemType(EMP, R.layout.list_item_emp);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()){
            case EMP:
                EmpUserVo empUserVo = (EmpUserVo) item;

                CheckBox checkBox_emp = helper.getView(R.id.checkbox);
                if(previousEmpList.contains(empUserVo)){
                    checkBox_emp.setChecked(true);
                    helper.itemView.setEnabled(false);
                } else{
                    helper.itemView.setEnabled(true);

                    if(selectEmpList.contains(empUserVo)){
                        checkBox_emp.setChecked(true);
                    }else{
                        checkBox_emp.setChecked(false);
                    }
                }

                helper.setText(R.id.tv_name, ((EmpUserVo) item).getUserName());
                break;
        }
    }
}

