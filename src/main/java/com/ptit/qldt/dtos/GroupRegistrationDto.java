package com.ptit.qldt.dtos;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;
@Data
public class GroupRegistrationDto {
    private AccountDto account;
    private GroupDto group;
    private Date time;
}
