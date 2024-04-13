package com.ptit.qldt.repositories;

import com.ptit.qldt.models.Course;
import com.ptit.qldt.models.Group;
import com.ptit.qldt.models.GroupRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface GroupRegistrationRepository  extends JpaRepository<GroupRegistration, Integer> {

    @Query("SELECT gr FROM GroupRegistration gr WHERE gr.account.account_id = :accountId AND gr.group.course.term = 6")
    List<GroupRegistration> findGroupRegistration(@Param("accountId") int accountId);

    @Query("SELECT gr.group FROM GroupRegistration gr WHERE gr.group.time LIKE %:dayOfWeek%")
    List<Group> findByDayOfWeek(@Param("dayOfWeek") String dayOfWeek);

//    @Query("SELECT gr FROM GroupRegistration gr WHERE gr.account.account_id = :accountId AND gr.time")
//    List<GroupRegistration> findGroupRegistration(@Param("accountId") int accountId);

    @Modifying
    @Transactional
    @Query("DELETE FROM GroupRegistration gr WHERE gr.account.account_id = :accountId AND gr.group.groupId = :groupId")
    void deleteByAccountIdAndGroupId(@Param("accountId") int accountId, @Param("groupId") String groupId);

    @Query("SELECT gr FROM GroupRegistration gr WHERE gr.group.time LIKE %:dayOfWeek% AND gr.group.time LIKE %:time%")
    List<GroupRegistration> findGroupRegistrationByDayOfWeekandTime(@Param("dayOfWeek") String dayOfWeek, @Param("time") String time);

}
