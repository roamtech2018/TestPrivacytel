package com.roamtech.privacytel.intfc;

/**
 * 结果监听(无返回值)
 */
public interface ResultEmptyListener {
    /**
     * 成功方法
     */
    void onSuccess();

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
