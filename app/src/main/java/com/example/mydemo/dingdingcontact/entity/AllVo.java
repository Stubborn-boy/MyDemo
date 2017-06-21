package com.example.mydemo.dingdingcontact.entity;


import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.mydemo.dingdingcontact.OrgContactAdapter;

import java.io.Serializable;

/**
 * Created by jack on 2017/5/21.
 */

public class AllVo implements MultiItemEntity, Serializable {
    private String orgId;

    @Override
    public int getItemType() {
        return OrgContactAdapter.ALL;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AllVo) {
            AllVo vo = (AllVo) obj;
            return (orgId.equals(vo.orgId));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return orgId.hashCode();
    }
}

