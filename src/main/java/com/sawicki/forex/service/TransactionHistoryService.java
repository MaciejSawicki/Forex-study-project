package com.sawicki.forex.service;

import com.sawicki.forex.entity.TransactionHistory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface TransactionHistoryService {

    List<TransactionHistory> getHistory();
}
