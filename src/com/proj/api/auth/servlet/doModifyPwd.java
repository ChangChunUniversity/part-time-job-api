package com.proj.api.auth.servlet;

import com.proj.api.exception.database.NonRelationalDatabaseException;
import com.proj.api.exception.database.RelationalDatabaseException;
import com.proj.api.exception.other.InvalidCheckCodeException;
import com.proj.api.exception.other.InvalidParamsException;
import com.proj.api.exception.auth.UserDisableException;
import com.proj.api.exception.auth.UserNotAuthorizedException;
import com.proj.api.exception.auth.UserNotExistException;
import com.proj.api.exception.utils.AESDecryptException;
import com.proj.api.exception.utils.MalformedJsonException;
import com.proj.api.auth.controller.ModifyPassword;
import com.proj.api.auth.gson.ModifyPasswordRecvGson;
import com.proj.api.auth.gson.ModifyPasswordRetGson;
import com.proj.api.utils.InputStrUtils;
import com.proj.api.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jangitlau on 2017/11/15.
 */
public class doModifyPwd extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String retStr = "";
        try {
            String recvStr = InputStrUtils.getRecvString(request);
            ModifyPasswordRecvGson modifyPasswordRecvGson = JsonUtils.fromJson(recvStr, ModifyPasswordRecvGson.class);

            ModifyPassword modifyPassword = new ModifyPassword(modifyPasswordRecvGson.getUser_id()
                    , modifyPasswordRecvGson.getExchange_password()
                    , modifyPasswordRecvGson.getCheck_code());

            retStr = JsonUtils.toJson(new ModifyPasswordRetGson());
        } catch (InvalidParamsException e) {
            retStr = e.getRetJson();
        } catch (InvalidCheckCodeException e) {
            retStr = e.getRetJson();
        } catch (AESDecryptException e) {
            retStr = e.getRetJson();
        } catch (UserNotExistException e) {
            retStr = e.getRetJson();
        } catch (UserNotAuthorizedException e) {
            retStr = e.getRetJson();
        } catch (RelationalDatabaseException e) {
            retStr = e.getRetJson();
        } catch (NonRelationalDatabaseException e) {
            retStr = e.getRetJson();
        } catch (MalformedJsonException e) {
            retStr = e.getRetJson();
        } catch (UserDisableException e) {
            retStr = e.getRetJson();
        }
        response.setHeader("content-type", "text/html;charset=utf-8");
        response.getWriter().print(retStr);
    }

}
