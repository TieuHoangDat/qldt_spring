package com.ptit.qldt.security;

import com.ptit.qldt.models.Account;
import com.ptit.qldt.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private AccountRepository accountRepository;

    @Autowired
    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = accountRepository.findFirstByUsername(username);
        if(user != null) {
            User authUser = new User(
                    user.getUsername(),
                    user.getPassword(),
                    List.of()
            );
            return authUser;
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
