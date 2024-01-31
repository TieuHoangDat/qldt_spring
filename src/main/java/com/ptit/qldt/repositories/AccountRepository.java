package com.ptit.qldt.repositories;

import com.ptit.qldt.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository  extends JpaRepository<Account, String> {
    Account findFirstByUsername(String username);
}
