package com.proj.api.auth.servlet;

import com.proj.api.auth.controller.LoginUserInfo;
import com.proj.api.auth.controller.ModifyPassword;
import com.proj.api.auth.gson.LoginUserInfoRetGson;
import com.proj.api.auth.gson.ModifyInfRecvGson;
import com.proj.api.auth.gson.ModifyPasswordRecvGson;
import com.proj.api.auth.gson.ModifyPasswordRetGson;
import com.proj.api.exception.auth.*;
import com.proj.api.exception.database.NonRelationalDatabaseException;
import com.proj.api.exception.database.RelationalDatabaseException;
import com.proj.api.exception.other.InvaildPathException;
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
 * Created by jangitlau on 2017/11/29.
 */
public class UserInfo extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String retStr = "";
        try {
            String sRouteParam = InputStrUtils.getPathInfo(request);
            String sLoginId, sCheckCode, sPhoneNum;
            switch (sRouteParam) {
                case "/":
                case "":
                    sLoginId = request.getParameter("login_id");
                    sCheckCode = request.getParameter("check_code");
                    LoginUserInfo userInfo = new LoginUserInfo(sLoginId, sCheckCode);
                    LoginUserInfoRetGson loginUserInfoRetGson = new LoginUserInfoRetGson(
                            userInfo.getsUserId(),
                            userInfo.getsUserName(),
                            userInfo.getsPhoneNum(),
                            userInfo.getiType(),
                            userInfo.getiAuthority(),
                            userInfo.getiStatus(),
                            userInfo.getsCheckCode()
                    );
                    retStr = JsonUtils.toJson(loginUserInfoRetGson);
                    break;
                case "/phone_num":
                    sLoginId = request.getParameter("login_id");
                    sPhoneNum = request.getParameter("phone_num");
                    sCheckCode = request.getParameter("check_code");
                    //发送验证码
                    break;
                default:
                    throw new InvaildPathException();
            }
        } catch (RelationalDatabaseException e) {
            retStr = e.getRetJson();
        } catch (InvalidOperationException e) {
            retStr = e.getRetJson();
        } catch (UserNotAuthorizedException e) {
            retStr = e.getRetJson();
        } catch (UserDisableException e) {
            retStr = e.getRetJson();
        } catch (NonRelationalDatabaseException e) {
            retStr = e.getRetJson();
        } catch (MalformedJsonException e) {
            retStr = e.getRetJson();
        } catch (InvalidCheckCodeException e) {
            retStr = e.getRetJson();
        } catch (UserNotExistException e) {
            retStr = e.getRetJson();
        } catch (InvalidBackstageOperationException e) {
            retStr = e.getRetJson();
        } catch (InvalidParamsException e) {
            retStr = e.getRetJson();
        } catch (InvaildPathException e) {
            retStr = e.getRetJson();
        }
        response.setHeader("content-type", "text/html;charset=utf-8");
        response.getWriter().print(retStr);
    }

    protected void doPUT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String retStr = "";
        try {
            String recvStr = InputStrUtils.getRecvString(request);
            String sRouteParam = InputStrUtils.getPathInfo(request);
            switch (sRouteParam) {
                case "/password":
                    ModifyPasswordRecvGson modifyInfRecvGson = JsonUtils.fromJson(recvStr, ModifyPasswordRecvGson.class);
                    new ModifyPassword(
                            modifyInfRecvGson.getUser_id(),
                            modifyInfRecvGson.getNew_password(),
                            modifyInfRecvGson.getCheck_code()
                    );
                    retStr = JsonUtils.toJson(new ModifyPasswordRetGson());
                    break;
                case "/phone_num":
                    //修改手机号
                    break;
                default:
                    throw new InvaildPathException();
            }
        } catch (InvalidParamsException e) {
            retStr = e.getRetJson();
        } catch (NonRelationalDatabaseException e) {
            retStr = e.getRetJson();
        } catch (AESDecryptException e) {
            retStr = e.getRetJson();
        } catch (UserNotExistException e) {
            retStr = e.getRetJson();
        } catch (UserDisableException e) {
            retStr = e.getRetJson();
        } catch (RelationalDatabaseException e) {
            retStr = e.getRetJson();
        } catch (InvalidCheckCodeException e) {
            retStr = e.getRetJson();
        } catch (MalformedJsonException e) {
            retStr = e.getRetJson();
        } catch (UserNotAuthorizedException e) {
            retStr = e.getRetJson();
        } catch (InvaildPathException e) {
            retStr = e.getRetJson();
        }
        response.setHeader("content-type", "text/html;charset=utf-8");
        response.getWriter().print(retStr);
    }
}
