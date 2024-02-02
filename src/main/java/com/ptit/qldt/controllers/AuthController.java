package com.ptit.qldt.controllers;

import com.ptit.qldt.dtos.RegistrationDto;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
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
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("acc");
        return "redirect:/login";
    }
}
