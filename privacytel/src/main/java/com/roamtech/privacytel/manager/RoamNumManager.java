package com.roamtech.privacytel.manager;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.roamtech.privacytel.bean.BaseBean;
import com.roamtech.privacytel.bean.BindPhoneBean;
import com.roamtech.privacytel.bean.CallOutBean;
import com.roamtech.privacytel.bean.CallRecord;
import com.roamtech.privacytel.bean.NoData;
import com.roamtech.privacytel.bean.PageFetch;
import com.roamtech.privacytel.bean.RoamNumBean;
import com.roamtech.privacytel.bean.SMSRecord;
import com.roamtech.privacytel.bean.TokenBean;
import com.roamtech.privacytel.bean.UnbindNum;
import com.roamtech.privacytel.intfc.ResultDataListener;
import com.roamtech.privacytel.intfc.ResultEmptyListener;
import com.roamtech.privacytel.net.RetrofitApi;
import com.roamtech.privacytel.net.exception.ApiException;
import com.roamtech.privacytel.net.provider.SchedulerProvider;
import com.roamtech.privacytel.net.response.ResponseTransformer;
import com.roamtech.privacytel.utils.ToastUtils;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 隐私号管理类
 *
 * @author wxf
 */
public class RoamNumManager {
    // 静态实例
    private static RoamNumManager instance = new RoamNumManager();
    // 上下文
    private Application context;
    // 网络请求
    private CompositeDisposable mDisposable = new CompositeDisposable();
    // token
    private String token;

    /**
     * 设置为单例
     */
    private RoamNumManager(){}

    /**
     * 获取静态实例
     *
     * @return RoamNumManager
     */
    public static RoamNumManager getInstance(){
//        if(null == instance){
//            synchronized (RoamNumManager.class){
//                if(null == instance){
//                    instance = new RoamNumManager();
//                }
//            }
//        }
        return instance;
    }

    /**
     * 获取上下文
     *
     * @return Context
     */
    public Context getContext(){
        return context;
    }

    /**
     * 初始化
     *
     * @param context
     *          上下文
     */
    public void init(Application context){
        this.context = context;
    }

    /**
     * 设置token
     *
     * @param token
     *          token
     */
    public void setToken(String token){
        this.token = token;
    }

    /**
     * 获取token
     *
     * @return String
     */
    public String getToken(){
        return token;
    }

    public void getToken(ResultDataListener<TokenBean> listener, TokenBean tokenBean){
        Disposable disposable = RetrofitApi.getRequestInterface().getToken(tokenBean)
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(bean -> {
                    if(null != bean){
                        listener.onSuccess(bean);
                    } else {
                        listener.onFailed(101,"获取token失败");
                    }
                }, throwable -> {
                    // 处理异常
                    if(throwable instanceof ApiException){
                        throwable.printStackTrace();
                        listener.onFailed(((ApiException) throwable).getCode(),((ApiException) throwable).getDisplayMessage());
                        return;
                    }
                    listener.onFailed(102,throwable.getCause().getMessage());
                });
        mDisposable.add(disposable);
    }

    /**
     * 获取工作赋号列表
     *
     * @param listener
     *          结果监听
     */
    public void getRoamNumList(ResultDataListener<List<RoamNumBean>> listener){
        if(null == listener){
            showParamError();
            return;
        }
        Disposable disposable = RetrofitApi.getRequestInterface().getRoamNumList()
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(list -> {
                    if(null != list && list.size() > 0){
                        listener.onSuccess(list);
                    } else {
                        listener.onFailed(101,"暂未分配工作赋号");
                    }
                }, throwable -> {
                    // 处理异常
                    if(throwable instanceof ApiException){
                        throwable.printStackTrace();
                        listener.onFailed(((ApiException) throwable).getCode(),((ApiException) throwable).getDisplayMessage());
                        return;
                    }
                    listener.onFailed(102,throwable.getCause().getMessage());
                });
        mDisposable.add(disposable);
    }

    /**
     * 绑定工作赋号
     *
     * @param bindPhoneBean
     *          绑定类
     * @param listener
     *          结果监听
     */
    public void bindNum(BindPhoneBean bindPhoneBean, ResultDataListener<RoamNumBean> listener){
        if(!validate(bindPhoneBean,listener)){
            return ;
        }
        Disposable disposable = RetrofitApi.getRequestInterface().bindRoamNum(bindPhoneBean)
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(roamNumBean -> {
                    if(null != roamNumBean){
                        listener.onSuccess(roamNumBean);
                    } else {
                        listener.onFailed(101,"绑定失败");
                    }
                }, throwable -> {
                    // 处理异常
                    if(throwable instanceof ApiException){
                        throwable.printStackTrace();
                        listener.onFailed(((ApiException) throwable).getCode(),((ApiException) throwable).getDisplayMessage());
                        return;
                    }
                    listener.onFailed(102,throwable.getCause().getMessage());
                });
        mDisposable.add(disposable);
    }

