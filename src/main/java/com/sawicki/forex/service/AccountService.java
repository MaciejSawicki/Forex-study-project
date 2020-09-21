package com.sawicki.forex.service;

import com.sawicki.forex.entity.Account;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    void changeAccountBalance(double volume, double price, String operation, String currency) throws Exception;

    double getBalance(String currency);

    Account getUserInfo();
}
