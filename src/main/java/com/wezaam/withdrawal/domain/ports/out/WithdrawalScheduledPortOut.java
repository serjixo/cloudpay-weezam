package com.wezaam.withdrawal.domain.ports.out;

import com.wezaam.withdrawal.domain.model.Withdrawal;
import com.wezaam.withdrawal.domain.model.WithdrawalScheduled;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface WithdrawalScheduledPortOut {

    WithdrawalScheduled save(WithdrawalScheduled withdrawal);

    Optional<WithdrawalScheduled> findById(Long id);

    List<WithdrawalScheduled> findAll();
    List<WithdrawalScheduled> findAllByExecuteAtBefore(Instant executeAt);
}
