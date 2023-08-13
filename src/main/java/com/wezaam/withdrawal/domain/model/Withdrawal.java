package com.wezaam.withdrawal.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity(name = "withdrawals")
public class Withdrawal implements Withdrawals {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long transactionId;
    private Double amount;
    private Instant createdAt;
    private Long userId;
    private Long paymentMethodId;
    @Enumerated(EnumType.STRING)
    private WithdrawalStatus status;

}
