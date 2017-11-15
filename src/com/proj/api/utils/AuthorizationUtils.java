package com.proj.api.utils;

import com.google.gson.Gson;
import com.proj.api.database.KeyValueDatabase;
import com.proj.api.exception.database.NonRelationalDatabaseException;
import com.proj.api.exception.other.InvalidCheckCodeException;
import com.proj.api.exception.user.UserNotAuthorizedException;
import com.proj.api.exception.utils.MalformedJsonException;
import com.proj.api.user.gson.LoggedInUserInfGson;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by jangitlau on 2017/11/5.
 */
public class AuthorizationUtils {
    private String sId;
    private String sUserName;
    private int iType;
    private int iAuthority;
    private int iStatus;
    private String sToken;
    private long lLoginTime;

    public AuthorizationUtils(String _sUserId) throws NonRelationalDatabaseException, UserNotAuthorizedException, MalformedJsonException {
        KeyValueDatabase kvConn=new KeyValueDatabase(LoggedInUserInfGson.sessionPrefix);
        if(!kvConn.exists(_sUserId)){
            kvConn.close();
            throw new UserNotAuthorizedException();
        }
        LoggedInUserInfGson loggedInUserInfGson=JsonUtils.fromJson(kvConn.get(_sUserId),LoggedInUserInfGson.class);
        kvConn.close();
        this.sId=loggedInUserInfGson.getsId();
        this.sUserName=loggedInUserInfGson.getsUserName();
        this.iType=loggedInUserInfGson.getiType();
        this.iAuthority=loggedInUserInfGson.getiAuthority();
        this.iStatus=loggedInUserInfGson.getiStatus();
        this.sToken=loggedInUserInfGson.getsToken();
        this.lLoginTime=loggedInUserInfGson.getlLoginTime();
    }

    //验证参数是否正确，输入验证参数按照字母顺序排列的值以及验证码，错误抛出异常(全部小写字母)
    public void checkParams(String _sParamSet,String _CheckCode) throws InvalidCheckCodeException {
        if(!DigestUtils.md5Hex((_sParamSet+this.sToken).toLowerCase()).toLowerCase().equals(_CheckCode.toLowerCase())){
            throw new InvalidCheckCodeException();
        }
    }

    public String getsId() {
        return sId;
    }

    public String getsUserName() {
        return sUserName;
    }

    public int getiType() {
        return iType;
    }

    public int getiAuthority() {
        return iAuthority;
    }

    public int getiStatus() {
        return iStatus;
    }

    public String getsToken() {
        return sToken;
    }

    public long getlLoginTime() {
        return lLoginTime;
    }
}
