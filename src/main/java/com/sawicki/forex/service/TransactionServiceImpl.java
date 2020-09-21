package com.sawicki.forex.service;

import com.sawicki.forex.entity.TransactionHistory;
import com.sawicki.forex.repository.CurrencyPairRepository;
import com.sawicki.forex.repository.TransactionHistoryRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class TransactionServiceImpl implements TransactionService{

    private AccountService accountService;
    private TransactionHistoryRepository transactionHistoryRepository;
    private CurrencyPairRepository currencyPairRepository;

    public TransactionServiceImpl(AccountService accountService, TransactionHistoryRepository transactionHistoryRepository, CurrencyPairRepository currencyPairRepository) {
        this.accountService = accountService;
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.currencyPairRepository = currencyPairRepository;
    }

    @Override
    public TransactionHistory buyAsset(String pairName, double volume) {
        double buyPrice = getBuyPrice(pairName);
        String[] currencies = pairName.split("/");
        try {
            accountService.changeAccountBalance(volume,buyPrice,"ADD", pairName);
            return createTransactionHistoryRecord(volume, buyPrice, volume*buyPrice, currencies[1], "BUY");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public TransactionHistory sellAsset(String pairName, double volume) {
        double sellPrice = getSellPrice("USD/PLN");
        String[] currencies = pairName.split("/");
        try {
            accountService.changeAccountBalance(volume,sellPrice,"SUBSTRACT", pairName);
            return createTransactionHistoryRecord(volume, sellPrice, volume*sellPrice, currencies[1], "SELL");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private TransactionHistory createTransactionHistoryRecord(double volume, double price, double value, String currency, String operation) {
        TransactionHistory newTransaction = new TransactionHistory();
        newTransaction.setAction(operation);
        newTransaction.setPrice(price);
        newTransaction.setPairName("USD/PLN");
        newTransaction.setVolume(volume);
        newTransaction.setValue(value);
        newTransaction.setBalance(accountService.getBalance("PLN"));
        newTransaction.setDate(new Timestamp(System.currentTimeMillis()));
        transactionHistoryRepository.save(newTransaction);
        return newTransaction;
    }

    private double getBuyPrice(String assetName) {
        return currencyPairRepository.findFirstByNameOrderByDateDesc(assetName).get().getBuyValue();
    }

    private double getSellPrice(String assetName) {
        return currencyPairRepository.findFirstByNameOrderByDateDesc(assetName).get().getSellValue();
    }
}
