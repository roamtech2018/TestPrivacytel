package com.roamtech.privacytel.bean;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class TokenBean {
    @SerializedName("corp_key")
    private String corpKey;
    private String sign;
    private Long ts;
    @SerializedName("user_id")
    private Long userId;
    private String token;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("corp_key", corpKey);
        map.put("ts", ts);
        map.put("sign", sign);
        map.put("user_id", userId);
        return map;
    }

    public String getCorpKey() {
        return corpKey;
    }

    public void setCorpKey(String corpKey) {
        this.corpKey = corpKey;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public String toString() {
        return "corpKey = " + corpKey + ",sign = " +sign+",ts=" + ts +",userId = " + userId + ",token = " + token;
    }
}
