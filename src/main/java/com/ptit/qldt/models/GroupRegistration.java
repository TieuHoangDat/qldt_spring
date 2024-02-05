package com.ptit.qldt.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "groupregistrations")
public class GroupRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Column(name = "time", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL")
    private LocalDateTime time;

    public GroupRegistration() {
        this.time = LocalDateTime.now();
    }
}
