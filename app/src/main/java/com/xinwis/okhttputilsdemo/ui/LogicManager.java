package com.xinwis.okhttputilsdemo.ui;

import java.util.HashMap;
import java.util.Map;




public class LogicManager {

    private static final String TAG = "LogicManager";

    Map<String, Object> logicManagerMap = new HashMap<String, Object>();

    protected LogicManager() {
    }

    private static LogicManager instance = null;
    public synchronized static LogicManager getInstance() {
        if(instance == null) {
            instance = new LogicManager();
        }
        return instance;
    }


    /**
     * 根据实体类来获得logicManager
     * @param logicManagerClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends Object> T findBylogicManagerClass(Class<T> logicManagerClass) {
        T logicManager = null;
        String className = getlogicManagerClassName(logicManagerClass);
        if(logicManagerMap.containsKey(className)) {
            logicManager = (T) logicManagerMap.get(className);
        }if(logicManager == null) {
            try {
                logicManager = (T) logicManagerClass.newInstance();
            } catch (IllegalAccessException e) {
                //Logger.e(TAG, e.getMessage());
            } catch (InstantiationException e) {
                // Logger.e(TAG, e.getMessage());
            }
            if (logicManager != null) {
                logicManagerMap.put(className, logicManager);
            } else {
                // Logger.e(TAG, String.format("fail to find logic manager %s", className));
            }
        }

        return (T)logicManager;
    }

    /**
     * 获得实体类的类名
     * @param logicManagerClass
     * @return
     */
    protected <T> String getlogicManagerClassName(Class<T> logicManagerClass) {
        //用class name, 避免冲突
        return logicManagerClass.getName();
    }
}

