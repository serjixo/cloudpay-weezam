package com.wezaam.withdrawal.application.service;

import com.wezaam.withdrawal.domain.model.Withdrawal;
import com.wezaam.withdrawal.domain.model.WithdrawalScheduled;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EventsService {

    @Async
    public void send(Withdrawal withdrawal) {
        // build and send an event in message queue async
    }

    @Async
    public void send(WithdrawalScheduled withdrawal) {
        // build and send an event in message queue async
    }
}
