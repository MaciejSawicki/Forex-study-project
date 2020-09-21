package com.sawicki.forex.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<Asset> assets = new ArrayList<>();

    public Account() {

    }

    public Account addAsset(Asset asset) {
        asset.setAccount(this);
        this.assets.add(asset);
        return this;
    }
}
