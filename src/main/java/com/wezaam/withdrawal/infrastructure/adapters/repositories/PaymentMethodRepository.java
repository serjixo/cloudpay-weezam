package com.wezaam.withdrawal.infrastructure.adapters.repositories;

import com.wezaam.withdrawal.domain.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}
