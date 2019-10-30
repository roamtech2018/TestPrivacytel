package com.roamtech.privacytel.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 工作赋号实体类
 *
 * @author wxf
 */
public class RoamNumBean implements Serializable {
    @SerializedName("roam_num")
    private String roamNum;
    private String phone;
    private Integer model;
    @SerializedName("user_id")
    private Long userId;
    private Integer status;
    private Long expiration;
    private Long bindExpiration;

    public String getRoamNum() {
        return roamNum;
    }

    public void setRoamNum(String roamNum) {
        this.roamNum = roamNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public Long getBindExpiration() {
        return bindExpiration;
    }

    public void setBindExpiration(Long bindExpiration) {
        this.bindExpiration = bindExpiration;
    }
}
