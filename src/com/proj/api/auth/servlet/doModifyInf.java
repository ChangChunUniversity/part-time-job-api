package com.proj.api.auth.servlet;

import com.proj.api.auth.controller.ModifyUserInf;
import com.proj.api.auth.gson.ModifyInfRecvGson;
import com.proj.api.auth.gson.ModifyInfRetGson;
import com.proj.api.exception.auth.*;
import com.proj.api.exception.database.NonRelationalDatabaseException;
import com.proj.api.exception.database.RelationalDatabaseException;
import com.proj.api.exception.other.InvalidCheckCodeException;
import com.proj.api.exception.other.InvalidParamsException;
import com.proj.api.exception.utils.AESDecryptException;
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
 * Created by jangitlau on 2017/11/27.
 */
public class doModifyInf extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String retStr = "";
        try {
            String sRecvStr = InputStrUtils.getRecvString(request);
            ModifyInfRecvGson modifyInfRecvGson = JsonUtils.fromJson(sRecvStr, ModifyInfRecvGson.class);
            ModifyUserInf modifyUserInf = new ModifyUserInf(
                    modifyInfRecvGson.getAction(),
                    modifyInfRecvGson.getAuth_id(),
                    modifyInfRecvGson.getUser_id(),
                    modifyInfRecvGson.getUsername(),
                    modifyInfRecvGson.getPhone_num(),
                    modifyInfRecvGson.getPassword_key(),
                    modifyInfRecvGson.getType(),
                    modifyInfRecvGson.getAuthority(),
                    modifyInfRecvGson.getStatus(),
                    modifyInfRecvGson.getCheck_code()
            );
            ModifyInfRetGson modifyInfRetGson = new ModifyInfRetGson(
                    modifyUserInf.getsUserId(),
                    modifyUserInf.isbUsername(),
                    modifyUserInf.isbPassword(),
                    modifyUserInf.isbPhone_num(),
                    modifyUserInf.isbType(),
                    modifyUserInf.isbAuthority(),
                    modifyUserInf.isbStatus(),
                    modifyUserInf.getCheck_code()
            );
            retStr = JsonUtils.toJson(modifyInfRetGson);
        } catch (InvalidParamsException e) {
            retStr = e.getRetJson();
        } catch (MalformedJsonException e) {
            retStr = e.getRetJson();
        } catch (UserNotExistException e) {
            retStr = e.getRetJson();
        } catch (UserDisableException e) {
            retStr = e.getRetJson();
        } catch (UserNotAuthorizedException e) {
            retStr = e.getRetJson();
        } catch (ConvertUserTypeException e) {
            retStr = e.getRetJson();
        } catch (NonRelationalDatabaseException e) {
            retStr = e.getRetJson();
        } catch (InvalidBackstageOperationException e) {
            retStr = e.getRetJson();
        } catch (InvalidOperationException e) {
            retStr = e.getRetJson();
        } catch (UserAlreadyExistException e) {
            retStr = e.getRetJson();
        } catch (InvalidCheckCodeException e) {
            retStr = e.getRetJson();
        } catch (AESDecryptException e) {
            retStr = e.getRetJson();
        } catch (RelationalDatabaseException e) {
            retStr = e.getRetJson();
        }
    }

}
