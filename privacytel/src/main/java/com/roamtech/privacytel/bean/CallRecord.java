package com.roamtech.privacytel.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CallRecord implements Serializable {
    // 唯一id
    @SerializedName("call_id")
    private String callId;
    // 主叫
    private String caller;
    // 被叫
    private String callee;
    // 主叫拨通虚拟号码时刻
    private Long time;
    // 被叫接通时刻
    @SerializedName("connect_time")
    private Long connectTime;
    // 通话结束时刻
    @SerializedName("end_time")
    private Long endTime;
    // 主被叫之间的通话时长，单位为秒
    private Integer duration;
    // 通话状态
    @SerializedName("call_result")
    private String callResult;
    // 语音地址
    private String file;
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

    public Long getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(Long connectTime) {
        this.connectTime = connectTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCallResult() {
        return callResult;
    }

    public void setCallResult(String callResult) {
        this.callResult = callResult;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
