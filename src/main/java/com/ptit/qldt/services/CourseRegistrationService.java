package com.ptit.qldt.services;

import com.ptit.qldt.models.CourseRegistration;

import java.util.List;

public interface CourseRegistrationService {
    List<String> getTermByStudentID(int id);

    List<CourseRegistration> getCRByIdAndTerm(int id, String s);
}
