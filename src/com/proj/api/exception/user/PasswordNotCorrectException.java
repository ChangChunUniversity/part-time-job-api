package com.proj.api.exception.user;

import com.proj.api.exception.error.CustomError;

/**
 * Created by jangitlau on 2017/11/3.
 */
public class PasswordNotCorrectException extends CustomError{
    private static final int err_code = 406;
    protected PasswordNotCorrectException() {
        super(err_code);
    }
}
