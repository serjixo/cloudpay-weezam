package com.wezaam.withdrawal.domain.ports.in;

import com.wezaam.withdrawal.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserPortIn {
    List<User> findAll();

    Optional<User> findById(Long id);
}
