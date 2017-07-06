package com.xinwis.okhttputilsdemo.logicmanger;

import com.xinwis.okhttputilsdemo.utils.DesNewUtil;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

/**
 * 存放请求的信息
 * Created by ottozheng on 15/9/11.
 */
public abstract class BaseAppRequest extends BaseRequest {

    public BaseAppRequest() {
        try {
            mContentParam.put("tranCode", getTranCode());
            mContentParam.put("isEncryption",getIsEncryption());
            if (getIsEncryption()==0) {
                mContentParam.put("bizContent",mJsonParmas);
            }else {
                mContentParam.put("bizContent", DesNewUtil.encrypt(mJsonParmas.toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getTranCode() {
        return getProtocolTranCode();
    }

    public abstract int getProtocolTranCode();
}
