package com.wezaam.withdrawal.infrastructure.adapters;

import com.wezaam.withdrawal.domain.model.User;
import com.wezaam.withdrawal.domain.ports.out.UserPortOut;
import com.wezaam.withdrawal.infrastructure.adapters.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserRepositoryAdapter implements UserPortOut {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
