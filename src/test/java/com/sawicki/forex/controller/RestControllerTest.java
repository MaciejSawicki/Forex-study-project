package com.sawicki.forex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sawicki.forex.entity.TransactionHistory;
import com.sawicki.forex.service.CanvasjsChartService;
import com.sawicki.forex.service.TransactionHistoryService;
import com.sawicki.forex.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RestControllerTest {
    RestController controller;

    @Mock
    CanvasjsChartService canvasjsChartService;

    @Mock
    TransactionService transactionService;

    ObjectMapper objectMapper;

    @Mock
    TransactionHistory transactionHistory;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        transactionHistory = new TransactionHistory();
        transactionHistory.setId(1L);
        transactionHistory.setAction("BUY");
        transactionHistory.setBalance(2.0);
        transactionHistory.setDate(new Timestamp(1522));
        transactionHistory.setPairName("USD/PLN");
        transactionHistory.setPrice(20.0);
        transactionHistory.setValue(20.0);
        transactionHistory.setVolume(20.0);
        objectMapper = new ObjectMapper();

        controller = new RestController(canvasjsChartService, transactionService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getDataPoints() throws Exception {
        String canvasPoint = "[{\"x\": date,\"sellPrice\": 3.43,\"buyPrice\": 3.46}";
        when(canvasjsChartService.getAssetRecords(any())).thenReturn(canvasPoint);
        mockMvc.perform(get("/restfull-service/currencyPair.json"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(canvasPoint)));
        verify(canvasjsChartService,times(1)).getAssetRecords(any());
    }

    @Test
    public void getNextValue() throws Exception {
        String canvasPoint = "[{\"x\": date,\"sellPrice\": 3.43,\"buyPrice\": 3.46}";
        when(canvasjsChartService.getNextAssetPoint(any())).thenReturn(canvasPoint);
        mockMvc.perform(get("/restfull-service/next-value.json").param("pairname", "PLN/USD"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(canvasPoint)));
        verify(canvasjsChartService,times(1)).getNextAssetPoint(any());
    }

    @Test
    public void buyAsset() throws Exception {

        when(transactionService.buyAsset(anyString(), anyDouble())).thenReturn(transactionHistory);

        mockMvc.perform(post("/buyAsset").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionHistory)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(transactionService, times(1)).buyAsset(anyString(),anyDouble());
    }

    @Test
    public void buyAssetErrorSavingToRepository() throws Exception {

        when(transactionService.buyAsset(anyString(), anyDouble())).thenReturn(null);

        mockMvc.perform(post("/buyAsset").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionHistory)))
                .andExpect(status().isOk());
        verify(transactionService, times(1)).buyAsset(anyString(),anyDouble());
    }

    @Test
    public void sellAsset() throws Exception {

        when(transactionService.sellAsset(anyString(), anyDouble())).thenReturn(transactionHistory);

        mockMvc.perform(post("/sellAsset").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionHistory)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(transactionService, times(1)).sellAsset(anyString(),anyDouble());
    }

}