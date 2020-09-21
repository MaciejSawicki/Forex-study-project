package com.sawicki.forex.controller;


import com.sawicki.forex.entity.TransactionHistory;
import com.sawicki.forex.model.TransactionRequest;
import com.sawicki.forex.service.CanvasjsChartService;
import com.sawicki.forex.service.TransactionService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RestController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CanvasjsChartService canvasjsChartService;
    private final TransactionService transactionService;

    public RestController(CanvasjsChartService canvasjsChartService, TransactionService transactionService) {
        this.canvasjsChartService = canvasjsChartService;
        this.transactionService = transactionService;
    }

    @GetMapping("/restfull-service/currencyPair.json")
    public @ResponseBody String getDataPoints(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return canvasjsChartService.getAssetRecords("USD/PLN");
    }

    @GetMapping("/restfull-service/next-value.json")
    public @ResponseBody String getNextValue(@RequestParam(value = "pairname") String pairName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return canvasjsChartService.getNextAssetPoint(pairName);
    }

    @PostMapping(path = "/buyAsset",
            produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody TransactionHistory buyAssets(@RequestBody TransactionRequest transaction) throws Exception {
        TransactionHistory transactionHistory = transactionService.buyAsset(transaction.getPairName(),transaction.getVolume());
        if(transactionHistory != null) {
            return transactionHistory;
        } else {
            return null;
        }
    }

    @PostMapping(path = "/sellAsset",
            produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody TransactionHistory sellAssets(@RequestBody TransactionRequest transaction) throws Exception {
        return transactionService.sellAsset(transaction.getPairName(),transaction.getVolume());
    }


}