package com.ptit.qldt.repositories;
import com.ptit.qldt.models.Course;
import com.ptit.qldt.models.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, String> {

    @Query("SELECT cr.course FROM CourseRegistration cr WHERE cr.account.account_id = :accountId AND cr.term = 'Kỳ 2 năm học 2023-2024'")
    List<Course> findCourseRegister(@Param("accountId") int accountId);
    @Query("SELECT c FROM Course c WHERE c.id LIKE CONCAT( '%',:name,'%') or c.name like CONCAT( '%',:name,'%') ")
    List<Course> findByName(@Param("name") String name );

    @Query("SELECT cr FROM CourseRegistration cr WHERE cr.account.account_id = :accountId and cr.course.id = :courseId ")
    CourseRegistration findCourseRegisterByCourseIdAndAccountId(@Param("courseId") String courseId,@Param("accountId") int accountId);

    @Query("SELECT c FROM Course c WHERE c.term = :semester")
    List<Course> findBySemester(@Param("semester") int semester);
}
