package com.proj.api.user.gson;

import com.proj.api.exception.other.InvalidParamsException;

/**
 * Created by jangitlau on 2017/11/3.
 */
public class RegistrationRecvGson {
    private String username;
    private String phone_num;
    private String password_key;
    private int type;

    public String getPhone_num() throws InvalidParamsException {
        if (phone_num == null) {
            throw new InvalidParamsException();
        } else {
            return phone_num;
        }
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getUsername() throws InvalidParamsException {
        if (username == null) {
            throw new InvalidParamsException();
        } else {
            return username;
        }
    }

    public void setUsername(String username) {
        System.out.println("username:"+username);
        this.username = username;
    }

    public String getPassword_key() throws InvalidParamsException {
        if (password_key == null) {
            throw new InvalidParamsException();
        } else {
            return password_key;
        }
    }

    public void setPassword_key(String password_key) {
        System.out.println("password_key:"+password_key);
        this.password_key = password_key;
    }

    public int getType() throws InvalidParamsException {
        if (this.type != 0 && this.type != 1) {
            throw new InvalidParamsException();
        } else {
            return type;
        }
    }

    public void setType(int type) {
        System.out.println("type:"+String.valueOf(type));
        this.type = type;
    }
}
