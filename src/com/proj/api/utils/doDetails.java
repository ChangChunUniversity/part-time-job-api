package com.proj.api.utils;
/**
 * author:Jerry
 */
import com.proj.api.exception.auth.*;
import com.proj.api.exception.database.NonRelationalDatabaseException;
import com.proj.api.exception.database.RelationalDatabaseException;
import com.proj.api.exception.other.InvalidCheckCodeException;
import com.proj.api.exception.utils.MalformedJsonException;
import com.proj.api.auth.controller.InfomationDetailsInfo;
import com.proj.api.auth.gson.InformationCheckRetGson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class doDetails extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String retStr = "";
        try {
            String auth_id = request.getParameter("auth_id");
            String user_id = request.getParameter("user_id");
            String check_code = request.getParameter("check_code");
            InfomationDetailsInfo infomationDetailsInfo = new InfomationDetailsInfo(auth_id, user_id, check_code);
            InformationCheckRetGson informationCheckRetGson = new InformationCheckRetGson(
                    infomationDetailsInfo.getsUserId()
                    , infomationDetailsInfo.getsUserName()
                    , infomationDetailsInfo.getsPhoneNum()
                    , infomationDetailsInfo.getiType()
                    , infomationDetailsInfo.getiAuthority()
                    , infomationDetailsInfo.getiStatus()
                    , infomationDetailsInfo.getsCheckCode()
            );
            retStr = JsonUtils.toJson(informationCheckRetGson);
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