package com.wezaam.withdrawal.infrastructure.adapters.repositories;

import com.wezaam.withdrawal.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
