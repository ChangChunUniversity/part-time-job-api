package com.proj.api.user.gson;

import com.proj.api.exception.error.Reason;

/**
 * Created by jangitlau on 2017/11/3.
 */
public class AuthorizationRetGson {
    private int err_code=0;
    private String reason= Reason.getReason(0);
    private InnerData data;

    public AuthorizationRetGson(String _sUsername,String _sUserId,String sPreToken,int iType) {
        this.data=new InnerData(_sUsername,_sUserId,sPreToken,iType);
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
        String username;
        String user_id;
        String pre_token;
        int type;

        public InnerData(String username, String user_id, String pre_token, int type) {
            this.username = username;
            this.user_id = user_id;
            this.pre_token = pre_token;
            this.type = type;
        }

        public String getUsername() {
            return username;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getPre_token() {
            return pre_token;
        }

        public int getType() {
            return type;
        }
    }
}
