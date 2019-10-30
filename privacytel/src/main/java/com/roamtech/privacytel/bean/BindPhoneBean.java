package com.roamtech.privacytel.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 绑定真实手机号码
 */
public class BindPhoneBean extends BaseBean {
    // 工作赋号
    @SerializedName("roam_num")
    private String roamNum;
    // 真实手机号
    @SerializedName("phone")
    private String phoneNum;
    // 作为被叫时，来电显示类型:0-对方真实号码;2-对方虚拟号码
    private Integer model;
    // 绑定持续时间，单位:秒，不能超过号码有效期
    private Long expiration;

    public String getRoamNum() {
        return roamNum;
    }

    public void setRoamNum(String roamNum) {
        this.roamNum = roamNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    @Override
    public boolean validateParam() {
        return null != roamNum && null != phoneNum;
    }
}
