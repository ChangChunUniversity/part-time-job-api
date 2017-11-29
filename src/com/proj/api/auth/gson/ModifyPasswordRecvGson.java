package com.proj.api.auth.gson;

import com.proj.api.exception.other.InvalidParamsException;

/**
 * Created by jangitlau on 2017/11/15.
 */
public class ModifyPasswordRecvGson {
    private String user_id;
    private String new_password;
    private String check_code;

    public String getUser_id() throws InvalidParamsException {
        if (user_id == null) {
            throw new InvalidParamsException();
        } else {
            return user_id;
        }
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNew_password() throws InvalidParamsException {
        if (new_password == null) {
            throw new InvalidParamsException();
        } else {
            return new_password;
        }
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
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
