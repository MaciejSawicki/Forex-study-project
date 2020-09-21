package com.sawicki.forex.bootstrap;

import com.sawicki.forex.entity.Account;
import com.sawicki.forex.entity.Asset;
import com.sawicki.forex.entity.CurrencyPair;
import com.sawicki.forex.repository.AccountRepository;
import com.sawicki.forex.repository.AssetRepository;
import com.sawicki.forex.repository.CurrencyPairRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


import java.sql.Timestamp;
import java.util.*;


@Component
public class BootClass implements ApplicationListener<ContextRefreshedEvent> {

    CurrencyPairRepository currencyPairRepository;
    AccountRepository accountRepository;
    AssetRepository assetRepository;

    public BootClass(CurrencyPairRepository currencyPairRepository, AccountRepository accountRepository, AssetRepository assetRepository) {
        this.currencyPairRepository = currencyPairRepository;
        this.accountRepository = accountRepository;
        this.assetRepository = assetRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        Account account = new Account();
        account.setFirstName("Jan");
        account.setLastName("Szczepanik");
        account.setAddress("ul. Zachodnia 59/6 Łódź");
        //Account.builder().id(Long.valueOf(1)).lastName("Szczepanik").firstName("Jan").address("ul. Zachodnia 59/6 Łódź").build();
        account.addAsset(new Asset(account, "United States Dollar","USD", 10.));
        account.addAsset(new Asset(account,"Polski Złoty", "PLN", 1000.));
        accountRepository.save(account);


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR,-1);
        Timestamp date = new Timestamp(calendar.getTimeInMillis());
        Random random = new Random();
        double buyValue = 3.40;
        double sellValue = 3.40;
        Timestamp dateNow = new Timestamp(System.currentTimeMillis());
        while(date.before(dateNow)) {
            CurrencyPair currencyPair = CurrencyPair.builder()
                    .name("USD/PLN")
                    .currency1("USD")
                    .currency2("PLN")
                    .date(date)
                    .sellValue(sellValue)
                    .buyValue(buyValue).build();
            sellValue +=(random.nextDouble() / 20) - 0.025;
            buyValue = sellValue + (random.nextDouble() / 40);
            calendar.add(Calendar.SECOND,30);
            date.setTime(calendar.getTimeInMillis());
            currencyPairRepository.save(currencyPair);
        }
        System.out.println("DONE!!!");
    }
}
