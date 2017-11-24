package com.proj.api.auth.gson;

import com.proj.api.exception.other.InvalidParamsException;

public class InformationCheckRecvGson {
    private String action;
    private String auth_id;
    private String user_id;
    private String username;
    private String phone_num;
    private String password_key;
    private int authority;
    private int status;
    private String check_code;

    public String getAction() throws InvalidParamsException {
        if (action == null) {
            throw new InvalidParamsException();
        }else {
            return action;
        }
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAuth_id() throws InvalidParamsException {
        if (auth_id == null) {
            throw new InvalidParamsException();
        }else {
            return auth_id;
        }
    }

    public void setAuth_id(String auth_id) {
        this.auth_id = auth_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getPassword_key() {
        return password_key;
    }

    public void setPassword_key(String password_key) {
        this.password_key = password_key;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCheck_code() throws InvalidParamsException {
        if (check_code == null) {
            throw new InvalidParamsException();
        } else {
            return check_code;
        }
    }

    public void setCheck_code(String check_code) {
        this.check_code = check_code;
    }
}
