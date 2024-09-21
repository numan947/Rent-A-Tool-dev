package com.numan947.toolrent.auth;

import com.numan947.toolrent.auth.dto.RegistrationRequestDTO;
import com.numan947.toolrent.role.Role;
import com.numan947.toolrent.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
@RequiredArgsConstructor
public class AuthMapper {
//    private final PasswordEncoder passwordEncoder;
//    public User toUser(RegistrationRequestDTO registrationRequestDTO, Role userRole) {
//        return User.builder()
//                .firstname(registrationRequestDTO.firstname())
//                .lastname(registrationRequestDTO.lastname())
//                .email(registrationRequestDTO.email())
//                .password(passwordEncoder.encode(registrationRequestDTO.password()))
//                .enabled(false)
//                .locked(false)
//                .roles(List.of(userRole))
//                .build();
//    }
}
