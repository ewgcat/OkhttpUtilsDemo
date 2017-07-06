package com.xinwis.okhttputilsdemo.login.request;


import com.xinwis.okhttputilsdemo.logicmanger.BaseAppResponse;
import com.xinwis.okhttputilsdemo.login.model.AccountInfo;

import org.json.JSONObject;


public class LoginResponse extends BaseAppResponse {

    AccountInfo mAccountInfo;

    @Override
    public void parseJsonData(JSONObject JsonObject) {
        mAccountInfo = new AccountInfo(JsonObject);
    }




    public AccountInfo getmAccountInfo() {
        return mAccountInfo;
    }

}
