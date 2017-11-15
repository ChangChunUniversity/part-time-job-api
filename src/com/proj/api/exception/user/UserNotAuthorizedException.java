package com.proj.api.exception.user;

import com.proj.api.exception.error.CustomError;

/**
 * Created by jangitlau on 2017/11/5.
 */
public class UserNotAuthorizedException extends CustomError{
    private static final int err_code = 410;
    protected UserNotAuthorizedException() {
        super(err_code);
    }
}
