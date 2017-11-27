package com.proj.api.auth.gson;

import com.proj.api.exception.error.Reason;

/**
 * Created by jangitlau on 2017/11/27.
 */
public class ModifyInfRetGson {
    private int err_code=0;
    private String reason= Reason.getReason(err_code);
    private InnerData data;
    private String check_code;

    public ModifyInfRetGson(String user_id, boolean username, boolean password, boolean phone_num, boolean type, boolean authority, boolean status,String _check_code) {
        this.data = new InnerData(user_id,username,password,phone_num,type,authority,status);
        this.check_code = _check_code;
    }

    public int getErr_code() {
        return err_code;
    }

    public String getReason() {
        return reason;
    }

    public InnerData getData() {
        return data;
    }

    public String getCheck_code() {
        return check_code;
    }

    public class InnerData{
        private String user_id;
        private boolean username;
        private boolean password;
        private boolean phone_num;
        private boolean type;
        private boolean authority;
        private boolean status;

        public InnerData(String user_id, boolean username, boolean password, boolean phone_num, boolean type, boolean authority, boolean status) {
            this.user_id = user_id;
            this.username = username;
            this.password = password;
            this.phone_num = phone_num;
            this.type = type;
            this.authority = authority;
            this.status = status;
        }

        public String getUser_id() {
            return user_id;
        }

        public boolean isUsername() {
            return username;
        }

        public boolean isPassword() {
            return password;
        }

        public boolean isPhone_num() {
            return phone_num;
        }

        public boolean isType() {
            return type;
        }

        public boolean isAuthority() {
            return authority;
        }

        public boolean isStatus() {
            return status;
        }
    }
}
