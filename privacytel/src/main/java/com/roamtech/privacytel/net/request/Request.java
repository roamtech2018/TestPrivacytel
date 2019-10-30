package com.roamtech.privacytel.net.request;


import com.roamtech.privacytel.bean.BindPhoneBean;
import com.roamtech.privacytel.bean.CallOutBean;
import com.roamtech.privacytel.bean.CallRecord;
import com.roamtech.privacytel.bean.NoData;
import com.roamtech.privacytel.bean.PageFetch;
import com.roamtech.privacytel.bean.RoamNumBean;
import com.roamtech.privacytel.bean.SMSRecord;
import com.roamtech.privacytel.bean.TokenBean;
import com.roamtech.privacytel.bean.UnbindNum;
import com.roamtech.privacytel.net.response.Response;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Request {
    String HOST = "https://test-sdk.roam-tech.com/";

    @POST("api/privacy_tel/roam_num/t_s_fetch_token")
    Observable<Response<TokenBean>> getToken(@Body TokenBean tokenBean);

    @POST("api/privacy_tel/roam_num/s_fetch")
    Observable<Response<List<RoamNumBean>>> getRoamNumList();

    @POST("api/privacy_tel/roam_num/s_bind_phone")
    Observable<Response<RoamNumBean>> bindRoamNum(@Body BindPhoneBean bindPhoneBean);

    @POST("api/privacy_tel/roam_num/s_unbind_phone")
    Observable<Response<RoamNumBean>> unbindRoamNum(@Body UnbindNum unbindNum);

    @POST("api/privacy_tel/roam_num/s_call_out")
    Observable<Response<NoData>> callOut(@Body CallOutBean callOutBean);

    @POST("api/privacy_tel/call_record/s_fetch")
    Observable<Response<List<CallRecord>>> getCallRecord(@Body PageFetch pageFetch);

    @POST("api/privacy_tel/sms_record/s_fetch")
    Observable<Response<List<SMSRecord>>> getSMSRecord(@Body PageFetch pageFetch);
}