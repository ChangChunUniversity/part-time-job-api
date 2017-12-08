package com.proj.api.auth.backstage.gson;

/**
 * Created by jangitlau on 2017/12/8.
 */
public class AddUserInfoRecvGson {
    private String login_id;
    private String username;
    private String phone_num;
    private String password_key;
    private int type;
    private int authority;
    private int status;
    private String check_code;

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getCheck_code() {
        return check_code;
    }

    public void setCheck_code(String check_code) {
        this.check_code = check_code;
    }
}
