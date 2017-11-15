package com.proj.api.exception.user;

import com.proj.api.exception.error.CustomError;

/**
 * Created by jangitlau on 2017/11/2.
 */
public class UsernameNotExistException extends CustomError {
    private static final int err_code = 402;
    public UsernameNotExistException() {
        super(err_code);
    }
}
