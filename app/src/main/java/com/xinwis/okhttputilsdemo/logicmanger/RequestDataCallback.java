package com.xinwis.okhttputilsdemo.logicmanger;


public interface RequestDataCallback {
    void onSuccess();

    void onFailure(String msg);
}
