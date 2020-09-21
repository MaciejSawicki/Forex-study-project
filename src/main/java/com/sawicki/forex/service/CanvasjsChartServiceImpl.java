package com.sawicki.forex.service;

import com.sawicki.forex.entity.CurrencyPair;
import com.sawicki.forex.repository.CurrencyPairRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

@Service
public class CanvasjsChartServiceImpl implements CanvasjsChartService {

    private CurrencyPairRepository currencyPairRepository;

    private StringBuilder assetBuilder;

    public CanvasjsChartServiceImpl(CurrencyPairRepository currencyPairRepository) {
        this.currencyPairRepository = currencyPairRepository;
    }

    @Override
    public String getAssetRecords(String name) {
        List<CurrencyPair> currencyPairList = currencyPairRepository.findByName(name);
        assetBuilder = new StringBuilder("[");
        for (CurrencyPair currencyPair : currencyPairList) {
            assetBuilder.append("{\"x\":" + currencyPair.getDate().getTime() + ",\"sellPrice\":" + currencyPair.getSellValue() +",\"buyPrice\":"+ currencyPair.getBuyValue() + "},");
        }
        assetBuilder.setLength(assetBuilder.length() - 1);
        assetBuilder.append("]");
        return assetBuilder.toString();
    }

    @Override
    public String getNextAssetPoint(String pairName) {
        Random random = new Random();
        CurrencyPair previousCurrencyPair = currencyPairRepository.findFirstByNameOrderByDateDesc(pairName).get();

        CurrencyPair currencyPair = new CurrencyPair();
        currencyPair.setName(pairName);
        String[] currency = pairName.split("/");
        currencyPair.setCurrency1(currency[0]);
        currencyPair.setCurrency2(currency[1]);
        currencyPair.setSellValue(previousCurrencyPair.getSellValue() + ((random.nextDouble() / 20) - 0.025));
        currencyPair.setBuyValue(currencyPair.getSellValue() + (random.nextDouble() / 40));
        currencyPair.setDate(new Timestamp(previousCurrencyPair.getDate().getTime() + 5000));
        currencyPairRepository.save(currencyPair);
        return "{\"x\":" + currencyPair.getDate().getTime() + ",\"sellPrice\":" + currencyPair.getSellValue() +",\"buyPrice\":"+ currencyPair.getBuyValue() + "}";
    }


}