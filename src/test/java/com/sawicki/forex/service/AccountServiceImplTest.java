package com.sawicki.forex.service;

import com.sawicki.forex.entity.Account;
import com.sawicki.forex.entity.Asset;
import com.sawicki.forex.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {

    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    Optional<Account> accountOptional;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        accountService = new AccountServiceImpl(accountRepository);

        List<Asset> assets = new ArrayList<>();
        Asset asset1 = new Asset();
        asset1.setCurrencyCode("USD");
        asset1.setBalance(100.);
        Asset asset2 = new Asset();
        asset2.setCurrencyCode("PLN");
        asset2.setBalance(100.);
        assets.add(asset1);
        assets.add(asset2);
        Account account = new Account();
        account.setAssets(assets);
        accountOptional = Optional.of(account);
    }

    @Test
    public void changeAccountBalanceBuyAssetTest() throws Exception {
        //when
        when(accountRepository.findById(anyLong())).thenReturn(accountOptional);

        accountService.changeAccountBalance(20.,3.4,"ADD", "USD/PLN");
        verify(accountRepository, times(1)).save(any());
        verify(accountRepository, times(2)).findById(anyLong());

    }

    @Test(expected = Exception.class)
    public void changeAccountBalanceBuyAssetWithNotEnoughMoneyTest() throws Exception {
        //when
        when(accountRepository.findById(anyLong())).thenReturn(accountOptional);

        accountService.changeAccountBalance(200.,3.4,"SUBSTRACT", "USD/PLN");
        verify(accountRepository, times(1)).save(any());
        verify(accountRepository, times(2)).findById(anyLong());

    }

    @Test
    public void changeAccountBalanceSellAssetTest() throws Exception {
        //when
        when(accountRepository.findById(anyLong())).thenReturn(accountOptional);

        accountService.changeAccountBalance(20.,3.4,"SELL", "USD/PLN");
        verify(accountRepository, times(1)).save(any());
        verify(accountRepository, times(2)).findById(anyLong());

    }

    @Test(expected = Exception.class)
    public void changeAccountBalanceSellAssetWithNotEnoughMoneyTest() throws Exception {
        //when
        when(accountRepository.findById(anyLong())).thenReturn(accountOptional);

        accountService.changeAccountBalance(200.,3.4,"SELL", "USD/PLN");
        verify(accountRepository, times(1)).save(any());
        verify(accountRepository, times(2)).findById(anyLong());

    }

    @Test
    public void getBalanceTest() throws Exception {
        //when
        when(accountRepository.findById(anyLong())).thenReturn(accountOptional);

        double testBalance = accountService.getBalance("PLN");
        assertNotNull(testBalance);
        verify(accountRepository, times(1)).findById(anyLong());
        verify(accountRepository, never()).findAll();
    }

    @Test
    public void getUserInfoTest() throws Exception {
        when(accountRepository.findById(anyLong())).thenReturn(accountOptional);
        accountService.getUserInfo();

        verify(accountRepository, times(1)).findById(anyLong());
    }
}