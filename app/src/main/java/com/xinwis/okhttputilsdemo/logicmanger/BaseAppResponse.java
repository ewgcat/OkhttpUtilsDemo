package com.xinwis.okhttputilsdemo.logicmanger;

import com.xinwis.okhttputilsdemo.utils.DesNewUtil;
import com.xinwis.okhttputilsdemo.utils.JsonUtil;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by ottozheng on 15/9/11.
 */
public abstract class BaseAppResponse extends BaseResponse {
    private static final String TAG = "BaseJsonResponse";
    protected JSONObject mResponseJson;
    protected JSONObject mJsonData;

    @Override
    public void parse() {
        JSONObject jsonObject = JsonUtil.getJsonObjectFromString(mResultString);
        if (jsonObject == null) {
            return;
        }

        mResponseJson = jsonObject;

        mMsgCode = JsonUtil.getInt(jsonObject, "msgCode", 0);
        mMsg = JsonUtil.getString(jsonObject, "msg");
        mTranCode = JsonUtil.getInt(jsonObject, "tranCode");
        isEncryption=JsonUtil.getInt(jsonObject, "isEncryption");
        if (isEncryption==1) {
            String temp=JsonUtil.getString(jsonObject,"bizContent");
            try {
                mJsonData=JsonUtil.getJsonObjectFromString(DesNewUtil.decrypt(temp));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else {
            mJsonData = JsonUtil.getJsonObject(jsonObject, "bizContent");
        }

        if (mJsonData == null) {
            return;
        }
        parseJsonData(mJsonData);
    }

    public abstract void parseJsonData(JSONObject jsonData);
}
