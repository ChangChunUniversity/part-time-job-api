package com.proj.api.auth.gson;
/**
 * author:Jerry
 */
import com.proj.api.exception.error.Reason;

public class RegisterInformationRetGson {
    private int err_code=0;
    private String reason= Reason.getReason(0);
    private InnerData data;
    public RegisterInformationRetGson(boolean username,boolean phone_num) {
        this.data=new InnerData(username,phone_num);
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

    public class InnerData{
        boolean username;
        boolean phone_num;

        public InnerData(boolean username, boolean phone_num) {
            this.username = username;
            this.phone_num = phone_num;
        }

        public boolean isUsername() {
            return username;
        }

        public boolean isPhone_num() {
            return phone_num;
        }
    }
}
