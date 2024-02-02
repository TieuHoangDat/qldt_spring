package com.ptit.qldt.repositories;

import com.ptit.qldt.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Integer> {
    Account findByEmail(String email);
    Account findByUsername(String userName);
    Account findFirstByUsername(String username);
}
