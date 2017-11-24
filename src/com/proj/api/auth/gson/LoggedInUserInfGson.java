package com.proj.api.auth.gson;

/**
 * Created by jangitlau on 2017/11/3.
 */
public class LoggedInUserInfGson {
    public final static int iSessionExpire = 120;
    public final static String sessionPrefix = "user_inf_";

    private String sId;
    private String sUserName;
    private int iType;
    private int iAuthority;
    private int iStatus;
    private String sToken;
    private long lLoginTime;

    public LoggedInUserInfGson(String sId, String sUserName, int iType, int iAuthority, int iStatus, String sToken, long lLoginTime) {
        this.sId = sId;
        this.sUserName = sUserName;
        this.iType = iType;
        this.iAuthority = iAuthority;
        this.iStatus = iStatus;
        this.sToken = sToken;
        this.lLoginTime = lLoginTime;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsUserName() {
        return sUserName;
    }

    public void setsUserName(String sUserName) {
        this.sUserName = sUserName;
    }

    public int getiType() {
        return iType;
    }

    public void setiType(int iType) {
        this.iType = iType;
    }

    public int getiAuthority() {
        return iAuthority;
    }

    public void setiAuthority(int iAuthority) {
        this.iAuthority = iAuthority;
    }

    public int getiStatus() {
        return iStatus;
    }

    public void setiStatus(int iStatus) {
        this.iStatus = iStatus;
    }

    public String getsToken() {
        return sToken;
    }

    public void setsToken(String sToken) {
        this.sToken = sToken;
    }

    public long getlLoginTime() {
        return lLoginTime;
    }

    public void setlLoginTime(long lLoginTime) {
        this.lLoginTime = lLoginTime;
    }
}
