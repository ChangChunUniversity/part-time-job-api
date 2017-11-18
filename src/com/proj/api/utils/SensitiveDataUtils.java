package com.proj.api.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by jangitlau on 2017/11/3.
 */
public class SensitiveDataUtils {
    public static final String sAuthPasswordSalt="";
    public static final String sPrePasswordSalt="";
    public static final String sTranPasswordSalt="";

    public static String toAuthpassword(String _sClearPassword){
        return DigestUtils.md5Hex(
                DigestUtils.md5Hex(_sClearPassword + SensitiveDataUtils.sPrePasswordSalt)
                        + SensitiveDataUtils.sAuthPasswordSalt);
    }

    public static String toTranpassword(String _sClearPassword){
        return DigestUtils.md5Hex(_sClearPassword + SensitiveDataUtils.sTranPasswordSalt);
    }
}
