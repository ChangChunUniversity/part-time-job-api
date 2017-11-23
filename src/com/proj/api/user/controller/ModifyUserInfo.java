package com.proj.api.user.controller;
/**
 * author:Jerry
 */

import com.proj.api.database.RelationalDatabase;
import com.proj.api.exception.user.ConvertUserTypeException;
import com.proj.api.exception.database.NonRelationalDatabaseException;
import com.proj.api.exception.database.RelationalDatabaseException;
import com.proj.api.exception.other.InvalidCheckCodeException;
import com.proj.api.exception.other.InvalidParamsException;
import com.proj.api.exception.user.*;
import com.proj.api.exception.utils.AESDecryptException;
import com.proj.api.exception.utils.MalformedJsonException;
import com.proj.api.utils.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModifyUserInfo {

    String sUserId;
    boolean bUsername = false;
    boolean bPhone_num = false;
    boolean bPassword = false;
    boolean bType = false;
    boolean bAuthority = false;
    boolean bStatus = false;

    public ModifyUserInfo(String action, String auth_id, String user_id, String username, String phone_num, String password_key,
                          int type, int authority, int status, String check_code)
            throws NonRelationalDatabaseException, UserNotAuthorizedException, InvalidOperationException, InvalidCheckCodeException,
            UserAlreadyExistException, RelationalDatabaseException, InvalidParamsException, AESDecryptException, UserNotExistException,
            MalformedJsonException, InvalidBackstageOperationException, UserDisableException, ConvertUserTypeException {
        AuthorizationUtils authorizationUtils = new AuthorizationUtils(auth_id);

        if (authorizationUtils.getiType() != 3||authorizationUtils.getiAuthority()<5) {
            throw new InvalidOperationException();
        }
        authorizationUtils.checkParams(action + auth_id + String.valueOf(authority) + password_key + String.valueOf(status) + String.valueOf(type) + user_id + username, check_code);

        String sPassword = "";
        if (password_key != null && password_key != "") {
            sPassword = AESUtils.decryptData(password_key, authorizationUtils.getsToken());
        }

        switch (action) {
            case "add":
                if(authorizationUtils.getiAuthority()<=10){
                    throw new InvalidBackstageOperationException();
                }
                addUser(user_id, username, phone_num, sPassword, type, authority, status);
                break;
            case "del":
                if(authorizationUtils.getiAuthority()<=10&&type==3){
                    throw new InvalidBackstageOperationException();
                }
                delUser(user_id);
                break;
            case "mod":
                if(authorizationUtils.getiAuthority()<=10&&type==3){
                    throw new InvalidBackstageOperationException();
                }
                modUser(user_id, username, phone_num, sPassword, type, authority, status);
                break;
            default:
                throw new InvalidParamsException();
        }

    }

    private void addUser(String sUserId, String username, String phone_num, String password, int type, int authority, int status)
            throws NonRelationalDatabaseException, RelationalDatabaseException, InvalidParamsException, UserAlreadyExistException, ConvertUserTypeException {
        //验证输入数据的有效性
        if (username == null && password == null && username == "" && password == "") {
            throw new InvalidParamsException();
        }
        if (type < 0 && authority < 0 && status < 0) {
            throw new InvalidParamsException();
        }
        if(status==2){
            throw new ConvertUserTypeException();
        }
        //检查用户名是否存在
        RelationalDatabase rConn = new RelationalDatabase();
        try {
            ResultSet result = rConn.doQuery("SELECT uuid FROM user_auth WHERE username=?", new String[]{username});
            if (result.first()) {
                rConn.close();
                throw new UserAlreadyExistException();
            }
            String uuid = RandomUtils.getUUID();
            String sTranPassword = SensitiveDataUtils.toTranpassword(password);
            String sAuthPassword = SensitiveDataUtils.toAuthpassword(password);
            rConn.doSQL("insert INTO user_auth (uuid,username,phone_num,tran_password,auth_password,type,authority,status) " +
                    "VALUES(?,?,?,?,?,?,?,?) ", new Object[]{uuid, username, phone_num, sTranPassword, sAuthPassword, type, authority, status});
            if (result.first()) {
                rConn.close();
                throw new UserAlreadyExistException();
            }
            this.sUserId = uuid;
            this.bUsername = true;
            this.bPassword = true;
            this.bType = true;
            this.bAuthority = true;
            this.bStatus = true;
        } catch (SQLException e) {
            rConn.close();
            throw new RelationalDatabaseException(e);
        }
        rConn.close();
    }

    private void delUser(String user_id) throws InvalidParamsException, RelationalDatabaseException, UserAlreadyExistException, UserNotExistException {
        if (user_id == null) {
            throw new InvalidParamsException();
        }
        RelationalDatabase rConn = new RelationalDatabase();
        ResultSet result = rConn.doQuery("SELECT uuid FROM user_auth WHERE username=?", new String[]{user_id});
        try {
            if (!result.first()) {
                rConn.close();
                throw new UserNotExistException();
            }
            rConn.doSQL("DELETE FROM user_auth WHERE uuid = ?", new Object[]{user_id});
        } catch (SQLException e) {
            throw new RelationalDatabaseException(e);
        }
        rConn.close();
        this.sUserId=sUserId;
    }

    private void modUser(String sUserId, String username, String phone_num, String password, int type, int authority, int status) throws InvalidParamsException, RelationalDatabaseException, UserNotExistException, ConvertUserTypeException {
        //检查数据是否有效
        if (username == null && password == null) {
            throw new InvalidParamsException();
        }
        if (authority < 0) {
            throw new InvalidParamsException();
        }

        //检查用户名和手机号码
        RelationalDatabase rConn = new RelationalDatabase();
        ResultSet result = rConn.doQuery("SELECT uuid FROM user_auth WHERE username=? OR phone_num = ?", new String[]{username,phone_num});
        try {
            if (!result.first()) {
                rConn.close();
                throw new UserNotExistException();
            }
            if (username != "") {
                rConn.doSQL("UPDATE user_auth SET username = ? WHERE uuid=?", new Object[]{username, sUserId});
                this.bUsername = true;
            }
            if (phone_num != "") {
                rConn.doSQL("UPDATE user_auth SET phone_num = ? WHERE uuid=?", new Object[]{phone_num, sUserId});
                this.bPhone_num = true;
            }
            if (password != "") {
                String sTranPassword = SensitiveDataUtils.toTranpassword(password);
                String sAuthPassword = SensitiveDataUtils.toAuthpassword(password);
                rConn.doSQL("UPDATE user_auth SET tran_password = ?,auth_password = ? WHERE uuid=?", new Object[]{sTranPassword, sAuthPassword, sUserId});
                this.bPassword = true;
            }
            if (type != -1) {
                if(status==2){
                    throw new ConvertUserTypeException();
                }
                rConn.doSQL("UPDATE user_auth SET type = ? WHERE uuid=?", new Object[]{type, sUserId});
                this.bType = true;
            }
            if (authority != -1) {
                rConn.doSQL("UPDATE user_auth SET authority = ? WHERE uuid=?", new Object[]{authority, sUserId});
                this.bAuthority = true;
            }
            if (status != -1) {
                rConn.doSQL("UPDATE user_auth SET status = ? WHERE uuid=?", new Object[]{status, sUserId});
                this.bStatus = true;
            }
            this.sUserId=sUserId;
        } catch (SQLException e) {
            rConn.close();
            throw new RelationalDatabaseException(e);
        }
        rConn.close();
    }

    public String getsUserId() {
        return sUserId;
    }

    public boolean isbUsername() {
        return bUsername;
    }

    public boolean isbPassword() {
        return bPassword;
    }

    public boolean isbType() {
        return bType;
    }

    public boolean isbAuthority() {
        return bAuthority;
    }

    public boolean isbStatus() {
        return bStatus;
    }
}


