package com.ptit.qldt.dtos;

import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.Group;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@Builder
public class GroupRegistrationDto {
    private int id;
    private Group group;
    private Account account;
    private LocalDateTime time;
}
