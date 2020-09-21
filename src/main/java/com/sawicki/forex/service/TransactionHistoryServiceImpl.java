package com.sawicki.forex.service;

import com.sawicki.forex.entity.TransactionHistory;
import com.sawicki.forex.repository.TransactionHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;

    public TransactionHistoryServiceImpl(TransactionHistoryRepository transactionHistoryRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    @Override
    public List<TransactionHistory> getHistory() {
        return transactionHistoryRepository.findAll();
    }
}
