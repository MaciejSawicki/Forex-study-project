package com.sawicki.forex.service;

import com.sawicki.forex.entity.CurrencyPair;
import com.sawicki.forex.repository.CurrencyPairRepository;
import com.sawicki.forex.repository.TransactionHistoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TransactionServiceImplTest {

    TransactionService transactionService;

    @Mock
    private AccountService accountService;
    @Mock
    private TransactionHistoryRepository transactionHistoryRepository;
    @Mock
    private CurrencyPairRepository currencyPairRepository;

    Optional<CurrencyPair> currencyPair;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        transactionService = new TransactionServiceImpl(accountService,transactionHistoryRepository,currencyPairRepository);

        CurrencyPair testCurrencyPair = new CurrencyPair();
        testCurrencyPair.setBuyValue(1.);
        testCurrencyPair.setSellValue(1.);
        testCurrencyPair.setName("USD/PLN");
        currencyPair = Optional.of(testCurrencyPair);
    }

    @Test
    public void buyAssetTest() throws Exception {
        when(currencyPairRepository.findFirstByNameOrderByDateDesc(anyString())).thenReturn(currencyPair);
        transactionService.buyAsset("USD/PLN", 10);
        verify(accountService, times(1)).changeAccountBalance(anyDouble(),anyDouble(),anyString(),anyString());
        verify(transactionHistoryRepository, times(1)).save(any());
        verify(currencyPairRepository, times(1)).findFirstByNameOrderByDateDesc(anyString());
    }

    @Test
    public void buyAssetTrowsExceptionTest() throws Exception {
        when(currencyPairRepository.findFirstByNameOrderByDateDesc(anyString())).thenReturn(currencyPair);
        doThrow(Exception.class).when(accountService).changeAccountBalance(anyDouble(),anyDouble(),anyString(),anyString());

        assertNull(transactionService.buyAsset("USD/PLN", 10));
        verify(accountService, times(1)).changeAccountBalance(anyDouble(),anyDouble(),anyString(),anyString());
        verify(currencyPairRepository, times(1)).findFirstByNameOrderByDateDesc(anyString());
    }
    @Test
    public void SellAssetTest() throws Exception {
        when(currencyPairRepository.findFirstByNameOrderByDateDesc(anyString())).thenReturn(currencyPair);
        transactionService.sellAsset("USD/PLN", 10);
        verify(accountService, times(1)).changeAccountBalance(anyDouble(),anyDouble(),anyString(),anyString());
        verify(transactionHistoryRepository, times(1)).save(any());
        verify(currencyPairRepository, times(1)).findFirstByNameOrderByDateDesc(anyString());
    }

    @Test
    public void SellAssetTrowsExceptionTest() throws Exception {
        when(currencyPairRepository.findFirstByNameOrderByDateDesc(anyString())).thenReturn(currencyPair);
        doThrow(Exception.class).when(accountService).changeAccountBalance(anyDouble(),anyDouble(),anyString(),anyString());

        assertNull(transactionService.sellAsset("USD/PLN", 10));
        verify(accountService, times(1)).changeAccountBalance(anyDouble(),anyDouble(),anyString(),anyString());
        verify(currencyPairRepository, times(1)).findFirstByNameOrderByDateDesc(anyString());
    }

}