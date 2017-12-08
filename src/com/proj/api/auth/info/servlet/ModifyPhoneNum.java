package com.proj.api.auth.info.servlet;

import com.proj.api.auth.info.controller.PreModifyPhoneNum;
import com.proj.api.auth.info.gson.ModifyPhoneNumRecvGson;
import com.proj.api.auth.info.gson.ModifyPhoneNumRetGson;
import com.proj.api.auth.info.gson.PreModifyPhoneNumRetGson;
import com.proj.api.exception.other.InvalidParamsException;
import com.proj.api.exception.utils.MalformedJsonException;
import com.proj.api.utils.InputStrUtils;
import com.proj.api.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jangitlau on 2017/12/6.
 */
public class ModifyPhoneNum extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String retStr = "";
        try {
            InputStrUtils inputStrUtils = new InputStrUtils(request);
            String recvStr = inputStrUtils.getRecvString();
            ModifyPhoneNumRecvGson modifyPhoneNumRecvGson=JsonUtils.fromJson(recvStr,ModifyPhoneNumRecvGson.class);
            com.proj.api.auth.info.controller.ModifyPhoneNum modifyPhoneNum = new com.proj.api.auth.info.controller.ModifyPhoneNum(
                    modifyPhoneNumRecvGson.getLogin_id(),
                    modifyPhoneNumRecvGson.getPhone_checkcode(),
                    modifyPhoneNumRecvGson.getCheck_code()
            );
            retStr= JsonUtils.toJson(new ModifyPhoneNumRetGson());
        } catch (InvalidParamsException e) {
            retStr=e.getRetJson();
        } catch (MalformedJsonException e) {
            retStr=e.getRetJson();
        }
        response.setHeader("content-type", "text/html;charset=utf-8");
        response.getWriter().print(retStr);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String retStr = "";
        try {
            InputStrUtils inputStrUtils = new InputStrUtils(request);
            String sLoginId = inputStrUtils.getRequiredParameter("login_id");
            String sPhoneNum = inputStrUtils.getRequiredParameter("phone_num");
            PreModifyPhoneNum preModifyPhoneNum = new PreModifyPhoneNum(sLoginId, sPhoneNum);
            retStr= JsonUtils.toJson(new PreModifyPhoneNumRetGson());
        } catch (InvalidParamsException e) {
            retStr=e.getRetJson();
        }
        response.setHeader("content-type", "text/html;charset=utf-8");
        response.getWriter().print(retStr);
    }
}
