package com.sawicki.forex.service;

import com.sawicki.forex.entity.TransactionHistory;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {

    TransactionHistory buyAsset(String pairName, double volume);

    TransactionHistory sellAsset(String pairName, double volume);


}
