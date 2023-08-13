package com.wezaam.withdrawal.infrastructure.adapters.repositories;

import com.wezaam.withdrawal.domain.model.Withdrawal;
import com.wezaam.withdrawal.domain.ports.out.WithdrawalPortOut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
}
