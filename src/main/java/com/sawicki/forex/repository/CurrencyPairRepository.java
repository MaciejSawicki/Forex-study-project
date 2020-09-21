package com.sawicki.forex.repository;

import com.sawicki.forex.entity.CurrencyPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyPairRepository extends JpaRepository<CurrencyPair, Long> {

    List<CurrencyPair> findByName(String symbol);

    Optional<CurrencyPair> findFirstByNameOrderByDateDesc(String symbol);
}
