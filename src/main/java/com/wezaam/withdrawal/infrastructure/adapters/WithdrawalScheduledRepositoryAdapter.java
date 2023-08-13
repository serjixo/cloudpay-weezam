package com.wezaam.withdrawal.infrastructure.adapters;

import com.wezaam.withdrawal.domain.model.WithdrawalScheduled;
import com.wezaam.withdrawal.domain.ports.out.WithdrawalScheduledPortOut;
import com.wezaam.withdrawal.infrastructure.adapters.repositories.WithdrawalScheduledRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
@Component
@AllArgsConstructor
public class WithdrawalScheduledRepositoryAdapter implements WithdrawalScheduledPortOut {

    private final WithdrawalScheduledRepository withdrawalScheduledRepository;

    @Override
    public WithdrawalScheduled save(WithdrawalScheduled withdrawal) {
        return withdrawalScheduledRepository.save(withdrawal);
    }

    @Override
    public Optional<WithdrawalScheduled> findById(Long id) {
        return withdrawalScheduledRepository.findById(id);
    }

    @Override
    public List<WithdrawalScheduled> findAll() {
        return withdrawalScheduledRepository.findAll();
    }

    public List<WithdrawalScheduled> findAllByExecuteAtBefore(Instant executeAt) {
        return withdrawalScheduledRepository.findAllByExecuteAtBefore(executeAt);
    }
}
