package com.xinwis.okhttputilsdemo.login.model;

import android.text.TextUtils;

import com.xinwis.okhttputilsdemo.utils.JsonUtil;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * AccountInfo 包含账户信息
 * 直接在构造方法里把相关的数据解析出来
 * Created by zhengzhe on 15-9-12.
 */
public class AccountInfo implements Serializable {

    protected long mOrgId;//组织ID Long类型，必填，用户隶属于的组织层次包括集团（用户）、区域／分公司（用户）、社区/项目（用户）

    protected String mOrgName;//这里组织名称是用来判断权限,并且用于显示各个模块'切换那里的默认名称

    protected long mGrade;//用户层级：1集团 2区域公司 3区域子公司(区域分公司) 4项目 5部门6岗位

    protected long mUserId=-1L;//用户ID，Long类型

    protected String mUserName;//用户名(登录名)，string类型

    protected String mName;//显示名,string类型

    protected String mSex;//性别0女1男
    public static final String SEX_MALE = "1"; //男性代号为 1 ，静态
    public static final String SEX_FEMALE = "0"; //女性代号为 0   静态

    protected String mTelphone;//联系方式,手机号

    protected String mDeptId;//部门id，若有多个，以逗号隔开

    protected String mDeptName; // 部门名称，若有多个，以逗号隔开

    protected String mJobId;//职位（岗位）id，若有多个，以逗号隔开

    protected String mJobName;// 职位（岗位）名称，若有多个，以逗号隔开;

    protected String headImg;// 头像



    public AccountInfo() {
    }



    public AccountInfo(JSONObject json) {
        mOrgId = JsonUtil.getLong(json, "orgId");
        mOrgName = JsonUtil.getString(json, "orgName");
        mGrade = JsonUtil.getLong(json, "grade");
        mUserId = JsonUtil.getLong(json, "userId");
        mUserName = JsonUtil.getString(json, "userName");
        mName = JsonUtil.getString(json, "name");
        mSex = JsonUtil.getString(json, "sex");
        mTelphone = JsonUtil.getString(json, "telphone");
        mDeptId = JsonUtil.getString(json, "deptId");
        mDeptName = JsonUtil.getString(json, "deptName");
        mJobId = JsonUtil.getString(json, "jobId");
        mJobName = JsonUtil.getString(json, "jobName");
        headImg = JsonUtil.getString(json, "headImg");
    }

     public AccountInfo(long mOrgId,long mGrade,long mUserId,String mOrgName,String mUserName,String mName,String mTelphone,String mDeptId,String mDeptName,String mJobId,String mJobName,String mSex,String headImg){
         this.mOrgId = mOrgId;
         this.mOrgName = mOrgName;
         this.mGrade = mGrade;
         this.mUserId = mUserId;
         this.mUserName = mUserName;
         this.mName = mName;

         this.mTelphone = mTelphone;
         this.mDeptId = mDeptId;
         this.mDeptName = mDeptName;
         this.mJobId = mJobId;
         this.mJobName = mJobName;
         this.headImg = headImg;

         if (mSex!=null){
             if ( "保密".equals(mSex)){
                 this.mSex="";
             } else if ("男".equals(mSex)){
                 this.mSex =SEX_MALE;
             }else if ("女".equals(mSex)){
                 this.mSex =SEX_FEMALE;
             }
         }else {
             this.mSex="";
         }

     }



    public String getmOrgName() {
        return mOrgName;
    }
    public String getmHeadImg() {
        return headImg;
    }
    public void setmHeadImg(String url) {
         this.headImg=url;
    }

    public long getmGrade() {
        return mGrade;
    }

    public long getmOrgId() {
        return mOrgId;
    }

    public long getmUserId() {
        return mUserId;
    }

    public String getmUserName() {
        return mUserName;
    }

    public String getmNickName() {
        return mName;
    }

    public String getmSex() {
        if (mSex!=null){
            if (TextUtils.isEmpty(mSex.trim())){
                return "保密";
            }else {
                return mSex.equals(SEX_MALE) ? "男" : "女";
            }
        }else {
            return "保密";
        }
    }

    public static String getSexMale() {
        return SEX_MALE;
    }

    public static String getSexFemale() {
        return SEX_FEMALE;
    }

    public String getmTelphone() {
        return mTelphone;
    }

    public String getmDeptName() {
        return mDeptName;
    }

    public String getmDeptId() {
        return mDeptId;
    }

    public String getmJobId() {
        return mJobId;
    }

    public String getmJobName() {
        return mJobName;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "mOrgId=" + mOrgId +
                ", mOrgName='" + mOrgName + '\'' +
                ", mGrade=" + mGrade +
                ", mUserId=" + mUserId +
                ", mUserName='" + mUserName + '\'' +
                ", mName='" + mName + '\'' +
                ", mSex='" + mSex + '\'' +
                ", mTelphone='" + mTelphone + '\'' +
                ", mDeptId='" + mDeptId + '\'' +
                ", mDeptName='" + mDeptName + '\'' +
                ", mJobId='" + mJobId + '\'' +
                ", mJobName='" + mJobName + '\'' +
                ", headImg='" + headImg + '\'' +
                '}';
    }
}
