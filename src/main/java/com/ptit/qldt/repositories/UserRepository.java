package com.ptit.qldt.repositories;

import com.ptit.qldt.dtos.AccountDto;
import com.ptit.qldt.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<Account, Integer> {
    Account findByEmail(String email);
    Account findByUsername(String userName);
    Account findFirstByUsername(String username);

    @Query("SELECT gr.account FROM GroupRegistration gr WHERE gr.group.groupId = :groupId")
    List<Account> findStudentsByGroupId(String groupId);
    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.password = :password WHERE a.account_id = :accountId")
    void updatePasswordByAccountId(int accountId, String password);
    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.otp = :otp WHERE a.account_id = :accountId")
    void updateOtp(int accountId, String otp);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.user_id_telegram = :userIdTelegram WHERE a.account_id = :accountId")
    void updateUserIdTelegram(int accountId, String userIdTelegram);
}
