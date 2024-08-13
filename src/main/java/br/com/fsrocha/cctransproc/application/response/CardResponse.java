package br.com.fsrocha.cctransproc.application.response;

import lombok.Data;

import java.util.List;

@Data
public class CardResponse {

    String cardNumber;
    String expirationDate;
    String cvc;
    List<AccountResponse> accounts;

}
