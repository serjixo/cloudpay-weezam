package com.wezaam.withdrawal.domain.ports.out;

import com.wezaam.withdrawal.domain.model.Withdrawal;

import java.util.List;
import java.util.Optional;

public interface WithdrawalPortOut {

    Withdrawal save(Withdrawal withdrawal);

    Optional<Withdrawal> findById(Long id);

    List<Withdrawal> findAll();
}
