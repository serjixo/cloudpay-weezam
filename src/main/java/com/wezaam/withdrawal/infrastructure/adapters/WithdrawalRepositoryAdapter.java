package com.wezaam.withdrawal.infrastructure.adapters;

import com.wezaam.withdrawal.domain.model.User;
import com.wezaam.withdrawal.domain.model.Withdrawal;
import com.wezaam.withdrawal.domain.ports.out.UserPortOut;
import com.wezaam.withdrawal.domain.ports.out.WithdrawalPortOut;
import com.wezaam.withdrawal.infrastructure.adapters.repositories.WithdrawalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
@AllArgsConstructor
public class WithdrawalRepositoryAdapter implements WithdrawalPortOut {

     private final WithdrawalRepository withdrawalRepository;

    @Override
    public Withdrawal save(Withdrawal withdrawal) {
        return withdrawalRepository.save(withdrawal);
    }

    @Override
    public Optional<Withdrawal> findById(Long id) {
        return withdrawalRepository.findById(id);
    }

    @Override
    public List<Withdrawal> findAll() {
        return null;
    }
}
