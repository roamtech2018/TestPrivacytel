package com.roamtech.privacytel.bean;

import java.io.Serializable;

/**
 * bean基类
 */
public abstract class BaseBean implements Serializable {
    /**
     * 验证参数
     *
     * @return boolean
     */
    public abstract boolean validateParam();
}
