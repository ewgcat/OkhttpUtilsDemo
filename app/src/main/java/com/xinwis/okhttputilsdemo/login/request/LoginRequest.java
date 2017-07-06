package com.xinwis.okhttputilsdemo.login.request;

import android.util.Base64;

import com.xinwis.okhttputilsdemo.logicmanger.BaseAppRequest;

import org.json.JSONException;

/**
 * Created by zhengzhe on 15-9-8.
 */
public class LoginRequest extends BaseAppRequest {
    public LoginRequest(String userName, String password) {
        super();
        try {
            mJsonParmas.put("username", userName);
            mJsonParmas.put("password", Base64.encodeToString(password.getBytes(), Base64.DEFAULT));
            mJsonParmas.put("platform", 1);
            mJsonParmas.put("clientId", "123");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getProtocolTranCode() {
        return 1017;
    }   //返回TranCode1017
}
