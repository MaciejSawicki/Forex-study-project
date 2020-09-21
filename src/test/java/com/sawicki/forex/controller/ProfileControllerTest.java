package com.sawicki.forex.controller;

import com.sawicki.forex.entity.Account;
import com.sawicki.forex.entity.TransactionHistory;
import com.sawicki.forex.service.AccountService;
import com.sawicki.forex.service.TransactionHistoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ProfileControllerTest {
    ProfileController controller;

    @Mock
    TransactionHistoryService transactionHistoryService;
    @Mock
    AccountService accountService;

    @Mock
    Model model;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new ProfileController(transactionHistoryService, accountService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getProfilePage() throws Exception {
        //given
        Account testAccount = new Account();
        List<TransactionHistory> transactionHistoryList = new ArrayList<>();
        double testBalance = 20.0;
        //when
        when(accountService.getUserInfo()).thenReturn(testAccount);
        when(accountService.getBalance(any())).thenReturn(testBalance);
        when(transactionHistoryService.getHistory()).thenReturn(transactionHistoryList);
        //then
        mockMvc.perform(get("/profile.html"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("history"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("account"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("balance"));
        verify(accountService, times(1)).getUserInfo();
        verify(accountService, times(1)).getBalance("PLN");
        verify(transactionHistoryService, times(1)).getHistory();
    }
}