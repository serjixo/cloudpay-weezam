package com.wezaam.withdrawal.infrastructure.adapters.rest.mapper;

import com.wezaam.withdrawal.domain.model.Withdrawal;
import com.wezaam.withdrawal.domain.model.WithdrawalScheduled;
import com.wezaam.withdrawal.domain.model.WithdrawalStatus;
import com.wezaam.withdrawal.domain.model.Withdrawals;
import com.wezaam.withdrawal.infrastructure.adapters.rest.controllers.dto.CreateWithdrawalRequestDto;

import java.time.Instant;
import java.time.format.DateTimeParseException;

public class WithdrawalMapper {
    public static Withdrawal withdrawalFromCreateWithdrawalRequestDto(CreateWithdrawalRequestDto createWithdrawalRequestDto) {

        Withdrawal withdrawal = new Withdrawal();

        setCommonPropertiesWithdrawals(createWithdrawalRequestDto, withdrawal);

        return withdrawal;
    }

    public static WithdrawalScheduled scheduledWithdrawalFromCreateWithdrawalRequestDto(CreateWithdrawalRequestDto createWithdrawalRequestDto) {

        WithdrawalScheduled scheduled = new WithdrawalScheduled();

        setCommonPropertiesWithdrawals(createWithdrawalRequestDto, scheduled);

        try {
            scheduled.setExecuteAt(Instant.parse(createWithdrawalRequestDto.executeAt()));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid timestamp format for executeAt", e);
        }

        return scheduled;
    }

    private static void setCommonPropertiesWithdrawals(CreateWithdrawalRequestDto createWithdrawalRequestDto, Withdrawals scheduled) {
        scheduled.setUserId(createWithdrawalRequestDto.userId());
        scheduled.setPaymentMethodId(createWithdrawalRequestDto.paymentMethodId());
        scheduled.setAmount(Double.valueOf(createWithdrawalRequestDto.amount()));
        scheduled.setCreatedAt(Instant.now());
        scheduled.setStatus(WithdrawalStatus.PENDING);
    }
}

