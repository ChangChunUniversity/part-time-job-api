package com.proj.api.user.controller;
/**
 * author:Jerry
 */
import com.proj.api.database.RelationalDatabase;
import com.proj.api.exception.database.NonRelationalDatabaseException;
import com.proj.api.exception.database.RelationalDatabaseException;
import com.proj.api.exception.user.*;
import com.proj.api.exception.utils.MalformedJsonException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterInformation {
    private boolean bUsername;
    private boolean bPhoneNum;

    public RegisterInformation(String username, String phone_num) throws UserNotAuthorizedException, UserDisableException, NonRelationalDatabaseException, MalformedJsonException, RelationalDatabaseException {
        RelationalDatabase rConn = new RelationalDatabase();
        if (username != null && username != "") {
            ResultSet result = rConn.doQuery("SELECT uuid FROM user_auth WHERE username=?", new String[]{username});
            try {
                if (result.first()) {
                    this.bUsername = true;
                } else {
                    this.bUsername = false;
                }
            } catch (SQLException e) {
                rConn.close();
                throw new RelationalDatabaseException(e);
            }
        }
        if (phone_num != null && phone_num != "") {
            ResultSet result = rConn.doQuery("SELECT uuid FROM user_auth WHERE phone_num=?", new String[]{phone_num});
            try {
                if (result.first()) {
                    this.bPhoneNum = true;
                } else {
                    this.bPhoneNum = false;
                }
            } catch (SQLException e) {
                rConn.close();
                throw new RelationalDatabaseException(e);
            }
        }
        rConn.close();
    }

    public boolean isbUsername() {
        return bUsername;
    }

    public boolean isbPhoneNum() {
        return bPhoneNum;
    }
}
