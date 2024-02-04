package com.ptit.qldt.repositories;
import com.ptit.qldt.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, String> {

    @Query("SELECT cr.course FROM CourseRegistration cr WHERE cr.account.account_id = :accountId AND cr.term = 'Kỳ 1 năm học 2023-2024'")
    List<Course> findCourseRegister(@Param("accountId") int accountId);
}
