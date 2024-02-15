package com.ptit.qldt.services.impl;

import com.ptit.qldt.models.CourseRegistration;
import com.ptit.qldt.repositories.CourseRegistrationRepository;
import com.ptit.qldt.services.CourseRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseRegistrationServiceImpl implements CourseRegistrationService {
    private CourseRegistrationRepository courseRegistrationRepository;
    @Autowired
    public CourseRegistrationServiceImpl(CourseRegistrationRepository courseRegistrationRepository) {
        this.courseRegistrationRepository = courseRegistrationRepository;
    }

    @Override
    public List<String> getTermByStudentID(int id) {
        return courseRegistrationRepository.findTermByAccountId(id);
    }

    @Override
    public List<CourseRegistration> getCRByIdAndTerm(int id, String s) {
        return courseRegistrationRepository.findCRByIdAndTerm(id, s);
    }
}
