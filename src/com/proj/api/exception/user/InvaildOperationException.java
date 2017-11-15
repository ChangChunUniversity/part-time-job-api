package com.proj.api.exception.user;

import com.proj.api.exception.error.CustomError;

/**
 * Created by jangitlau on 2017/11/3.
 */
public class InvaildOperationException extends CustomError{
    private static final int err_code = 409;
    protected InvaildOperationException() {
        super(err_code);
    }
}
