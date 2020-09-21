package com.sawicki.forex.controller;

import com.sawicki.forex.service.AccountService;
import com.sawicki.forex.service.TransactionHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final AccountService accountService;

    public IndexController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping({"", "/", "index", "index.html"})
    public String index(Model model) {
        model.addAttribute("balance", accountService.getBalance("PLN"));
        return "index";
    }

}
