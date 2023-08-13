package com.wezaam.withdrawal.domain.ports.out;

import com.wezaam.withdrawal.domain.model.PaymentMethod;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodPortOut {
    Optional<PaymentMethod> findById(Long id);
}
