package com.ptit.qldt.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseDto {
    private String id;
    private String name;
    private int num_credit;
    private int term;
    private int notcal;
}
