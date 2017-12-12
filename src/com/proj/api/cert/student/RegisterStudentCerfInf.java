package com.proj.api.cert.student;

import com.proj.api.database.RelationalDatabase;
import com.proj.api.exception.auth.InvalidBackstageOperationException;
import com.proj.api.exception.auth.InvalidOperationException;
import com.proj.api.exception.auth.UserDisableException;
import com.proj.api.exception.auth.UserNotAuthorizedException;
import com.proj.api.exception.cert.InvalidCertTypeException;
import com.proj.api.exception.database.NonRelationalDatabaseException;
import com.proj.api.exception.database.RelationalDatabaseException;
import com.proj.api.exception.utils.MalformedJsonException;
import com.proj.api.utils.AuthorizationUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jangitlau on 2017/12/12.
 */
public class RegisterStudentCerfInf {
    public RegisterStudentCerfInf(String login_id, int cert_status, String co_name, String co_nickname, int industry_type, int co_type, String org_code, String biz_code, String org_pic, String biz_pic, int co_scale, String co_desc, String contact_person, String contact_phone, String contact_mail, String co_site, String[] co_pic_array, int co_city, String co_addr, String check_code) throws UserNotAuthorizedException, UserDisableException, NonRelationalDatabaseException, MalformedJsonException, InvalidOperationException, InvalidBackstageOperationException, RelationalDatabaseException, InvalidCertTypeException {
        
    }
}
