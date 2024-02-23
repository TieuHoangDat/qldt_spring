package com.ptit.qldt.dtos;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class NotificationDto {
    private Integer id;
    private String title;
    private String message;
    private LocalDateTime createdAt;
}
