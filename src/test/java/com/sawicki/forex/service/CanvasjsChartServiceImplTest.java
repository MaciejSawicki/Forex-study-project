package com.sawicki.forex.service;

import com.sawicki.forex.entity.CurrencyPair;
import com.sawicki.forex.repository.CurrencyPairRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CanvasjsChartServiceImplTest {

    private CanvasjsChartService canvasjsChartService;

    @Mock
    private CurrencyPairRepository currencyPairRepository;

    private CurrencyPair currencyPair1;
    private CurrencyPair currencyPair2;
    private List<CurrencyPair> currencyPairs;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        canvasjsChartService = new CanvasjsChartServiceImpl(currencyPairRepository);

        currencyPair1 = new CurrencyPair();
        currencyPair1.setDate(new Timestamp(1234));
        currencyPair1.setSellValue(3.23);
        currencyPair1.setBuyValue(3.24);

        currencyPair2 = new CurrencyPair();
        currencyPair2.setDate(new Timestamp(1235));
        currencyPair2.setSellValue(3.25);
        currencyPair2.setBuyValue(3.27);

        currencyPairs = new ArrayList<>();
        currencyPairs.add(currencyPair1);
        currencyPairs.add(currencyPair2);

    }

    @Test
    public void getAssetRecordsTest() throws Exception {
        when(currencyPairRepository.findByName(anyString())).thenReturn(currencyPairs);
        assertNotNull(canvasjsChartService.getAssetRecords("PLN/USD"));
        verify(currencyPairRepository, times(1)).findByName(anyString());
    }

    @Test
    public void getNextAssetPointTest() throws Exception {
        when(currencyPairRepository.findFirstByNameOrderByDateDesc(anyString())).thenReturn(Optional.of(currencyPair1));

        assertNotNull(canvasjsChartService.getNextAssetPoint("USD/PLN"));

        verify(currencyPairRepository, times(1)).findFirstByNameOrderByDateDesc(anyString());
        verify(currencyPairRepository,times(1)).save(any());
    }
}