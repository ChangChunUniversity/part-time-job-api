package com.proj.api.auth.servlet;

import com.proj.api.auth.controller.InfomationDetailsInfo;
import com.proj.api.auth.gson.GetDetailsRetGson;
import com.proj.api.exception.auth.*;
import com.proj.api.exception.database.NonRelationalDatabaseException;
import com.proj.api.exception.database.RelationalDatabaseException;
import com.proj.api.exception.other.InvalidCheckCodeException;
import com.proj.api.exception.utils.MalformedJsonException;
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
public class doGetDetails extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String retStr = "";
        try {
            InfomationDetailsInfo infomationDetailsInfo = new InfomationDetailsInfo(
                    request.getParameter("auth_id"),
                    request.getParameter("user_id"),
                    request.getParameter("check_code")
            );
            GetDetailsRetGson getDetailsRetGson = new GetDetailsRetGson(
                    infomationDetailsInfo.getsUserId(),
                    infomationDetailsInfo.getsUserName(),
                    infomationDetailsInfo.getsPhoneNum(),
                    infomationDetailsInfo.getiType(),
                    infomationDetailsInfo.getiAuthority(),
                    infomationDetailsInfo.getiStatus(),
                    infomationDetailsInfo.getsCheckCode()
            );
            retStr = JsonUtils.toJson(getDetailsRetGson);
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
        }
        response.setHeader("content-type", "text/html;charset=utf-8");
        response.getWriter().print(retStr);
    }
}
