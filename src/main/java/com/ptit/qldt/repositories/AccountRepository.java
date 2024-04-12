package com.ptit.qldt.repositories;

import com.ptit.qldt.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Integer> {

    @Query("SELECT a FROM Account a WHERE a.role = 2")
    List<Account> findAllTeacher();

    @Query("SELECT a FROM Account a WHERE a.role = 3")
    List<Account> findAllStudent();
}
