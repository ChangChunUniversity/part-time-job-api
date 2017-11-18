package com.proj.api.user.controller;

import com.proj.api.database.KeyValueDatabase;
import com.proj.api.database.RelationalDatabase;
import com.proj.api.exception.database.NonRelationalDatabaseException;
import com.proj.api.exception.database.RelationalDatabaseException;
import com.proj.api.exception.user.InvalidOperationException;
import com.proj.api.exception.user.UserAlreadyExistException;
import com.proj.api.exception.utils.AESDecryptException;
import com.proj.api.exception.utils.AESEncryptException;
import com.proj.api.exception.utils.MalformedJsonException;
import com.proj.api.user.gson.LoggedInUserInfGson;
import com.proj.api.user.gson.PreRegistrationInfGson;
import com.proj.api.utils.AESUtils;
import com.proj.api.utils.JsonUtils;
import com.proj.api.utils.RandomUtils;
import com.proj.api.utils.SensitiveDataUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by jangitlau on 2017/11/3.
 */
public class Registration {
    private String sPreToken;
    private String sId;
    private int iType;

    public Registration(String _sUsername,String _sPhoneNum,String _sPasswordKey,int _iType) throws RelationalDatabaseException, NonRelationalDatabaseException, AESDecryptException, AESEncryptException, UserAlreadyExistException, MalformedJsonException, InvalidOperationException {
        this.iType = _iType;
        KeyValueDatabase kvConn = new KeyValueDatabase(PreRegistrationInfGson.sessionPrefix);
        if (!kvConn.exists(_sPhoneNum)) {
            kvConn.close();
            throw new InvalidOperationException();
        }
        PreRegistrationInfGson preRegistrationInfGson = JsonUtils.fromJson(kvConn.get(_sPhoneNum), PreRegistrationInfGson.class);
        String sClearPassword = "";
        try {
            sClearPassword = AESUtils.decryptData(_sPasswordKey, preRegistrationInfGson.getRand_str()).replace("\0","");
        } catch (Exception e) {
            kvConn.close();
            throw new AESDecryptException();
        }
        RelationalDatabase rConn = new RelationalDatabase();
        try{
            ResultSet result=rConn.doQuery("SELECT uuid FROM user_auth WHERE username=?",new String[]{_sUsername});
            if(result.first()){
                kvConn.close();
                rConn.close();
                throw new UserAlreadyExistException();
            }
        }catch (SQLException e){
            kvConn.close();
            rConn.close();
            throw new RelationalDatabaseException(e);
        }
        this.sId = UUID.randomUUID().toString();
        System.out.println("Clear PWD:"+sClearPassword);
        String sTranPassword = SensitiveDataUtils.toTranpassword(sClearPassword);
        String sAuthPassword = SensitiveDataUtils.toAuthpassword(sClearPassword);
        rConn.doSQL("INSERT INTO user_auth(uuid,username,phone_num,tran_password,auth_password,type,authority,status) VALUES(?,?,?,?,?,?,?,?)"
                , new Object[]{this.sId, _sUsername, _sPhoneNum, sTranPassword, sAuthPassword, _iType, 0, 0});
        rConn.close();
        String sToken=RandomUtils.getRandomString(64);
        this.sPreToken = AESUtils.encryptData(sToken, preRegistrationInfGson.getRand_str());
        LoggedInUserInfGson loggedInUserInfGson = new LoggedInUserInfGson(this.sId, _sUsername, iType, 0, 0, sToken, System.currentTimeMillis());
        kvConn.setPrefix(LoggedInUserInfGson.sessionPrefix);
        kvConn.set(String.valueOf(this.sId), JsonUtils.toJson(loggedInUserInfGson), LoggedInUserInfGson.iSessionExpire);
        kvConn.close();
    }

    public String getsPreToken() {
        return sPreToken;
    }

    public String getsId() {
        return sId;
    }


    public int getiType() {
        return iType;
    }
}
