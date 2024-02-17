package com.ptit.qldt.controllers;

import com.ptit.qldt.dtos.RegistrationDto;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.services.EmailService;
import com.ptit.qldt.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Random;

@Controller
public class AuthController {
    private UserService userService;
    private EmailService emailService;

    public AuthController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
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

    @PostMapping("/login/check")
    public String login(HttpSession session, @ModelAttribute("user")RegistrationDto acc) {
        String username = acc.getUsername();
        String password = acc.getPassword();

        Account user = userService.findFirstByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("acc", user);
            return "redirect:/courses";
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

            session.setAttribute("acc", user);
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
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("acc");
        return "redirect:/login";
    }
}
