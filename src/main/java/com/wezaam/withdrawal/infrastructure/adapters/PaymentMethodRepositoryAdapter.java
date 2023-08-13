package com.wezaam.withdrawal.infrastructure.adapters;

import com.wezaam.withdrawal.domain.model.PaymentMethod;
import com.wezaam.withdrawal.domain.model.WithdrawalScheduled;
import com.wezaam.withdrawal.domain.ports.out.PaymentMethodPortOut;
import com.wezaam.withdrawal.domain.ports.out.WithdrawalScheduledPortOut;
import com.wezaam.withdrawal.infrastructure.adapters.repositories.PaymentMethodRepository;
import com.wezaam.withdrawal.infrastructure.adapters.repositories.WithdrawalScheduledRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PaymentMethodRepositoryAdapter implements PaymentMethodPortOut {

    private final PaymentMethodRepository paymentMethodRepository;

    public Optional<PaymentMethod> findById(Long id)
    {
        return paymentMethodRepository.findById(id);
    }
}
