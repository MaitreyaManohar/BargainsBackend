package com.example.oopsProject.Mail;

import com.example.oopsProject.Security.PwdGenerator;
import com.example.oopsProject.Security.passwordencoder;
import com.example.oopsProject.UserClass.UserClass;
import com.example.oopsProject.UserClass.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mail")
public class EmailController {
    private EmailService emailService;
    private UserService userService;
    @Autowired
    public EmailController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @PostMapping("/sendmail")
    public String sendMail(@RequestBody EmailDetails details)
    {
        String status = emailService.sendSimpleMail(details);
        return status;
    }

    @PostMapping("/resetpwd")
    public String sendNewPwd(@RequestBody ChangePwd changePwd){
        UserClass user = userService.findByEmail(changePwd.getEmail());
        String userEmail = user.getEmail();
        String newPwd = PwdGenerator.randomPwdGenerator();
        String emailStatus = emailService.sendSimpleMail(new EmailDetails(userEmail,"Your new password is:"+ newPwd,"Password Reset"));
        if(emailStatus.equals("Mail Sent Successfully...")){
            userService.changePwd(user.getId(),newPwd);
            return "Password Reset Succesfully";
        }
        return "Error Occured";
    }

    @PostMapping("/changepwd")
    public String changePwd(@RequestBody ChangePwd changePwd){
        UserClass user = userService.findById(changePwd.getUserId());
        if(user.getPassword().equals(passwordencoder.encode(changePwd.getOldPwd()))){
            String userEmail = user.getEmail();
            String emailStatus = emailService.sendSimpleMail(new EmailDetails(userEmail,"Password Changed","Change Of Password"));
            if(emailStatus.equals("Mail Sent Successfully...")){
                userService.changePwd(user.getId(),changePwd.getOldPwd(),changePwd.getNewPwd());
                return "Password Changed";
            }
        }
        return "Could not change password";
    }
}
