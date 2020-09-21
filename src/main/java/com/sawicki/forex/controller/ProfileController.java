package com.sawicki.forex.controller;

import com.sawicki.forex.service.AccountService;
import com.sawicki.forex.service.TransactionHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProfileController {

    private final TransactionHistoryService transactionHistoryService;
    private final AccountService accountService;

    public ProfileController(TransactionHistoryService transactionHistoryService, AccountService accountService) {
        this.transactionHistoryService = transactionHistoryService;
        this.accountService = accountService;
    }


    @RequestMapping({"profile", "profile.html"})
    public String profilePage(Model model) {
        model.addAttribute("history", transactionHistoryService.getHistory());
        model.addAttribute("account", accountService.getUserInfo());
        model.addAttribute("balance", accountService.getBalance("PLN"));
        return "profile";
    }
}
