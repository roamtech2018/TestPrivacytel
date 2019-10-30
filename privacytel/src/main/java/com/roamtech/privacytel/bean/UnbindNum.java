package com.roamtech.privacytel.bean;

import com.google.gson.annotations.SerializedName;

public class UnbindNum {
    @SerializedName("roam_num")
    private String roamNum;

    public UnbindNum(String roamNum){
        this.roamNum = roamNum;
    }

    public String getRoamNum() {
        return roamNum;
    }

    public void setRoamNum(String roamNum) {
        this.roamNum = roamNum;
    }
}
