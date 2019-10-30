package com.wxf.testprivacytel;

import android.app.Application;

import com.roamtech.privacytel.manager.RoamNumManager;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        getApplicationContext();

        RoamNumManager.getInstance().init(BaseApplication.this);
    }
}
