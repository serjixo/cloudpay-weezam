package com.wezaam.withdrawal.domain.model;

import com.wezaam.withdrawal.domain.model.PaymentMethod;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String firstName;
    @OneToMany(mappedBy = "user")
    private List<PaymentMethod> paymentMethods;
    private Double maxWithdrawalAmount;

}
