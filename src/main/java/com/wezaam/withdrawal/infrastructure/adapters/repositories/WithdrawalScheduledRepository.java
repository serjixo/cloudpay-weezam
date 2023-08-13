package com.wezaam.withdrawal.infrastructure.adapters.repositories;

import com.wezaam.withdrawal.domain.model.WithdrawalScheduled;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface WithdrawalScheduledRepository extends JpaRepository<WithdrawalScheduled, Long> {

    List<WithdrawalScheduled> findAllByExecuteAtBefore(Instant date);
}
