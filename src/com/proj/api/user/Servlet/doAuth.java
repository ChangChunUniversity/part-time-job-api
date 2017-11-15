package com.proj.api.user.Servlet;

import com.google.gson.Gson;
import com.proj.api.exception.database.NonRelationalDatabaseException;
import com.proj.api.exception.database.RelationalDatabaseException;
import com.proj.api.exception.error.ErrGsonStructure;
import com.proj.api.exception.other.InvalidParamsException;
import com.proj.api.exception.user.InvaildOperationException;
import com.proj.api.exception.user.PasswordNotCorrectException;
import com.proj.api.exception.user.UsernameNotExistException;
import com.proj.api.exception.utils.AESEncryptException;
import com.proj.api.user.controller.Authorization;
import com.proj.api.user.controller.PreAuthorization;
import com.proj.api.user.gson.AuthorizationRecvGson;
import com.proj.api.user.gson.AuthorizationRetGson;
import com.proj.api.user.gson.PreAuthorizationRetGson;
import com.proj.api.utils.InputStrUtils;

import java.io.IOException;

/**
 * Created by jangitlau on 2017/11/3.
 */
public class doAuth extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String sCallback = request.getParameter("callback");
        Gson json = new Gson();
        String retStr = "";
        try {
            String recvStr = InputStrUtils.getRecvString(request);
            AuthorizationRecvGson authorizationRecvGson = json.fromJson(recvStr, AuthorizationRecvGson.class);
            Authorization authorization = new Authorization(
                    authorizationRecvGson.getUsername(),
                    authorizationRecvGson.getRand_str(),
                    authorizationRecvGson.getPre_password());
            AuthorizationRetGson authorizationRetGson = new AuthorizationRetGson(
                    authorization.getsUsername(),
                    authorization.getiId(),
                    authorization.getsPreToken());
            retStr = json.toJson(authorizationRetGson);
        } catch (InvalidParamsException e) {
            retStr = e.getRetJson();
        } catch (AESEncryptException e) {
            retStr = e.getRetJson();
        } catch (PasswordNotCorrectException e) {
            retStr = e.getRetJson();
        } catch (NonRelationalDatabaseException e) {
            retStr = e.getRetJson();
        } catch (InvaildOperationException e) {
            retStr = e.getRetJson();
        }
        response.setHeader("content-type", "text/html;charset=utf-8");
        if (sCallback == null) {
            response.getWriter().print(retStr);
        } else {
            response.getWriter().print(sCallback + "(" + retStr + ");");
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String sCallback = request.getParameter("callback");
        Gson json = new Gson();
        String retStr = "";
        try {
            String sUsername = request.getParameter("username");
            if (sUsername == null) {
                throw new InvalidParamsException();
            }
            PreAuthorization preAuthorization = new PreAuthorization(sUsername);
            PreAuthorizationRetGson preAuthorizationRetGson = new PreAuthorizationRetGson(
                    preAuthorization.getsUsername()
                    , preAuthorization.getsKey());
            retStr = json.toJson(preAuthorizationRetGson);
        } catch (InvalidParamsException e) {
            retStr = e.getRetJson();
        } catch (UsernameNotExistException e) {
            retStr = e.getRetJson();
        } catch (AESEncryptException e) {
            retStr = e.getRetJson();
        } catch (NonRelationalDatabaseException e) {
            retStr = e.getRetJson();
        } catch (RelationalDatabaseException e) {
            retStr = e.getRetJson();
        }
        response.setHeader("content-type", "text/html;charset=utf-8");
        if (sCallback == null) {
            response.getWriter().print(retStr);
        } else {
            response.getWriter().print(sCallback + "(" + retStr + ");");
        }
    }

}
