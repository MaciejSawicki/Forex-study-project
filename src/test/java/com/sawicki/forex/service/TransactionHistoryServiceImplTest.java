package com.sawicki.forex.service;

import com.sawicki.forex.repository.TransactionHistoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TransactionHistoryServiceImplTest {
    private TransactionHistoryService transactionHistoryService;

    @Mock
    TransactionHistoryRepository transactionHistoryRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        transactionHistoryService = new TransactionHistoryServiceImpl(transactionHistoryRepository);
    }

    @Test
    public void getHistoryTest() throws Exception {
        transactionHistoryService.getHistory();

        verify(transactionHistoryRepository,times(1)).findAll();
    }
}