package com.proj.api.user.Servlet;

import com.google.gson.Gson;
import com.proj.api.exception.database.NonRelationalDatabaseException;
import com.proj.api.exception.database.RelationalDatabaseException;
import com.proj.api.exception.error.ErrGsonStructure;
import com.proj.api.exception.other.InvalidParamsException;
import com.proj.api.exception.user.InvaildOperationException;
import com.proj.api.exception.user.PasswordNotCorrectException;
import com.proj.api.exception.user.PhoneAlreadyExistException;
import com.proj.api.exception.user.UserAlreadyExistException;
import com.proj.api.exception.utils.AESDecryptException;
import com.proj.api.exception.utils.AESEncryptException;
import com.proj.api.user.controller.Authorization;
import com.proj.api.user.controller.PreRegistration;
import com.proj.api.user.controller.Registration;
import com.proj.api.user.gson.*;
import com.proj.api.utils.InputStrUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jangitlau on 2017/11/3.
 */
public class doReg extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sCallback = request.getParameter("callback");
        Gson json = new Gson();
        String retStr = "";
        try {
            String recvStr = InputStrUtils.getRecvString(request);
            RegistrationRecvGson registrationRecvGson = json.fromJson(recvStr, RegistrationRecvGson.class);
            Registration registration = new Registration(
                    registrationRecvGson.getUsername()
                    , registrationRecvGson.getPhone_num()
                    , registrationRecvGson.getPassword_key()
                    , registrationRecvGson.getType());
            RegistrationRetGson registrationRetGson = new RegistrationRetGson(
                    registration.getsPreToken()
                    , registration.getsId()
                    , registration.getiType());
            retStr = json.toJson(registrationRetGson);
        } catch (InvalidParamsException e) {
            retStr = e.getRetJson();
        } catch (UserAlreadyExistException e) {
            retStr = e.getRetJson();
        } catch (AESDecryptException e) {
            retStr = e.getRetJson();
        } catch (AESEncryptException e) {
            retStr = e.getRetJson();
        } catch (NonRelationalDatabaseException e) {
            retStr = e.getRetJson();
        } catch (RelationalDatabaseException e) {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sCallback = request.getParameter("callback");
        String retStr = "";
        Gson json = new Gson();
        try {
            String sPhoneNum = request.getParameter("phone_num");
            if (sPhoneNum == null) {
                throw new InvalidParamsException();
            }
            PreRegistration preRegistration = new PreRegistration(sPhoneNum);
            PreRegistrationRetGson preRegistrationRetGson = new PreRegistrationRetGson(
                    preRegistration.getsPhoneNum(), preRegistration.getsKey());
            retStr = json.toJson(preRegistrationRetGson);
        } catch (InvalidParamsException e) {
            retStr = e.getRetJson();
        } catch (UserAlreadyExistException e) {
            retStr = e.getRetJson();
        } catch (AESEncryptException e) {
            retStr = e.getRetJson();
        } catch (NonRelationalDatabaseException e) {
            retStr = e.getRetJson();
        } catch (RelationalDatabaseException e) {
            retStr = e.getRetJson();
        } catch (PhoneAlreadyExistException e) {
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
