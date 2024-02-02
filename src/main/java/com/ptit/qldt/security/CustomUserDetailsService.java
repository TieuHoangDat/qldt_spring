//package com.ptit.qldt.security;
//
//import com.ptit.qldt.models.Account;
//import com.ptit.qldt.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//    private UserRepository userRepository;
//
//    @Autowired
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Account user = userRepository.findFirstByUsername(username);
//        if(user != null) {
//            User authUser = new User(
//                    user.getEmail(),
//                    user.getPassword(),
//                    user.getRole().toList()
//            );
//            return authUser;
//        } else {
//            throw new UsernameNotFoundException("Invalid username or password");
//        }
//    }
//}