package com.wezaam.withdrawal.application.service;

import com.wezaam.withdrawal.domain.model.User;
import com.wezaam.withdrawal.domain.ports.in.UserPortIn;
import com.wezaam.withdrawal.infrastructure.adapters.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserService implements UserPortIn {

    private final UserRepository userRepository;

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
