package com.wezaam.withdrawal.domain.ports.out;

import com.wezaam.withdrawal.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserPortOut {
    List<User> findAll();

    Optional<User> findById(Long id);
}
