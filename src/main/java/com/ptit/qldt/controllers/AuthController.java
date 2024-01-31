package com.ptit.qldt.controllers;

import com.ptit.qldt.services.AccountService;
import com.ptit.qldt.services.impl.AccountServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    private AccountService accountService;

    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
