package com.choi.securitystudy.user.controller;

import com.choi.securitystudy.user.dto.UserRequestDTO;
import com.choi.securitystudy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public String join(@RequestBody UserRequestDTO dto) {
        this.userService.join(dto);
        return "SUCCESS";
    }
}
