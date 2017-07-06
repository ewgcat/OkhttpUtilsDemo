package com.xinwis.okhttputilsdemo.ui;

import android.accounts.AccountManager;


public  class LogicService {

    /**
     * 登陆相关
     * @return
     */
    public static AccountManager accountManager() {
        return LogicManager.getInstance().findBylogicManagerClass(AccountManager.class);
    }

}
