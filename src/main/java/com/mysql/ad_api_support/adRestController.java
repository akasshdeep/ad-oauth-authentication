package com.mysql.ad_api_support;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class adRestController {
    ad auth = new auth_services();

    @GetMapping("/Signin")
    @ResponseBody
    public String dbconnectSignin(@RequestParam String urname, @RequestParam String pwd){
        auth.db_connect();
        return auth.db_validation(urname, pwd);
    }
    @PostMapping("/signup")
    @ResponseBody
    public String dbconnectSignup(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String mobile,
            @RequestParam String userName,
            @RequestParam String pwd,
            @RequestParam String confirmPwd
    ){
        auth.db_connect();
        return auth.signup(firstName, lastName, email, mobile, userName, pwd, confirmPwd);
    }
    @GetMapping("/callback")
    public String callback(@RequestParam String code, @RequestParam String state , HttpSession session) throws SQLException {
        String username = auth.githublogin(code, state);
        session.setAttribute("username", username);
        return "redirect:/Welcome";
    }
    @GetMapping("/Welcome")
    public String Welcome(HttpSession session, Model model){
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        return "Welcome1" ;
    }

}
