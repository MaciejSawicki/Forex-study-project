package com.sawicki.forex.service;

import com.sawicki.forex.entity.Account;
import com.sawicki.forex.entity.Asset;
import com.sawicki.forex.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void changeAccountBalance(double volume, double price, String operation, String pairName) throws Exception {
        Account account = accountRepository.findById(Long.valueOf(1)).get();
        String[] currency = pairName.split("/");
        double value = 0;
        if(operation == "ADD"){
             value+= volume * price;
        } else {
             value-= volume * price;
        }

        if(getBalance(currency[0]) + value > 0) {
            for (Asset a : account.getAssets()){
                if(a.getCurrencyCode().equals(currency[0])) {
                    account.getAssets().get(account.getAssets().indexOf(a)).setBalance(account.getAssets().get(account.getAssets().indexOf(a)).getBalance() + value);
                }
                if(a.getCurrencyCode().equals(currency[1])) {
                    account.getAssets().get(account.getAssets().indexOf(a)).setBalance(account.getAssets().get(account.getAssets().indexOf(a)).getBalance() - value);
                }
            }

            accountRepository.save(account);
        } else {
            throw new Exception("Not enough money!");
        }

    }

    @Override
    public double getBalance(String currency) {
        List<Asset> assets = accountRepository.findById(Long.valueOf(1)).get().getAssets();
        return  assets.stream().filter(c -> c.getCurrencyCode().equals(currency)).findFirst().get().getBalance();
    }

    @Override
    public Account getUserInfo() {
        return accountRepository.findById(Long.valueOf(1)).get();
    }


}
