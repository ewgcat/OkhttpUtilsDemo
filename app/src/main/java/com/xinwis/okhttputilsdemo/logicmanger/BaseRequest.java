package com.xinwis.okhttputilsdemo.logicmanger;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class BaseRequest {

    protected JSONObject mJsonParmas = new JSONObject();
    protected List<File> mFileParam = new ArrayList<>();
    protected JSONObject mContentParam=new JSONObject();
    protected long  readTimeOut=0;
    protected long writeTimeOut=0;
    protected long connTimeOut=0;

    /**
     * 获取请求类名
     */
    public String getRequestName() {
        return getClass().getSimpleName();
    }


    /**
     * 获取请求响应类名，子类可以重写该方法
     */
    public String getResponseName() {
        String fullRequestName = getClass().getName();
        return fullRequestName.replace("Request", "Response");
    }

    public void setConnTimeOut(long connTimeOut) {
        this.connTimeOut =connTimeOut;
    }

    /**
     * 获取超时时间
     */
    public long getConnTimeOut(){
        return connTimeOut;
    }

    public void setReadTimeOut(long readTimeOut) {
        this.readTimeOut=readTimeOut;
    }

      public long getReadTimeOut(){
            return readTimeOut;
    }


    public long getWriteTimeOut() {
            return writeTimeOut;
    }

    public void setWriteTimeOut(long writeTimeOut) {
        this.writeTimeOut =writeTimeOut;
    }



    /**
     * 获取协议地址，子类可以重写该方法
     * @return
     */
    public String getUrl() {
        return "http://uat.hengtech.com.cn//pmsSrv/api/api!gateway.action" ;
    }


    /**
     * 获取请求参数
     */
    public JSONObject getJsonParam() {
        return mContentParam;
    }


    /**
     * 获取文件参数
     */
    public List<File> getFileParam() {
        return mFileParam;
    }


    public int getTranCode() {
        return 0;
    }


    public int getIsEncryption() {
        return 0;
    }

}
