package com.choi.securitystudy.user.service;

import com.choi.securitystudy.user.dto.UserRequestDTO;
import com.choi.securitystudy.user.entity.User;
import com.choi.securitystudy.user.entity.UserRole;
import com.choi.securitystudy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(UserRequestDTO dto){
        String username = dto.username();
        String password = dto.password();

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.USER);

        userRepository.save(user);
    }
}
