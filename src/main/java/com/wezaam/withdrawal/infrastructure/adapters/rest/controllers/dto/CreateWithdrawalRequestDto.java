package com.wezaam.withdrawal.infrastructure.adapters.rest.controllers.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NonNull;
@ApiModel(description = "Details about the withdrawal request")
public record CreateWithdrawalRequestDto(  @ApiModelProperty(notes = "The unique ID of the user")
                                           @NonNull Long userId,

                                           @ApiModelProperty(notes = "The ID of the payment method used")
                                           @NonNull Long paymentMethodId,

                                           @ApiModelProperty(notes = "The amount to withdraw")
                                           @NonNull Double amount,

                                           @ApiModelProperty(notes = "The execution time for the withdrawal, can be 'ASAP' or a specific time")
                                           @NonNull String executeAt) {
}
/*
* {
"userId":"1",
"paymentMethodId":"1",
"amount":"200",
"executeAt":"ASAP"

}*/