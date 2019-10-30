package com.wxf.testprivacytel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.roamtech.privacytel.bean.CallOutBean;
import com.roamtech.privacytel.bean.CallRecord;
import com.roamtech.privacytel.bean.RoamNumBean;
import com.roamtech.privacytel.bean.SMSRecord;
import com.roamtech.privacytel.bean.TokenBean;
import com.roamtech.privacytel.intfc.ResultDataListener;
import com.roamtech.privacytel.intfc.ResultEmptyListener;
import com.roamtech.privacytel.manager.RoamNumManager;
import com.roamtech.privacytel.utils.ToastUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RoamNumAdapter adapter;
    // 被叫或接收短信的号码
    private EditText etPhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etPhoneNum = findViewById(R.id.et_phone_num);
        // 获取话单
        Button btnGetCall = findViewById(R.id.btn_get_call);
        btnGetCall.setOnClickListener(v -> {
            RoamNumManager.getInstance().getCallRecord(0,20,new ResultDataListener<List<CallRecord>>() {
                @Override
                public void onSuccess(List<CallRecord> data) {
                    if(null != data){
                        ToastUtils.show("获取话单成功");
                    }
                }

                @Override
                public void onFailed(int code, String msg) {
                    ToastUtils.show(msg);
                }
            });
        });

        // 获取短信
        Button btnGetSMS = findViewById(R.id.btn_get_sms);
        btnGetSMS.setOnClickListener(v -> {
            RoamNumManager.getInstance().getSMSRecord(0,20,new ResultDataListener<List<SMSRecord>>() {
                @Override
                public void onSuccess(List<SMSRecord> data) {
                    if(null != data){
                        ToastUtils.show("获取短信成功");
                    }
                }

                @Override
                public void onFailed(int code, String msg) {
                    ToastUtils.show(msg);
                }
            });
        });


        // 获取工作赋号
        Button btnGetNum = findViewById(R.id.btn_get_num);
        btnGetNum.setOnClickListener(v -> {
            RoamNumManager.getInstance().getRoamNumList(new ResultDataListener<List<RoamNumBean>>() {
                @Override
                public void onSuccess(List<RoamNumBean> data) {
                    adapter.setData(data);
                }

                @Override
                public void onFailed(int code, String msg) {
                    ToastUtils.show(msg);
                }
            });
        });

        recyclerView = findViewById(R.id.rv_list);
        adapter = new RoamNumAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // 拨打电话按钮
        Button btnCall = findViewById(R.id.btn_call);
        btnCall.setOnClickListener(v -> {
            call(new ResultEmptyListener() {
                @Override
                public void onSuccess() {
                    // TODO 跳转到系统拨号界面，如果读出本机号码是绑定号码，可以直接调用系统的拨打方法，不需要用户再次到系统拨号界面确认
                    redirectToCallSystem(MainActivity.this,getRoamNum());
                }

                @Override
                public void onFailed(int code, String msg) {
                    ToastUtils.show(msg);
                }
            });
         });

        // 发送短信按钮
        Button btnSMS = findViewById(R.id.btn_sms);
        btnSMS.setOnClickListener(v -> {
            call(new ResultEmptyListener() {
                @Override
                public void onSuccess() {
                    redirectToMessageSystem(MainActivity.this,getRoamNum());
                }

                @Override
                public void onFailed(int code, String msg) {
                    ToastUtils.show(msg);
                }
            });
        });

        TokenBean tokenBean = new TokenBean();
        tokenBean.setCorpKey("4982842105035499");
        tokenBean.setTs(System.currentTimeMillis()/1000);
        tokenBean.setUserId(667788L);
        tokenBean.setSign(SignUtils.getSignature(tokenBean.toMap(),"500c9023-deb5-4166-9b75-e773006f17b7"));

        System.out.println(tokenBean.toString());
        Log.e("aaa",tokenBean.toString());


        RoamNumManager.getInstance().getToken(new ResultDataListener<TokenBean>() {
            @Override
            public void onSuccess(TokenBean data) {
                ToastUtils.show("获取成功:"+data.getToken());
                RoamNumManager.getInstance().setToken(data.getToken());
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.show("获取失败！！！！");
            }
        },tokenBean);
    }


    /**
     * 绑定外呼关系(拨打电话或者发送短信前，需要调用该方法绑定关系，绑定成功后，直接拨打工作赋号即可呼到对方手机)
     *
     * @param listener
     *          监听
     */
    private void call(ResultEmptyListener listener){
        if(null != adapter.getData() && adapter.getData().size() > 0){
            CallOutBean callOutBean = new CallOutBean();
            callOutBean.setRoamNum(getRoamNum());
            callOutBean.setCalled(etPhoneNum.getText().toString());
            RoamNumManager.getInstance().call(callOutBean,listener);
        } else {
            ToastUtils.show("没有获取到工作赋号信息");
        }
    }

    private String getRoamNum(){
        if(null != adapter.getData() && adapter.getData().size() > 0){
            // TODO 目前演示，默认用第一个工作赋号
            return adapter.getData().get(0).getRoamNum();
        }
        return null;
    }


    /**
     * 跳转到系统短信页面
     *
     * @param context
     *          上下文
     * @param roamNum
     *          工作赋号
     */
    private void redirectToMessageSystem(Context context, String roamNum){
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(Uri.parse("smsto:" + roamNum));
        sendIntent.putExtra("sms_body", "");
        context.startActivity(sendIntent);
    }

    /**
     * 跳转到系统拨号页面
     *
     * @param context
     *          上下文
     * @param roamNum
     *          工作赋号
     */
    private void redirectToCallSystem(Context context, String roamNum){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + roamNum));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 拨打电话（直接拨打电话）
     * @param context
     *          上下文
     * @param roamNum
     *          工作赋号
     */
    @SuppressLint("MissingPermission")
    public void callSystem(Context context, String roamNum){
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + roamNum);
        intent.setData(data);
        context.startActivity(intent);
    }
}
