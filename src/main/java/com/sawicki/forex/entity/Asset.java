package com.sawicki.forex.entity;


import lombok.*;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(exclude = "account")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Account account;

    private String currencyName;
    private String currencyCode;

    private double balance;

    public Asset(Account account, String currencyName, String currencyCode, double balance) {
        this.account = account;
        this.currencyName = currencyName;
        this.currencyCode = currencyCode;
        this.balance = balance;
    }

    public Asset() {

    }
}
