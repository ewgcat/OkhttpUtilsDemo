package com.xinwis.okhttputilsdemo.logicmanger;

/**
 * Created by zhengzhe on 15-9-8.
 */
public class BaseResponse {
    protected BaseRequest mRequest;
    protected String mResultString;

    protected int mMsgCode = -1;
    protected String mMsg;
    protected int mTranCode;
    protected int isEncryption;//是否加密0否，1是

    public BaseRequest getmRequest()
    {
        return mRequest;
    }

    public void setmRequest(BaseRequest mRequest) {
        this.mRequest = mRequest;
    }

    public String getmResultString()
    {
        return mResultString;
    }

    public void setmResultString(String mResultString) {
        this.mResultString = mResultString;

        parse();
    }

    /**
     * 由子类override来处理数据解析
     */
    public void parse() {

    }

    public int getmMsgCode()
    {
        return mMsgCode;
    }

    public String getmMsg()
    {
        return mMsg;
    }

    public int getmTranCode()
    {
        return mTranCode;
    }

    public int getIsEncryption() {
        return isEncryption;
    }
}
