package com.sawicki.forex.repository;

import com.sawicki.forex.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
