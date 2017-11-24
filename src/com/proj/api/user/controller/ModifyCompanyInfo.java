package com.proj.api.user.controller;
/**
 * author:Jerry
 */
import com.proj.api.database.RelationalDatabase;
import com.proj.api.exception.database.NonRelationalDatabaseException;
import com.proj.api.exception.database.RelationalDatabaseException;
import com.proj.api.exception.other.InvalidParamsException;
import com.proj.api.exception.user.*;
import com.proj.api.exception.utils.MalformedJsonException;
import com.proj.api.utils.AuthorizationUtils;
import com.proj.api.utils.RandomUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModifyCompanyInfo {

    public ModifyCompanyInfo(String action, String auth_id, String user_id, int cert_status, String co_name, String co_nickname
    , int industry_type, int co_type, String org_code, String biz_code, String org_pic, String biz_pic, int co_scale
    , String co_desc, String contact_person, String contact_phone, String contact_mail, String co_site, String[] co_pic
    , int co_city, String co_addr, String check_code) throws UserNotAuthorizedException, UserDisableException, NonRelationalDatabaseException, MalformedJsonException, InvalidOperationException, InvalidParamsException, RelationalDatabaseException, SQLException, UserNotExistException {
        AuthorizationUtils authorizationUtils = new AuthorizationUtils(auth_id);
        if (authorizationUtils.getiType() != 3||authorizationUtils.getiAuthority()<5) {
            throw new InvalidOperationException();
        }
        switch (action) {
            case "add":
                addCompany(user_id,cert_status,co_name,co_nickname,industry_type,co_type,org_code,
                        biz_code,org_pic,biz_pic,co_scale,co_desc,contact_person,contact_phone,
                        contact_mail,co_site,co_pic,co_city,co_addr,check_code);
                break;
            case "del":
                delCompany(user_id);
                break;
            case "mod":
                modCompany(user_id,cert_status,co_name,co_nickname,industry_type,co_type,org_code,
                        biz_code,org_pic,biz_pic,co_scale,co_desc,contact_person,contact_phone,
                        contact_mail,co_site,co_pic,co_city,co_addr,check_code);
                break;
            default:
                throw new InvalidParamsException();
        }
    }
    private void addCompany( String user_id, int cert_status, String co_name, String co_nickname
            , int industry_type, int co_type, String org_code, String biz_code, String org_pic, String biz_pic, int co_scale
            , String co_desc, String contact_person, String contact_phone, String contact_mail, String co_site, String[] co_pic
            , int co_city, String co_addr, String check_code) throws RelationalDatabaseException {
            RelationalDatabase rConn = new RelationalDatabase();
        String uuid = RandomUtils.getUUID();
        rConn.doSQL("insert INTO company_inf (uuid,status,co_name,co_nickname," +
                "industry_type,co_type,org_code,biz_code,org_pic,biz_pic,co_scale,co_desc," +
                "contact_person,contact_phone,contact_mail,co_site,co_pic,co_city,co_addr,check_code) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ", new Object[]{uuid,cert_status,co_name,co_nickname,
                industry_type,co_type,org_code,biz_code,org_pic,biz_pic,co_scale,co_desc,contact_person,contact_phone,
                contact_mail,co_site,co_pic,co_city,co_addr,check_code});
    }

    private void delCompany(String user_id) throws InvalidParamsException, RelationalDatabaseException {
        RelationalDatabase rConn = new RelationalDatabase();
        if (user_id == null) {
            throw new InvalidParamsException();
        }
        rConn.doSQL("DELETE FROM company_inf WHERE uuid = ?", new Object[]{user_id});
    }

    private void modCompany(String user_id, int cert_status, String co_name, String co_nickname
            , int industry_type, int co_type, String org_code, String biz_code, String org_pic, String biz_pic, int co_scale
            , String co_desc, String contact_person, String contact_phone, String contact_mail, String co_site, String[] co_pic
            , int co_city, String co_addr, String check_code) throws RelationalDatabaseException, SQLException, UserNotExistException {
        RelationalDatabase rConn = new RelationalDatabase();
        ResultSet result = rConn.doQuery("SELECT uuid FROM company_inf WHERE co_name=?", new String[]{co_name});
        try {
        if (!result.first()) {
            rConn.close();
            throw new UserNotExistException();
        }
        if (co_name != "") {
            rConn.doSQL("UPDATE company_inf SET co_name = ? WHERE uuid=?", new Object[]{co_name, user_id});
        }
        if (co_nickname != "") {
            rConn.doSQL("UPDATE company_inf SET co_nickname = ? WHERE uuid=?", new Object[]{co_nickname, user_id});
        }
        if (industry_type <= 0) {
            rConn.doSQL("UPDATE company_inf SET industry_type = ? WHERE uuid=?", new Object[]{industry_type, user_id});
        }
        if (co_type <= 0) {
            rConn.doSQL("UPDATE company_inf SET co_type = ? WHERE uuid=?", new Object[]{co_type, user_id});
        }
        if (org_code != "") {
            rConn.doSQL("UPDATE company_inf SET org_code = ? WHERE uuid=?", new Object[]{org_code, user_id});
        }
        if (biz_code != "") {
            rConn.doSQL("UPDATE company_inf SET biz_code = ? WHERE uuid=?", new Object[]{biz_code, user_id});
        }
        if (org_pic != "") {
            rConn.doSQL("UPDATE company_inf SET org_pic = ? WHERE uuid=?", new Object[]{org_pic, user_id});
        }
        if (biz_pic != "") {
            rConn.doSQL("UPDATE company_inf SET biz_pic = ? WHERE uuid=?", new Object[]{biz_pic, user_id});
        }
        if (co_scale <= 0) {
            rConn.doSQL("UPDATE company_inf SET co_scale = ? WHERE uuid=?", new Object[]{co_scale, user_id});
        }
        if (co_desc != "") {
            rConn.doSQL("UPDATE company_inf SET co_desc = ? WHERE uuid=?", new Object[]{co_desc, user_id});
        }
        if (contact_person != "") {
            rConn.doSQL("UPDATE company_inf SET contact_person = ? WHERE uuid=?", new Object[]{contact_person, user_id});
        }
        if (contact_phone != "") {
            rConn.doSQL("UPDATE company_inf SET contact_phone = ? WHERE uuid=?", new Object[]{contact_phone, user_id});
        }
        if (contact_mail != "") {
            rConn.doSQL("UPDATE company_inf SET contact_mail = ? WHERE uuid=?", new Object[]{contact_mail, user_id});
        }
        if (co_site != "") {
            rConn.doSQL("UPDATE company_inf SET co_site = ? WHERE uuid=?", new Object[]{co_site, user_id});
        }
        if (co_pic.length==0) {
            for (int i=0;i<co_pic.length;i++){
            ///
            }
            rConn.doSQL("UPDATE company_inf SET co_pic = ? WHERE uuid=?", new Object[]{co_pic, user_id});
        }
        if (co_city <= 0) {
            rConn.doSQL("UPDATE company_inf SET co_city = ? WHERE uuid=?", new Object[]{co_city, user_id});
        }
        if (co_addr != "") {
            rConn.doSQL("UPDATE company_inf SET co_nickname = ? WHERE uuid=?", new Object[]{co_nickname, user_id});
        }

        }catch (SQLException e) {
            rConn.close();
            throw new RelationalDatabaseException(e);
        }
        rConn.close();

    }
}
