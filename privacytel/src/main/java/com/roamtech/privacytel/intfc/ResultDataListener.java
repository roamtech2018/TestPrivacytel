package com.roamtech.privacytel.intfc;

/**
 * 结果监听(有返回值)
 */
public interface ResultDataListener<T> {
    /**
     * 成功方法
     *
     * @param data
     *          返回数据
     */
    void onSuccess(T data);

    /**
     * 失败方法
     *
     * @param code
     *          编码
     * @param msg
     *          消息
     */
    void onFailed(int code, String msg);
}
