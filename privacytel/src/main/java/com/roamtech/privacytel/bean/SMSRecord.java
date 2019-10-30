package com.roamtech.privacytel.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SMSRecord implements Serializable {
    // 唯一ID
    @SerializedName("call_id")
    private String callId;
    // 主叫号码
    private String caller;
    // 被叫号码
    private String callee;
    // 短信发送时刻
    private Long time;
    // 短信内容
    @SerializedName("sms_content")
    private String smsContent;
    // 短信状态
    @SerializedName("sms_result")
    private String smsResult;
    // 用户ID
    @SerializedName("user_id")
    private Long userId;

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getCallee() {
        return callee;
    }

    public void setCallee(String callee) {
        this.callee = callee;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public String getSmsResult() {
        return smsResult;
    }

    public void setSmsResult(String smsResult) {
        this.smsResult = smsResult;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
