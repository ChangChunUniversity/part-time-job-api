package com.proj.api.utils;

import com.proj.api.exception.other.InvalidParamsException;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

/**
 * Created by jangitlau on 2017/11/3.
 */
public class InputStrUtils {
    private HttpServletRequest oRequest;
    public InputStrUtils(HttpServletRequest _oRequest){
        this.oRequest=_oRequest;
    }

    public String getRecvString() throws InvalidParamsException {
        StringBuffer buffer = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = this.oRequest.getReader();
            while((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            throw new InvalidParamsException();
        }
        String recvStr=buffer.toString();
        if(recvStr==null||recvStr==""){
            return "{}";
        }else{
            return buffer.toString();
        }
    }

    public String getPathInfo() {
        String sPathInfo = this.oRequest.getPathInfo().replace("/","").trim();
        if (sPathInfo == null || sPathInfo == "") {
            return "";
        }
        return sPathInfo;
    }

    public String getRequiredParameter(String sParamName) throws InvalidParamsException {
        String recvStr = this.oRequest.getParameter(sParamName);
        if(recvStr==null||recvStr==""){
            throw new InvalidParamsException();
        }
        return recvStr;
    }

    public String getOptionalParameter(String sParamName) {
        return this.oRequest.getParameter(sParamName);
    }

    public static boolean alertIfNull(String _sValue) throws InvalidParamsException {
        if(_sValue==""||_sValue==null){
            throw new InvalidParamsException();
        }
        return false;
    }

    public static boolean alertIfNull(int _iValue) throws InvalidParamsException {
        if(_iValue<=0){
            throw new InvalidParamsException();
        }
        return false;
    }
}
