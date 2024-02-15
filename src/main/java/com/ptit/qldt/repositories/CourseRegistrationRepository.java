package com.ptit.qldt.repositories;

import com.ptit.qldt.models.Course;
import com.ptit.qldt.models.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRegistrationRepository  extends JpaRepository<CourseRegistration, Integer> {
    @Query("SELECT DISTINCT cr.term FROM CourseRegistration cr WHERE cr.account.account_id = :accountId")
    List<String> findTermByAccountId(int accountId);
    @Query("SELECT cr FROM CourseRegistration cr WHERE cr.account.account_id = :accountId AND cr.term = :term")
    List<CourseRegistration> findCRByIdAndTerm(int accountId, String term);
}
