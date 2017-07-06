package com.xinwis.okhttputilsdemo.logicmanger;

import android.text.TextUtils;
import android.util.Log;


import com.xinwis.okhttputils.OkHttpUtils;
import com.xinwis.okhttputils.builder.PostFormBuilder;
import com.xinwis.okhttputils.builder.PostStringBuilder;
import com.xinwis.okhttputils.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;


/**
 * Created by zhengzhe on 15-9-8.
 */
public class BaseLogicManager {

    private static final String TAG = BaseLogicManager.class.getSimpleName();


    public interface SendRequestCallBack {
        void onRequestSuccess(BaseResponse response);

        void onRequestFailure(String errMsg);
    }


    /**
     * post请求
     *
     * @param request
     * @param callBack
     */
    public void sendRequest(final BaseRequest request, final SendRequestCallBack callBack) {
        //请求路径
        String url = request.getUrl();
        //请求报文
        String requestString = request.getJsonParam().toString();
        //打印请求报文和请求路径
        Log.i(TAG, request.getRequestName() + "  post请求 报文 : " + requestString + "  url=" + url);
        try {

            PostStringBuilder postStringBuilder = OkHttpUtils.postString();
            long connTimeOut = request.getConnTimeOut();
            if (connTimeOut != 0) {
                postStringBuilder.setConnTimeOut(connTimeOut);
            }
            long readTimeOut = request.getReadTimeOut();
            if (readTimeOut != 0) {
                postStringBuilder.setReadTimeOut(readTimeOut);
            }
            long writeTimeOut = request.getWriteTimeOut();
            if (writeTimeOut != 0) {
                postStringBuilder.setWriteTimeOut(writeTimeOut);
            }
            postStringBuilder
                    .url(url)
                    .content(requestString)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception error, int id) {
                            //打印请求错误日志
                            Log.i(TAG, request.getRequestName() + " post请求  failure, error msg : " + error.toString());
                            if (callBack != null) {
                                callBack.onRequestFailure(error.toString());
                            }
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            //打印请求结果
                            Log.i(TAG, request.getRequestName() + " post请求  success, result data : " + response);
                            //初步封装请求的结果
                            BaseResponse baseResponse = buildResponse(request);
                            if (baseResponse == null) {
                                if (callBack != null) {
                                    callBack.onRequestFailure("装配返回值异常");
                                    Log.e(TAG, "找不到" + request.getResponseName() + "处理网络请求");
                                }
                                return;
                            }
                            baseResponse.setmRequest(request);
                            baseResponse.setmResultString(response);
                            if (callBack != null) {
                                if (baseResponse.getmMsgCode() == 0) {
                                    callBack.onRequestSuccess(baseResponse);
                                } else {

                                    Log.i(TAG, request.getRequestName() + "  post请求  msgCode : " + baseResponse.getmMsgCode());
                                    String msg = baseResponse.getmMsg();
                                    if (!TextUtils.isEmpty(msg)) {
                                        String msg2 = msg.toUpperCase();
                                        if (msg2.contains("EEROR") || msg2.contains("EXCEPTION") || msg2.contains("FAIL") || msg2.contains("SQL")) {
                                            callBack.onRequestFailure("消息返回错误，请上报信息专员处理");
                                        } else {
                                            callBack.onRequestFailure(msg);
                                        }
                                    }

                                }
                            }
                        }
                    });
        } catch (Exception e) {
            Log.i(TAG, request.getRequestName() + " post请求  Exception  : " + e.toString());
        }
    }


    /**
     * post上传图片
     *
     * @param request
     * @param callBack
     */
    public void uploadImagesByPost(final BaseRequest request, final SendRequestCallBack callBack) {
        //请求路径
        String url = request.getUrl();
        //请求报文
        String requestString = request.getJsonParam().toString();
        //打印请求报文和请求路径
        String message = request.getRequestName() + "  post请求 报文 : " + requestString + "  url=" + url;
        Log.i(TAG, message);
        //添加上传图片文件
        PostFormBuilder postFormBuilder = OkHttpUtils.post();
        if (request.getFileParam().size() == 0) {
            if (callBack != null) {
                callBack.onRequestFailure("没有找到上传的文件，上传请求失败");
                return;
            }
        } else {
            for (int i = 0; i < request.getFileParam().size(); i++) {
                File file = request.getFileParam().get(i);
                String fileName = file.getName();
                postFormBuilder.addFile("image", fileName, file);
            }
        }
        //上传报文参数
        Map<String, String> params = new HashMap<>();
        params.put("reqStr", requestString);
        postFormBuilder.url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        //打印请求错误日志
                        Log.i(TAG, request.getRequestName() + " post请求  failure, error msg : " + e);
                        if (callBack != null) {
                            callBack.onRequestFailure(e.toString());
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //打印请求结果
                        Log.i(TAG, request.getRequestName() + "  post请求  success, result data : " + response);
                        //上传成功后删除
//                        FileUtil.deleteFiles(request.getFileParam());
                        //初步封装请求的结果
                        BaseResponse baseResponse = buildResponse(request);
                        baseResponse.setmRequest(request);
                        baseResponse.setmResultString(response);
                        if (callBack != null) {
                            if (baseResponse.getmMsgCode() == 0) {
                                callBack.onRequestSuccess(baseResponse);
                            } else {
                                Log.i(TAG, request.getRequestName() + " post请求   msgCode : " + baseResponse.getmMsgCode());
                                String msg = baseResponse.getmMsg();
                                if (!TextUtils.isEmpty(msg)) {
                                    String msg2 = msg.toUpperCase();
                                    if (msg2.contains("EEROR") || msg2.contains("EXCEPTION") || msg2.contains("FAIL") || msg2.contains("SQL")) {
                                        callBack.onRequestFailure("消息返回错误，请上报信息专员处理");
                                    } else {
                                        callBack.onRequestFailure(msg);
                                    }
                                }

                            }
                        }
                    }
                });
    }


    /**
     * get发送请求
     *
     * @param request
     * @param callBack
     */
    public void sendRequestByGet(final BaseRequest request, final SendRequestCallBack callBack) {
        //请求路径
        String url = request.getUrl();
        //请求报文
        String requestString = request.getJsonParam().toString();
        //打印请求报文和请求路径
        final String message = request.getRequestName() + " send : " + requestString + "  url=" + url;
        Log.i(TAG, message);
        try {
            OkHttpUtils
                    .get()
                    .url(url)
                    .addParams("reqStr", requestString)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception error, int id) {
                            //打印请求错误日志
                            Log.i(TAG, request.getRequestName() + " failure, error msg : " + error.toString());
                            if (callBack != null) {
                                callBack.onRequestFailure(error.toString());
                            }
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            //打印请求结果
                            Log.i(TAG, request.getRequestName() + " success, result data : " + response);
                            //初步封装请求的结果
                            BaseResponse baseResponse = buildResponse(request);
                            if (baseResponse == null) {
                                if (callBack != null) {
                                    callBack.onRequestFailure("装配返回值异常");
                                    Log.e(TAG, "找不到" + request.getResponseName() + "处理网络请求");
                                }
                                return;
                            }
                            baseResponse.setmRequest(request);
                            baseResponse.setmResultString(response);
                            if (callBack != null) {
                                if (baseResponse.getmMsgCode() == 0) {
                                    callBack.onRequestSuccess(baseResponse);
                                } else {
                                    Log.i(TAG, request.getRequestName() + "  msgCode : " + baseResponse.getmMsgCode());
                                    String msg = baseResponse.getmMsg();

                                    if (!TextUtils.isEmpty(msg)) {
                                        String msg2 = msg.toUpperCase();
                                        if (msg2.contains("EEROR") || msg2.contains("EXCEPTION") || msg2.contains("FAIL") || msg2.contains("SQL")) {
                                            callBack.onRequestFailure("消息返回错误，请上报信息专员处理");
                                        } else {
                                            callBack.onRequestFailure(msg);
                                        }
                                    }

                                }
                            }
                        }
                    });
        } catch (Exception e) {
            Log.i(TAG, request.getRequestName() + " Exception  : " + e.toString());
        }
    }


    /**
     * 根据request 的 getResponseName()，再通过反射生成bean
     *
     * @param request
     * @return
     */
    public static BaseResponse buildResponse(BaseRequest request) {
        if (request != null) {
            String responseName = request.getResponseName();
            if (responseName != null) {
                try {
                    Class<BaseResponse> responseClass = (Class<BaseResponse>) Class.forName(responseName);
                    BaseResponse response = responseClass.newInstance();
                    return response;
                } catch (ClassNotFoundException e) {
                    Log.i(TAG, "buildResponse ClassNotFoundException e:" + e);
                } catch (InstantiationException e) {
                    Log.i(TAG, "buildResponse InstantiationException e:" + e);
                } catch (IllegalAccessException e) {
                    Log.i(TAG, "buildResponse IllegalAccessException e:" + e);
                }
            }
        }
        return null;
    }


}
