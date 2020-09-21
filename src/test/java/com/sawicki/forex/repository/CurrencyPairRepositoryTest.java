package com.sawicki.forex.repository;

import com.sawicki.forex.entity.CurrencyPair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CurrencyPairRepositoryTest {

    @Autowired
    CurrencyPairRepository currencyPairRepository;

    @Before
    public void setUp() throws Exception {
        CurrencyPair currencyPair = new CurrencyPair();
        currencyPair.setName("USD/PLN");
        currencyPairRepository.save(currencyPair);
    }

    @Test
    public void findByName() throws Exception {
        List<CurrencyPair> currencyPairOptional = currencyPairRepository.findByName("USD/PLN");
        assertEquals("USD/PLN", currencyPairOptional.get(0).getName());
    }
}