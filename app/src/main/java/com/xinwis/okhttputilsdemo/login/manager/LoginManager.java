package com.xinwis.okhttputilsdemo.login.manager;


import com.xinwis.okhttputilsdemo.logicmanger.BaseLogicManager;
import com.xinwis.okhttputilsdemo.logicmanger.BaseResponse;
import com.xinwis.okhttputilsdemo.logicmanger.RequestDataCallback;
import com.xinwis.okhttputilsdemo.login.model.AccountInfo;
import com.xinwis.okhttputilsdemo.login.request.LoginRequest;
import com.xinwis.okhttputilsdemo.login.request.LoginResponse;


public class LoginManager extends BaseLogicManager  {
    protected String msg;



    public AccountInfo mAccountInfo = new AccountInfo();



    protected static LoginManager instance;

    public static LoginManager getInstance() {
        if (instance == null) {
            instance = new LoginManager();
        }
        return instance;
    }

     //登陆

    public void login(String userName, String password, final RequestDataCallback callback) {
        LoginRequest loginRequest = new LoginRequest(userName, password); //将账号和密码放到登陆请求里

        //发送请求
        sendRequest(loginRequest, new SendRequestCallBack() {


            @Override
            public void onRequestSuccess(BaseResponse response) {
                LoginResponse loginResponse = (LoginResponse) response;
                mAccountInfo = loginResponse.getmAccountInfo();   //获取账户信息



                if (callback != null) {
                    callback.onSuccess();
                }
            }

            @Override
            public void onRequestFailure(String errMsg) {
                if (callback != null) {
                    callback.onFailure(errMsg);
                }
            }
        });


    }

    public AccountInfo loadAccountInfo() {
        return mAccountInfo;
    }



}
