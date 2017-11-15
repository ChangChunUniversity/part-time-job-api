package com.proj.api.user.gson;

import com.proj.api.exception.error.Reason;

/**
 * Created by jangitlau on 2017/11/15.
 */
public class ModifyPasswordRetGson {
    private int err_code = 0;
    private String Reason = com.proj.api.exception.error.Reason.getReason(err_code);

    public int getErr_code() {
        return err_code;
    }

    public String getReason() {
        return Reason;
    }
}
