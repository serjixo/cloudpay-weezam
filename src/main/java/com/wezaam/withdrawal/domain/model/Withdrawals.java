package com.wezaam.withdrawal.domain.model;

import java.time.Instant;

public interface Withdrawals {
    void setUserId(Long userId);

    Long getUserId();

    void setPaymentMethodId(Long paymentMethodId);

    Long getPaymentMethodId();

    void setAmount(Double amount);

    Double getAmount();

    void setCreatedAt(Instant createdAt);

    Instant getCreatedAt();

    void setStatus(WithdrawalStatus status);

    void setTransactionId(Long transactionId);
}
