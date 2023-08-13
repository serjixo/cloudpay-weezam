package com.wezaam.withdrawal.application.service;

import com.wezaam.withdrawal.application.exceptions.TransactionException;
import com.wezaam.withdrawal.domain.model.*;
import com.wezaam.withdrawal.domain.ports.in.WithdrawalPortIn;
import com.wezaam.withdrawal.domain.ports.out.PaymentMethodPortOut;
import com.wezaam.withdrawal.domain.ports.out.WithdrawalPortOut;
import com.wezaam.withdrawal.domain.ports.out.WithdrawalScheduledPortOut;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class WithdrawalService implements WithdrawalPortIn {

    private final WithdrawalPortOut withdrawalPortOut;

    private final WithdrawalScheduledPortOut withdrawalScheduledPortOut;

    private final WithdrawalProcessingService withdrawalProcessingService;

    private final PaymentMethodPortOut paymentMethodPortOut;

    private final EventsService eventsService;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void create(Withdrawal withdrawal) {
        Withdrawal pendingWithdrawal = withdrawalPortOut.save(withdrawal);

        executorService.submit(() -> {
            processPendingWithdrawal(pendingWithdrawal);
        });

    }

    private void processPendingWithdrawal( Withdrawal pendingWithdrawal) {
        Optional<Withdrawal> savedWithdrawalOptional = withdrawalPortOut.findById(pendingWithdrawal.getId());

        if (!savedWithdrawalOptional.isPresent()) {
            return;
        }

        PaymentMethod paymentMethod;
        paymentMethod = paymentMethodPortOut.findById(savedWithdrawalOptional.get().getPaymentMethodId()).orElse(null);

        if (paymentMethod == null) {
            return;
        }

        Withdrawal savedWithdrawal = savedWithdrawalOptional.get();
        processWithdrawalWithPaymentMethod(savedWithdrawal, paymentMethod);

    }

    private void processWithdrawalWithPaymentMethod(Withdrawal withdrawal, PaymentMethod paymentMethod) {
        try {
            var transactionId = withdrawalProcessingService.sendToProcessing(withdrawal.getAmount(), paymentMethod);
            updateAndSendWithdrawalEvent(withdrawal, WithdrawalStatus.PROCESSING, transactionId);
        } catch (TransactionException te) {
            updateAndSendWithdrawalEvent(withdrawal, WithdrawalStatus.FAILED, null);
        } catch (Exception e) {
            updateAndSendWithdrawalEvent(withdrawal, WithdrawalStatus.INTERNAL_ERROR, null);
        }
    }

    private void updateAndSendWithdrawalEvent(Withdrawal withdrawal, WithdrawalStatus status, Long transactionId) {
        setStatusAndTransaction(withdrawal,status,transactionId);
        withdrawalPortOut.save(withdrawal);
        eventsService.send(withdrawal);
    }
    private void updateAndSend(WithdrawalScheduled withdrawal, WithdrawalStatus status, Long transactionId) {
        setStatusAndTransaction(withdrawal, status, transactionId);
        withdrawalScheduledPortOut.save(withdrawal);
        eventsService.send(withdrawal);
    }

    private static void setStatusAndTransaction(Withdrawals withdrawal, WithdrawalStatus status, Long transactionId) {
        withdrawal.setStatus(status);
        if (transactionId != null) {
            withdrawal.setTransactionId(transactionId);
        }
    }

    public void schedule(WithdrawalScheduled withdrawalScheduled) {
        withdrawalScheduledPortOut.save(withdrawalScheduled);
    }
    @Scheduled(fixedDelay = 5000)
    public void run() {
        withdrawalScheduledPortOut.findAllByExecuteAtBefore(Instant.now())
                .forEach(this::processScheduled);
    }

    private void processScheduled(WithdrawalScheduled withdrawal) {
        paymentMethodPortOut.findById(withdrawal.getPaymentMethodId())
                .ifPresent(paymentMethod -> processPayment(withdrawal, paymentMethod));
    }

    private void processPayment(WithdrawalScheduled withdrawal, PaymentMethod paymentMethod) {
        try {
            Long transactionId = withdrawalProcessingService.sendToProcessing(withdrawal.getAmount(), paymentMethod);
            updateAndSend(withdrawal, WithdrawalStatus.PROCESSING, transactionId);
        } catch (TransactionException te) {
            updateAndSend(withdrawal, WithdrawalStatus.FAILED, null);
        } catch (Exception e) {
            updateAndSend(withdrawal, WithdrawalStatus.INTERNAL_ERROR, null);
        }
    }


//
//    @Scheduled(fixedDelay = 5000)
//    public void run() {
//        withdrawalScheduledRepository.findAllByExecuteAtBefore(Instant.now())
//                .forEach(this::processScheduled);
//    }
//
//    private void processScheduled(WithdrawalScheduled withdrawal) {
//        PaymentMethod paymentMethod = paymentMethodRepository.findById(withdrawal.getPaymentMethodId()).orElse(null);
//        if (paymentMethod != null) {
//            try {
//                var transactionId = withdrawalProcessingService.sendToProcessing(withdrawal.getAmount(), paymentMethod);
//                withdrawal.setStatus(WithdrawalStatus.PROCESSING);
//                withdrawal.setTransactionId(transactionId);
//                withdrawalScheduledRepository.save(withdrawal);
//                eventsService.send(withdrawal);
//            } catch (Exception e) {
//                if (e instanceof TransactionException) {
//                    withdrawal.setStatus(WithdrawalStatus.FAILED);
//                    withdrawalScheduledRepository.save(withdrawal);
//                    eventsService.send(withdrawal);
//                } else {
//                    withdrawal.setStatus(WithdrawalStatus.INTERNAL_ERROR);
//                    withdrawalScheduledRepository.save(withdrawal);
//                    eventsService.send(withdrawal);
//                }
//            }
//        }
//    }

    public List<Withdrawals> findAllWithdrawals() {

        List<Withdrawal> withdrawals = withdrawalPortOut.findAll();
        List<WithdrawalScheduled> withdrawalsScheduled = withdrawalScheduledPortOut.findAll();
        List<Withdrawals> result = new ArrayList<>();
        result.addAll(withdrawals);
        result.addAll(withdrawalsScheduled);

        return result;
    }
}
