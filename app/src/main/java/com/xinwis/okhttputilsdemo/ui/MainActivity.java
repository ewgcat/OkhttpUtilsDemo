package com.xinwis.okhttputilsdemo.ui;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xinwis.okhttputils.utils.utils.NetworkUtil;
import com.xinwis.okhttputilsdemo.R;
import com.xinwis.okhttputilsdemo.logicmanger.RequestDataCallback;
import com.xinwis.okhttputilsdemo.login.manager.LoginManager;
import com.xinwis.okhttputilsdemo.login.model.AccountInfo;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private ProgressDialog dialog;
    private boolean islogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new ProgressDialog(this);
        dialog.setTitle("请稍后...");
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                islogin = false;
            }
        });
        tv = (TextView) findViewById(R.id.tv);

    }

    public void sendRequest(View view){
        if (!islogin){
            postLogin("zhusha5","hengte@2016");
        }

    }

    private void postLogin(final String username, final String password) {


        if (NetworkUtil.hasNetwork(getApplicationContext())) {  //如果手机联网，执行下面的动作
            tv.setText("");
            islogin = true;
            dialog.show();
            //向服务器发送登陆请求，响应的数据回调到onSuccess()方法中
            LoginManager.getInstance().login(username, password, new RequestDataCallback() {
                @Override
                public void onSuccess() {
                    AccountInfo accountInfo = LoginManager.getInstance().loadAccountInfo();
                    if (accountInfo!=null){
                        tv.setText(accountInfo.toString());
                    }
                    dialog.dismiss();

                }
                @Override
                public void onFailure(String msg) {
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                }
            });
        } else {        //在没有网络的情况下执行

            Toast.makeText(MainActivity.this,"请联网重新发送请求",Toast.LENGTH_SHORT).show();
        }
    }

}
