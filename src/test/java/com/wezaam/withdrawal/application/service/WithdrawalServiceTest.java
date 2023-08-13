package com.wezaam.withdrawal.application.service;
import com.wezaam.withdrawal.application.exceptions.TransactionException;
import com.wezaam.withdrawal.domain.model.PaymentMethod;
import com.wezaam.withdrawal.domain.model.Withdrawal;
import com.wezaam.withdrawal.domain.model.WithdrawalStatus;
import com.wezaam.withdrawal.domain.ports.out.PaymentMethodPortOut;
import com.wezaam.withdrawal.domain.ports.out.WithdrawalPortOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class WithdrawalServiceTest {
    @InjectMocks
    private WithdrawalService withdrawalService;

    @Mock
    private WithdrawalPortOut withdrawalPortOut;

    @Mock
    private PaymentMethodPortOut paymentMethodPortOut;

    @Mock
    private WithdrawalProcessingService withdrawalProcessingService;

    @Mock
    private EventsService eventsService;

    @Mock
    private ExecutorService executorService;

    @BeforeEach
    public void setUp() throws Exception {
        // Use reflection to access the private executorService field
        Field field = WithdrawalService.class.getDeclaredField("executorService");
        field.setAccessible(true);
        ExecutorService mockExecutorService = mock(ExecutorService.class);
        field.set(withdrawalService, mockExecutorService);

        // Mock executorService to run tasks immediately rather than asynchronously
        when(mockExecutorService.submit(any(Runnable.class))).thenAnswer(invocation -> {
            Runnable task = invocation.getArgument(0);
            task.run();
            return null;
        });
    }

    @Test
    public void testCreateWithValidWithdrawal() throws InterruptedException, TransactionException {
        // Given
        Withdrawal mockWithdrawal = new Withdrawal();
        mockWithdrawal.setId(1L);
        mockWithdrawal.setPaymentMethodId(1L);
        mockWithdrawal.setAmount(100.0);

        when(withdrawalPortOut.save(any())).thenReturn(mockWithdrawal);
        when(withdrawalPortOut.findById(1L)).thenReturn(Optional.of(mockWithdrawal));
        when(paymentMethodPortOut.findById(1L)).thenReturn(Optional.of(new PaymentMethod()));
        when(withdrawalProcessingService.sendToProcessing(100.0, new PaymentMethod())).thenReturn(1L);

        // Execute
        withdrawalService.create(mockWithdrawal);

        // Verify
        verify(withdrawalPortOut, times(2)).save(mockWithdrawal);
        verify(withdrawalProcessingService).sendToProcessing(100.0, new PaymentMethod());
        verify(eventsService).send(mockWithdrawal);
    }

    @Test
    public void testCreateWithTransactionException() throws TransactionException {
        Withdrawal withdrawal = new Withdrawal();
        when(withdrawalPortOut.save(any())).thenReturn(withdrawal);
        when(withdrawalPortOut.findById(any())).thenReturn(Optional.of(withdrawal));
        when(paymentMethodPortOut.findById(any())).thenReturn(Optional.of(new PaymentMethod()));
        when(withdrawalProcessingService.sendToProcessing(any(), any())).thenThrow(TransactionException.class);

        withdrawalService.create(withdrawal);
        assertEquals(WithdrawalStatus.FAILED, withdrawal.getStatus());
        verify(eventsService, times(1)).send(withdrawal);
    }

    @Test
    public void testCreateWithdrawalNotFound() throws TransactionException {
        Withdrawal withdrawal = new Withdrawal();

        when(withdrawalPortOut.save(any())).thenReturn(withdrawal);
        when(withdrawalPortOut.findById(any())).thenReturn(Optional.empty());

        withdrawalService.create(withdrawal);

        // As the withdrawal was not found, no further actions should be taken.
        verify(paymentMethodPortOut, never()).findById(any());
        verify(withdrawalProcessingService, never()).sendToProcessing(any(), any());
    }

    @Test
    public void testCreatePaymentMethodNotFound() throws TransactionException {
        Withdrawal withdrawal = new Withdrawal();

        when(withdrawalPortOut.save(any())).thenReturn(withdrawal);
        when(withdrawalPortOut.findById(any())).thenReturn(Optional.of(withdrawal));
        when(paymentMethodPortOut.findById(any())).thenReturn(Optional.empty());

        withdrawalService.create(withdrawal);

        // As the payment method was not found, no further actions should be taken.
        verify(withdrawalProcessingService, never()).sendToProcessing(any(), any());
    }

    @Test
    public void testCreateWithGenericException() throws TransactionException {
        Withdrawal withdrawal = new Withdrawal();

        when(withdrawalPortOut.save(any())).thenReturn(withdrawal);
        when(withdrawalPortOut.findById(any())).thenReturn(Optional.of(withdrawal));
        when(paymentMethodPortOut.findById(any())).thenReturn(Optional.of(new PaymentMethod()));
        when(withdrawalProcessingService.sendToProcessing(any(), any())).thenThrow(new RuntimeException("Some error"));

        withdrawalService.create(withdrawal);

        assertEquals(WithdrawalStatus.INTERNAL_ERROR, withdrawal.getStatus());
        verify(eventsService, times(1)).send(withdrawal);
    }

}