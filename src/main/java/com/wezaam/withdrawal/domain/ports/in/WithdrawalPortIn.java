package com.wezaam.withdrawal.domain.ports.in;

import com.wezaam.withdrawal.domain.model.Withdrawal;
import com.wezaam.withdrawal.domain.model.WithdrawalScheduled;
import com.wezaam.withdrawal.domain.model.Withdrawals;

import java.util.List;

public interface WithdrawalPortIn {

    void create(Withdrawal withdrawal);

    void schedule(WithdrawalScheduled withdrawalScheduled);

    List<Withdrawals> findAllWithdrawals();

}
