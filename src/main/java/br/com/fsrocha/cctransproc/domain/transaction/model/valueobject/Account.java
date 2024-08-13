package br.com.fsrocha.cctransproc.domain.transaction.model.valueobject;

import lombok.Data;

@Data
public class Account {

    String cardNumber;
    String expiryDate;
    String cvc;
    String password;

}
