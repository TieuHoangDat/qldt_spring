package com.ptit.qldt.repositories;

import com.ptit.qldt.models.Course;
import com.ptit.qldt.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository  extends JpaRepository<Group, String> {
    @Query("SELECT g FROM Group g WHERE g.course IN :courses")
    List<Group> findGroupsByCourses(@Param("courses") List<Course> courses);
}
