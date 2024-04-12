package com.ptit.qldt.repositories;

import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.Course;
import com.ptit.qldt.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface GroupRepository  extends JpaRepository<Group, String> {
    @Query("SELECT g FROM Group g WHERE g.course IN :courses")
    List<Group> findGroupsByCourses(@Param("courses") List<Course> courses);
    @Query("SELECT g FROM Group g WHERE g.teacher.account_id = :accountId")
    List<Group> getGroupByTeacherID(int accountId);


    List<Group> findByCourseId(String courseId);

    @Modifying
    @Transactional
    @Query("UPDATE Group g SET g.availableSlots = g.availableSlots - 1 WHERE g.groupId = :groupId")
    void decreaseAvailableSlots(@Param("groupId") String groupId);

    @Modifying
    @Transactional
    @Query("UPDATE Group g SET g.availableSlots = g.availableSlots + 1 WHERE g.groupId = :groupId")
    void increaseAvailableSlots(@Param("groupId") String groupId);

    @Query("SELECT g FROM Group g WHERE g.groupId = :groupId")
    Group findGroupById(@Param("groupId") String groupId);

    @Query("SELECT a FROM Account a")
    List<Account> findAllAccount();
    @Query("SELECT gr FROM Group gr WHERE gr.time LIKE %:dayOfWeek%")
    List<Group> findByDayOfWeek(@Param("dayOfWeek") String dayOf);
}
