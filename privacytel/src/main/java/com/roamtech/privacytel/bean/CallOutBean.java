package com.roamtech.privacytel.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * 外呼关系绑定
 */
public class CallOutBean extends BaseBean {
    // 工作赋号
    @SerializedName("roam_num")
    private String roamNum;
    // 被叫号码
    private String called;
    // 关系持续时间
    private Long expiration;

    public String getRoamNum() {
        return roamNum;
    }

    public void setRoamNum(String roamNum) {
        this.roamNum = roamNum;
    }

    public String getCalled() {
        return called;
    }

    public void setCalled(String called) {
        this.called = called;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    @Override
    public boolean validateParam() {
        return !TextUtils.isEmpty(roamNum) && !TextUtils.isEmpty(called);
    }
}
