package com.wezaam.withdrawal.infrastructure.adapters.rest.controllers;

import com.wezaam.withdrawal.application.service.WithdrawalService;
import com.wezaam.withdrawal.application.validator.WithdrawalValidator;
import com.wezaam.withdrawal.domain.model.Withdrawal;
import com.wezaam.withdrawal.domain.model.WithdrawalScheduled;
import com.wezaam.withdrawal.domain.ports.in.WithdrawalPortIn;
import com.wezaam.withdrawal.infrastructure.adapters.rest.controllers.dto.CreateWithdrawalRequestDto;
import com.wezaam.withdrawal.infrastructure.adapters.rest.mapper.WithdrawalMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequiredArgsConstructor
public class WithdrawalController {

    private final ApplicationContext context;

    private final WithdrawalValidator withdrawalValidator;
    private final WithdrawalPortIn withdrawalPortIn;

    @PostMapping("/create-withdrawals")
    @ApiOperation(value = "Create a new withdrawal request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created withdrawal request"),
            @ApiResponse(code = 400, message = "Invalid request provided")
    })
    public ResponseEntity<Object> create(@RequestBody CreateWithdrawalRequestDto createWithdrawalRequestDto) throws NotFoundException {

        withdrawalValidator.validate(createWithdrawalRequestDto);

        Object body;
        if (createWithdrawalRequestDto.executeAt().equals("ASAP")) {
            Withdrawal withdrawal = WithdrawalMapper.withdrawalFromCreateWithdrawalRequestDto(createWithdrawalRequestDto);
            withdrawalPortIn.create(withdrawal);
            body = withdrawal;
        } else {
            WithdrawalScheduled withdrawalScheduled = WithdrawalMapper.scheduledWithdrawalFromCreateWithdrawalRequestDto(createWithdrawalRequestDto);
            withdrawalPortIn.schedule(withdrawalScheduled);
            body = withdrawalScheduled;
        }

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/find-all-withdrawals")
    public ResponseEntity findAll() {
        WithdrawalService withdrawalService = context.getBean(WithdrawalService.class);
        return new ResponseEntity(withdrawalService.findAllWithdrawals(), HttpStatus.OK);
    }
}
