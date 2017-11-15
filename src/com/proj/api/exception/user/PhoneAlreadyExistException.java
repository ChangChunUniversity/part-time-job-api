package com.proj.api.exception.user;

import com.proj.api.exception.error.CustomError;

/**
 * Created by jangitlau on 2017/11/7.
 */
public class PhoneAlreadyExistException extends CustomError {
    private static final int err_code = 407;
    protected PhoneAlreadyExistException() {
        super(err_code);
    }
}
