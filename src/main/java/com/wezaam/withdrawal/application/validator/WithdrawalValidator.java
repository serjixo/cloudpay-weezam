package com.wezaam.withdrawal.application.validator;

import com.wezaam.withdrawal.infrastructure.adapters.rest.controllers.dto.CreateWithdrawalRequestDto;
import com.wezaam.withdrawal.application.service.PaymentMethodService;
import com.wezaam.withdrawal.application.service.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WithdrawalValidator {

    private final UserService userService;

    private final PaymentMethodService paymentMethodService;

    public void validate(CreateWithdrawalRequestDto createWithdrawalRequestDto) throws NotFoundException {
        if (userService.findById(createWithdrawalRequestDto.userId()).isEmpty()) {
            throw new NotFoundException("User not found");
        }
        if (paymentMethodService.findById(createWithdrawalRequestDto.paymentMethodId()).isEmpty()) {
            throw new NotFoundException("Payment method not found");
        }
    }
}
