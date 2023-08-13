package com.wezaam.withdrawal.infrastructure.adapters.rest.controllers;

import com.wezaam.withdrawal.domain.model.User;
import com.wezaam.withdrawal.domain.ports.in.UserPortIn;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
@AllArgsConstructor
public class UserController {
    private final UserPortIn userPortInService;

    @GetMapping("/find-all-users")
    public List<User> findAll() {
        return userPortInService.findAll();
    }

    @GetMapping("/find-user-by-id/{id}")
    public User findById(@PathVariable Long id) {
        return userPortInService.findById(id).orElseThrow();
    }
}
