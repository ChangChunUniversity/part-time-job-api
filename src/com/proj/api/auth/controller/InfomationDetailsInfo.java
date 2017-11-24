package com.proj.api.auth.controller;
/**
 * author:Jerry
 */
import com.proj.api.database.RelationalDatabase;
import com.proj.api.exception.database.NonRelationalDatabaseException;
import com.proj.api.exception.database.RelationalDatabaseException;
import com.proj.api.exception.other.InvalidCheckCodeException;
import com.proj.api.exception.auth.*;
import com.proj.api.exception.utils.MalformedJsonException;
import com.proj.api.utils.AuthorizationUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InfomationDetailsInfo {
    private String sUserId;
    private String sUserName;
    private String sPhoneNum;
    private int iType;
    private int iAuthority;
    private int iStatus;
    private String sCheckCode;

    public InfomationDetailsInfo(String auth_id, String user_id, String check_code) throws RelationalDatabaseException, InvalidOperationException, UserNotAuthorizedException, UserDisableException, NonRelationalDatabaseException, MalformedJsonException, InvalidCheckCodeException, UserNotExistException, InvalidBackstageOperationException {
        AuthorizationUtils authorizationUtils = new AuthorizationUtils(auth_id);
        authorizationUtils.checkParams(auth_id + user_id, check_code);
        if (authorizationUtils.getsId() != user_id && authorizationUtils.getiType() != 3) {
            throw new InvalidOperationException();
        }
        if (authorizationUtils.getiAuthority() < 2) {
            throw new InvalidBackstageOperationException();
        }
        RelationalDatabase rConn = new RelationalDatabase();
        ResultSet result = rConn.doQuery("SELECT uuid,username,phone_num,type,authority,status FROM user_auth WHERE uuid=?", new String[]{user_id});
        try {
            if (result.first()) {
                this.sUserId=result.getString("uuid");
                this.sUserName=result.getString("username");
                this.sPhoneNum=result.getString("phone_num");
                this.iType=result.getInt("type");
                this.iAuthority=result.getInt("authority");
                this.iStatus=result.getInt("status");
            }else{
                rConn.close();
                throw new UserNotExistException();
            }
        } catch (SQLException e) {
            rConn.close();
            throw new RelationalDatabaseException(e);
        }
        this.sCheckCode=authorizationUtils.getCheckCode(
                String.valueOf(this.iAuthority)
                +sPhoneNum
                +String.valueOf(this.iStatus)
                +String.valueOf(this.iType)
                +sUserId
                +sUserName
        );
    }

    public String getsUserId() {
        return sUserId;
    }

    public String getsUserName() {
        return sUserName;
    }

    public String getsPhoneNum() {
        return sPhoneNum;
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

    public String getsCheckCode() {
        return sCheckCode;
    }
}