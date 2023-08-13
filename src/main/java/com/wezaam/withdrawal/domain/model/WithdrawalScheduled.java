package com.wezaam.withdrawal.domain.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity(name = "scheduled_withdrawals")
public class WithdrawalScheduled implements Withdrawals {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long transactionId;
    private Double amount;
    private Instant createdAt;
    private Instant executeAt;
    private Long userId;
    private Long paymentMethodId;
    @Enumerated(EnumType.STRING)
    private WithdrawalStatus status;


}
