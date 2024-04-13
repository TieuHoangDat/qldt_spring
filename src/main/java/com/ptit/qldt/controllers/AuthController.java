package com.ptit.qldt.controllers;

import com.ptit.qldt.dtos.NotificationDto;
import com.ptit.qldt.dtos.RegistrationDto;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.services.EmailService;
import com.ptit.qldt.services.NotificationService;
import com.ptit.qldt.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Random;

@Controller
public class AuthController {
    private UserService userService;
    private EmailService emailService;
    private NotificationService notificationService;

    public AuthController(UserService userService, EmailService emailService,NotificationService notificationService) {
        this.userService = userService;
        this.emailService = emailService;
        this.notificationService = notificationService;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user")RegistrationDto user,
                           BindingResult result, Model model) {
        Account existingUserEmail = userService.findByEmail(user.getEmail());
        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
            return "redirect:/register?fail";
        }
        Account existingUserUsername = userService.findByUsername(user.getUsername());
        if(existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()) {
            return "redirect:/register?fail";
        }
        if(result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/courses?success";
    }

//    @GetMapping("/login")
//    public String loginPage() {
//        return "login";
//    }
    @GetMapping("/login")
    public String loginPage(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "login";
    }

    @GetMapping("/information")
    public String informationPage(Model model) {
        model.addAttribute("infoactive","active");
        return "information";
    }

    @PostMapping("/login/check")
    public String login(HttpSession session, @ModelAttribute("user")RegistrationDto acc) {
        String username = acc.getUsername();
        String password = acc.getPassword();
        List<NotificationDto> allNotification = notificationService.findAllNotification();
        for(NotificationDto x : allNotification){
            System.out.println(x.getTitle());
        }

        Account user = userService.findFirstByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("acc", user);
            session.setAttribute("allNotification",allNotification);
            return "redirect:/home";
        } else {

            return "redirect:/login?fail";
        }
    }
    @GetMapping("/forget-password")
    public String forgetPasswordPage(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "forget_password";
    }

    public static String generateOTP() {
        // Mảng các ký tự cho mã OTP
        String chars = "0123456789";
        StringBuilder otp = new StringBuilder();

        // Sinh mã OTP ngẫu nhiên
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            otp.append(chars.charAt(random.nextInt(chars.length())));
        }

        return otp.toString();
    }

    @PostMapping("/send-otp")
    public String sendOtp(HttpSession session, @ModelAttribute("user")RegistrationDto acc) {
        String username = acc.getUsername();
        String email = acc.getEmail();

        Account user = userService.findFirstByUsername(username);

        if (user != null && user.getEmail().equals(email)) {
            // tao, luu, gui otp
            String otp = generateOTP();
            userService.updateOtp(user.getAccount_id(), otp);
            emailService.sendSimpleMessage(user.getEmail(), "Reset password", otp);

            return "redirect:/reset-password";
        } else {
            return "redirect:/forget-password?fail";
        }
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "reset_password";
    }
    @PostMapping("/reset-password/check")
    public String reset(HttpSession session, @ModelAttribute("user")RegistrationDto acc) {
        String otp = acc.getOtp();
        String username = acc.getUsername();
        String newPassword = acc.getPassword();

        Account user = userService.findFirstByUsername(username);

        if (otp!=null && user != null && otp.equals(user.getOtp())) {
            // update password
            userService.updatePassword(user.getAccount_id(), newPassword);

            session.setAttribute("acc", user);
            return "redirect:/courses";
        } else {
            return "redirect:/reset-password?fail";
        }
    }
    @PostMapping("/updateUserIdTelegram")
    public String update(HttpSession session,
                         @RequestParam(value = "userIdTelegram") String userIdTelegram,
                         @RequestParam(value = "accountId") int accountId,
                         @RequestParam(value = "username") String username){
//        Object acc= session.getAttribute("acc");
        userService.updateUserIdTelegram(accountId,userIdTelegram);
        Account acc2 = userService.findByUsername(username);
        System.out.println(acc2.getUser_id_telegram());
        session.removeAttribute("acc");
        session.setAttribute("acc",acc2);
        return "redirect:/information";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("acc");
        session.removeAttribute("allNotification");
        return "redirect:/login";
    }

}
