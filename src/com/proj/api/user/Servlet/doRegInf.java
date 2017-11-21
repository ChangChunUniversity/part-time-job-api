package com.proj.api.user.Servlet;

import com.proj.api.exception.database.NonRelationalDatabaseException;
import com.proj.api.exception.database.RelationalDatabaseException;
import com.proj.api.exception.user.UserDisableException;
import com.proj.api.exception.user.UserNotAuthorizedException;
import com.proj.api.exception.utils.MalformedJsonException;
import com.proj.api.user.controller.RegisterInformation;
import com.proj.api.user.gson.RegisterInformationRetGson;
import com.proj.api.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class doRegInf extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String retStr = "";

        try {
            String sUsername = request.getParameter("username");
            String phone_num = request.getParameter("phone_num");
            RegisterInformation registerInformation = new RegisterInformation(sUsername,phone_num);
            RegisterInformationRetGson registerInformationRetGson=new RegisterInformationRetGson(
                    registerInformation.isbUsername()
                    ,registerInformation.isbPhoneNum()
            );
            retStr = JsonUtils.toJson(registerInformationRetGson);
        } catch (UserNotAuthorizedException e) {
            retStr = e.getRetJson();
        } catch (UserDisableException e) {
            retStr = e.getRetJson();
        } catch (NonRelationalDatabaseException e) {
            retStr = e.getRetJson();
        } catch (MalformedJsonException e) {
            retStr = e.getRetJson();
        } catch (RelationalDatabaseException e) {
            retStr = e.getRetJson();
        }
        response.setHeader("content-type", "text/html;charset=utf-8");
        response.getWriter().print(retStr);
    }
}
