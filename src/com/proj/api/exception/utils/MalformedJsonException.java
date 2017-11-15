package com.proj.api.exception.utils;

import com.proj.api.exception.error.CustomError;

/**
 * Created by jangitlau on 2017/11/15.
 */
public class MalformedJsonException extends CustomError{
    public MalformedJsonException() {
        super(411);
    }
}
