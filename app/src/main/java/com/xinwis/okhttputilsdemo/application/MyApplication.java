package com.xinwis.okhttputilsdemo.application;

import android.app.Application;
import android.content.Context;

import com.xinwis.okhttputils.OkHttpUtils;
import com.xinwis.okhttputils.cookie.store.NovateCookieManger;
import com.xinwis.okhttputils.https.AllowMyHostnameVerifier;
import com.xinwis.okhttputils.httpsfactroy.HttpsFactroy;
import com.xinwis.okhttputils.interceptor.BaseInterceptor;
import com.xinwis.okhttputils.interceptor.CaheInterceptor;
import com.xinwis.okhttputils.utils.Logger;
import com.xinwis.okhttputilsdemo.R;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by lishuaihua on 2017/7/6.
 */

public class MyApplication extends Application {
    private Cache cache = null;
    private File httpCacheDirectory;

    private static int[] certificates = {R.raw.fullchain};

    @Override
    public void onCreate() {
        super.onCreate();
        Context context=getApplicationContext();
        try {
            if (httpCacheDirectory == null && context != null) {
                String cacheDir = context.getExternalCacheDir().toString() + "/poly_cache";
                //判断目录是否存在，不存在则创建该目录
                File dirFile = new File(cacheDir);
                if (!dirFile.exists()) {
                    boolean mkdirs = dirFile.mkdirs();
                }
                //创建缓存文件
                httpCacheDirectory = new File(cacheDir, "poly_cache");
            }
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
            }
        } catch (Exception e) {
            Logger.i("CustomApplication",  e.getMessage());
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cookieJar(new NovateCookieManger(context))
                .cache(cache)
                .addInterceptor(new BaseInterceptor())
                .addInterceptor(new CaheInterceptor(context))
                .addNetworkInterceptor(new CaheInterceptor(context))
                .hostnameVerifier(new AllowMyHostnameVerifier())
                .sslSocketFactory(HttpsFactroy.getSSLSocketFactory(context, certificates), Platform.get().trustManager(HttpsFactroy.getSSLSocketFactory(context, certificates)))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .connectionPool(new ConnectionPool(8, 10, TimeUnit.SECONDS))
                .connectTimeout(30000L, TimeUnit.MILLISECONDS)
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }
}
