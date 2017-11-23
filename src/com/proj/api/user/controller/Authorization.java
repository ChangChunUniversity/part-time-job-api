package com.proj.api.user.controller;

import com.proj.api.database.KeyValueDatabase;
import com.proj.api.exception.database.NonRelationalDatabaseException;
import com.proj.api.exception.user.InvalidOperationException;
import com.proj.api.exception.user.PasswordNotCorrectException;
import com.proj.api.exception.user.UserDisableException;
import com.proj.api.exception.utils.AESEncryptException;
import com.proj.api.exception.utils.MalformedJsonException;
import com.proj.api.user.gson.LoggedInUserInfGson;
import com.proj.api.user.gson.PreAuthorizationGson;
import com.proj.api.utils.*;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by jangitlau on 2017/11/3.
 */
public class Authorization {
    private String iId;
    private String sUsername;
    private String sPreToken;
    private int iType;
    public Authorization(String _sUsername, String _sRandStr, String sPrePassword) throws NonRelationalDatabaseException, PasswordNotCorrectException, AESEncryptException, MalformedJsonException, InvalidOperationException, UserDisableException {
        KeyValueDatabase kvConn = new KeyValueDatabase(PreAuthorizationGson.sessionPrefix); //打开键值数据库
        /* 判断是否进行预授权，预授权时应以用户名为值向键值数据库
        储存关系型数据库user_auth表中关于该用户的所有信息 */
        if (!kvConn.exists(_sUsername)) {
            kvConn.close();
            throw new InvalidOperationException(); //未进行预授权，需要发出登录警告
        }
        PreAuthorizationGson preAuthorizationGson //获取预授权时获取的数据并进行解析
                = JsonUtils.fromJson(kvConn.get(_sUsername), PreAuthorizationGson.class);
        if (!_sRandStr.equals(preAuthorizationGson.getsRandomKey())) { //对比预授权时生成的随机数与上传的随机数是否一致
            kvConn.del(_sUsername);
            kvConn.close();
            throw new PasswordNotCorrectException(); //随机数不正确，说明密码不正确，需要发出登录警告
        }
        String sAuthPassword = DigestUtils.md5Hex(sPrePassword + SensitiveDataUtils.sAuthPasswordSalt);
        if (!sAuthPassword.equals(preAuthorizationGson.getsAuthPassword())) { //检查上传的授权密码是否与数据库中的符合
            kvConn.del(_sUsername);
            kvConn.close();
            throw new PasswordNotCorrectException(); //验证密码不正确，基本上在随机数正确的情况下是不会发生的，需要发出登录警告
        }
        if(preAuthorizationGson.getiStatus()==3){
            throw new UserDisableException();
        }
        kvConn.del(_sUsername);
        String sToken = RandomUtils.getRandomString(64); //生成64位的token以供服务端以及客户端进行通信
        LoggedInUserInfGson loggedInUserInfGson = new LoggedInUserInfGson( //构建结构储存用户的关键信息
                preAuthorizationGson.getsId()
                , preAuthorizationGson.getsUserName()
                , preAuthorizationGson.getiType()
                , preAuthorizationGson.getiAuthority()
                , preAuthorizationGson.getiStatus()
                , sToken
                , System.currentTimeMillis());
        String iId = preAuthorizationGson.getsId();
        kvConn.setPrefix(LoggedInUserInfGson.sessionPrefix);
        // 以用户ID为键值将用户信息(包括Token)储存进关系型数据库中
        kvConn.set(String.valueOf(iId), JsonUtils.toJson(loggedInUserInfGson), LoggedInUserInfGson.iSessionExpire);
        kvConn.close();
        this.iId=preAuthorizationGson.getsId();
        this.sUsername=preAuthorizationGson.getsUserName();
        //对Token进行加密并返回客户端
        this.sPreToken= AESUtils.encryptData(sToken, preAuthorizationGson.getsTranPassword());
        this.iType=preAuthorizationGson.getiType();
    }

    public String getiId() {
        return iId;
    }

    public String getsUsername() {
        return sUsername;
    }

    public String getsPreToken() {
        return sPreToken;
    }

    public int getiType() {
        return iType;
    }
}
