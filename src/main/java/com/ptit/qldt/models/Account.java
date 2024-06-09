package com.ptit.qldt.models;

//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    private int account_id;
    private String name;
    private String username;
    private String password;
    private String email;
    private Integer role;
    private String user_id_telegram;
    private String otp;
}
