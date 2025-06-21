package com.mysql.ad_api_support;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.*;
import java.net.*;

class auth_services implements ad {
    public Connection conn;
    public String db_connect() {
        String url = "jdbc:mysql://localhost:3306/ad"; // Replace with the your DB URL and Schema here ad is my Schema
        String user = "root"; //DB Username
        String password = "Rest@123"; // DB Password
        try {
            conn = DriverManager.getConnection(url, user, password);
            return "Database connection established";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Failed to connect to database: " + e.getMessage();

        }
    }

    public String db_validation(String urname, String pwd) {

        try {
            Statement smt = conn.createStatement();
            ResultSet rs = smt.executeQuery("SELECT password FROM userdata WHERE User_Name = '" + urname + "'");
            if (rs.next()) {
                if (rs.getString("Password").equals(pwd)) {
                    return "Password verified successfully";
                } else {
                    System.out.println("Password not verified");
                    return "Password not Verified";
                }
            } else {
                System.out.println("User Not Found in DB");
                return "User Not Found in DB";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String signup(String firstName, String lastName, String email, String mobile, String userName, String pwd, String confirmPwd) {
        Statement smt;
        String uname = "";
        String LastName = lastName;
        String confirmPassword = confirmPwd;

        try {
            smt = conn.createStatement();

            while (true) {

                try {
                    ResultSet rs = smt.executeQuery("SELECT User_Name FROM userdata WHERE USER_Name = '" + userName + "'"); // Adjust this query as per your SQL Table
                    if (rs.next()) {
                        System.out.println("User Name already exists");
                        return "User Name already exists";
                    } else {
                        System.out.println("User Name Available");
                        uname = userName;
                        break;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            while (true) {
                if (confirmPassword.equals(pwd)) {

                    String insertQuery = "INSERT INTO userdata (First_Name,Last_Name,User_Name,Password,email,mob)" + "VALUES ('" + firstName + "', '" + LastName + "', '" + uname + "' , '" + pwd + "' , '" + email + "' , '" + mobile + "')"; // Adjust this query as per your SQL Table
                    smt.executeUpdate(insertQuery);

                    System.out.println("User Created Successfully");
                    return "User Created Successfully";
                } else {
                    System.out.println("Password Not Matched!! Please try again");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String githublogin(String code, String state) {
        db_connect();
        String jsonbody = String.format("""
                {
                    "client_id": "XXXXXXXXXXXXXXXXXXXXXXXX", 
                    "client_secret": "YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY",
                    "code": "%s"
                }
                """, code); //Replace client_id and client_secret with your App Value and Also change it in index.html
        String uri = "https://github.com/login/oauth/access_token";
        String uri1 = "https://api.github.com/user";
        String username = " ";
        String body;
        String token = " ";
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonbody)).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            body = response.body();
            String[] pairs = body.split("&");
            Map<String, String> result = new HashMap<>();

            for (String pair1 : pairs) {
                String[] keyValue = pair1.split("=");
                result.put(keyValue[0], keyValue[1]);
            }
            token = result.get("access_token");
        } catch (Exception e) {}
        try {
            HttpRequest request1 = HttpRequest.newBuilder().uri(URI.create(uri1)).header("Authorization", "Bearer " + token).GET().build();
            HttpClient client1 = HttpClient.newHttpClient();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String body1 = response1.body();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> result1 = mapper.readValue(body1, Map.class);
            username = result1.get("login");
        } catch (Exception e) {}
        try {
            Statement smt = conn.createStatement();
            String insertQuery = "INSERT INTO userdata (User_Name)" + "VALUES ('" + username + "')";  // Adjust this query as per your SQL Table
            String insertQuery1 = "SELECT User_Name FROM userdata WHERE User_Name = '" + username + "'";  // Adjust this query as per your SQL Table
            ResultSet rs = smt.executeQuery(insertQuery1);
            if (rs.next()) {
                System.out.println("User Name already exists");
            }
            else {
                try {
                    smt.executeUpdate(insertQuery);
                } catch (SQLException e) {}
            }
        } catch (SQLException e) {
        }
        return username;
    }
}



