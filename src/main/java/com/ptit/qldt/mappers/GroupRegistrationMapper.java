package com.ptit.qldt.mappers;

import com.ptit.qldt.dtos.GroupRegistrationDto;
import com.ptit.qldt.models.GroupRegistration;

public class GroupRegistrationMapper {
    public static GroupRegistrationDto mapToGroupRegistrationDto(GroupRegistration groupRegistration) {
        return GroupRegistrationDto.builder()
                .id(groupRegistration.getId())
                .group(groupRegistration.getGroup())
                .account(groupRegistration.getAccount())
                .time(groupRegistration.getTime()).build();

    }
}
