package com.sawicki.forex.controller;

import com.sawicki.forex.service.AccountService;
import com.sawicki.forex.service.TransactionHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TradeController {

    private final TransactionHistoryService transactionHistoryService;
    private final AccountService accountService;

    public TradeController(TransactionHistoryService transactionHistoryService, AccountService accountService) {
        this.transactionHistoryService = transactionHistoryService;
        this.accountService = accountService;
    }

    @RequestMapping({"trade", "trade.html"})
    public String trade(Model model) {
        model.addAttribute("history", transactionHistoryService.getHistory());
        model.addAttribute("balance", accountService.getBalance("PLN"));
        return "forex";
    }
}
