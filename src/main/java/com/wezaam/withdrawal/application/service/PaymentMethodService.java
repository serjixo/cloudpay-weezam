package com.wezaam.withdrawal.application.service;

import com.wezaam.withdrawal.domain.model.PaymentMethod;
import com.wezaam.withdrawal.infrastructure.adapters.repositories.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    public Optional<PaymentMethod> findById(Long userId) {
        return paymentMethodRepository.findById(userId);
    }

}
