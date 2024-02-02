package com.ptit.qldt.repositories;

import com.ptit.qldt.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository  extends JpaRepository<Course, String> {
}
