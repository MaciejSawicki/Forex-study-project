package com.sawicki.forex.service;

import org.springframework.stereotype.Service;

@Service
public interface CanvasjsChartService {

    String getAssetRecords(String symbol);

    String getNextAssetPoint(String pairName);
}
