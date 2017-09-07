package com.example.mydemo.dingdingcontact.mvp;

import com.example.baselibrary.mvp.BaseContract;
import com.example.mydemo.dingdingcontact.entity.ResultVo;

/**
 * Created by 71541 on 2017/9/7.
 */

public interface IOrganizationView extends BaseContract.BaseView{
    void showData(ResultVo resultVo);
}
