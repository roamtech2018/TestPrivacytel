package com.roamtech.privacytel.utils;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.roamtech.privacytel.manager.RoamNumManager;

/**
 * 提示工具
 *
 * @author Administrator
 */
public class ToastUtils {
    private static Toast toast = null;

    @SuppressLint("ShowToast")
    public static void show(String str){
        if(null == toast){
            if(null == RoamNumManager.getInstance().getContext()){
                return;
            }
            toast = Toast.makeText(RoamNumManager.getInstance().getContext(), str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
    }
}