    /**
     * 绑定工作赋号
     *
     * @param roamNum
     *          工作赋号
     * @param listener
     *          结果监听
     */
    public void unbindNum(String roamNum, ResultDataListener<RoamNumBean> listener){
        if(TextUtils.isEmpty(roamNum) || null == listener){
            showParamError();
            return;
        }
        Disposable disposable = RetrofitApi.getRequestInterface().unbindRoamNum(new UnbindNum(roamNum))
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(roamNumBean -> {
                    if(null != roamNumBean){
                        listener.onSuccess(roamNumBean);
                    } else {
                        listener.onFailed(101,"解绑失败");
                    }
                }, throwable -> {
                    // 处理异常
                    if(throwable instanceof ApiException){
                        throwable.printStackTrace();
                        listener.onFailed(((ApiException) throwable).getCode(),((ApiException) throwable).getDisplayMessage());
                        return;
                    }
                    listener.onFailed(102,throwable.getCause().getMessage());
                });
        mDisposable.add(disposable);
    }

    /**
     * 绑定外呼关系
     *
     * @param callOutBean
     *          外呼关系类
     * @param listener
     *          监听
     */
    public void call(CallOutBean callOutBean,ResultEmptyListener listener){
        if(validate(callOutBean,listener)){
            showParamError();
            return;
        }

        Disposable disposable = RetrofitApi.getRequestInterface().callOut(callOutBean)
                .compose(ResponseTransformer.handleResult(NoData.class))
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe( roamNumBean -> {
                    listener.onSuccess();
                }, throwable -> {
                    // 处理异常
                    if(throwable instanceof ApiException){
                        throwable.printStackTrace();
                        listener.onFailed(((ApiException) throwable).getCode(),((ApiException) throwable).getDisplayMessage());
                        return;
                    }

                    listener.onFailed(102,throwable.getCause().getMessage());
                });
        mDisposable.add(disposable);
    }

    /**
     * 获取通话记录列表
     *
     * @param lastId
     *          从哪个记录开始查询(没有记录传0)
     * @param limit
     *          该次获取的数目
     * @param listener
     *          监听
     */
    public void getCallRecord(long lastId, int limit, ResultDataListener<List<CallRecord>> listener){
        if(null == listener){
            showParamError();
            return;
        }

        PageFetch pageFetch = new PageFetch();
        if(lastId > 0){
            pageFetch.setId(lastId);
            pageFetch.setGreater(true);
        } else {
            pageFetch.setGreater(false);
        }
        pageFetch.setSize(limit);

        Disposable disposable = RetrofitApi.getRequestInterface().getCallRecord(pageFetch)
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(listener::onSuccess, throwable -> {
                    // 处理异常
                    if(throwable instanceof ApiException){
                        throwable.printStackTrace();
                        listener.onFailed(((ApiException) throwable).getCode(),((ApiException) throwable).getDisplayMessage());
                        return;
                    }
                    listener.onFailed(102,throwable.getCause().getMessage());
                });
        mDisposable.add(disposable);
    }

    /**
     * 获取短信记录列表
     *
     * @param lastId
     *          从哪个记录开始查询(没有记录传0)
     * @param limit
     *          该次获取的数目
     * @param listener
     *          监听
     */
    public void getSMSRecord(long lastId, int limit, ResultDataListener<List<SMSRecord>> listener){
        if(null == listener){
            showParamError();
            return;
        }

        PageFetch pageFetch = new PageFetch();
        if(lastId > 0){
            pageFetch.setId(lastId);
            pageFetch.setGreater(true);
        } else {
            pageFetch.setGreater(false);
        }
        pageFetch.setSize(limit);
        Disposable disposable = RetrofitApi.getRequestInterface().getSMSRecord(pageFetch)
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(listener::onSuccess, throwable -> {
                    // 处理异常
                    if(throwable instanceof ApiException){
                        throwable.printStackTrace();
                        listener.onFailed(((ApiException) throwable).getCode(),((ApiException) throwable).getDisplayMessage());
                        return;
                    }
                    listener.onFailed(102,throwable.getCause().getMessage());
                });

        mDisposable.add(disposable);
    }

    /**
     * 验证参数
     *
     * @param bean
     *          bean类
     * @param listener
     *          监听
     * @return boolean
     */
    private boolean validate(BaseBean bean,Object listener){
        if(null == bean || null == listener || !bean.validateParam()){
            showParamError();
            return false;
        }
        return true;
    }

    /**
     * 弹出toast
     */
    private void showParamError(){
        ToastUtils.show("参数错误");
    }

    /**
     * 取消请求
     */
    public void cancelRequest(){
        mDisposable.clear();
    }
}