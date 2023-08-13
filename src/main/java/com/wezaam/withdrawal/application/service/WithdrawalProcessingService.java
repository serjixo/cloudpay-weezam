package com.wezaam.withdrawal.application.service;

import com.wezaam.withdrawal.application.exceptions.TransactionException;
import com.wezaam.withdrawal.domain.model.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class WithdrawalProcessingService {

    public Long sendToProcessing(Double amount, PaymentMethod paymentMethod) throws TransactionException {
        // call a payment provider
        // in case a transaction can be process
        // it generates a transactionId and process the transaction async
        // otherwise it throws TransactionException
        return System.nanoTime();
    }
}
