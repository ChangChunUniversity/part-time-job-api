package com.proj.api.user.controller;

import com.proj.api.database.RelationalDatabase;
import com.proj.api.exception.database.NonRelationalDatabaseException;
import com.proj.api.exception.database.RelationalDatabaseException;
import com.proj.api.exception.user.*;
import com.proj.api.exception.utils.MalformedJsonException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterInformation {
    private boolean bUsername = false;
    private boolean bPhoneNum = false;

    public RegisterInformation(String username,String phone_num) throws UserNotAuthorizedException, UserDisableException, NonRelationalDatabaseException, MalformedJsonException, RelationalDatabaseException {
        if (username != null && username !=""){
            RelationalDatabase rConn = new RelationalDatabase();
            ResultSet result = rConn.doQuery("SELECT uuid FROM user_auth WHERE username=?", new String[]{username});
            try {
                if (result.first()) {
                    bUsername = true;
                }
            }catch (SQLException e) {
                rConn.close();
                throw new RelationalDatabaseException(e);
            }
        }
        if (phone_num != null && phone_num !=""){
            RelationalDatabase rConn = new RelationalDatabase();
            ResultSet result = rConn.doQuery("SELECT uuid FROM user_auth WHERE phone_num=?", new String[]{phone_num});
            try {
                if (result.first()) {
                    bPhoneNum = true;
                }
            }catch (SQLException e) {
                rConn.close();
                throw new RelationalDatabaseException(e);
            }
        }
    }

    public boolean isbUsername() {
        return bUsername;
    }

    public boolean isbPhoneNum() {
        return bPhoneNum;
    }
}
