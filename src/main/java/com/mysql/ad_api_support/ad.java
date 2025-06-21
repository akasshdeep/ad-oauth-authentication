package com.mysql.ad_api_support;

import java.sql.SQLException;

public interface ad {
    String db_connect();
    String db_validation(String urname, String pwd);
    String signup(String uname, String firstName, String lastName, String email, String password, String mobile, String confirmPassword);
    String githublogin(String code, String state) throws SQLException;
}
