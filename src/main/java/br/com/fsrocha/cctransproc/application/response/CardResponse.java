package br.com.fsrocha.cctransproc.application.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CardResponse {

    String cardNumber;
    LocalDate expirationDate;
    int ccv;
    List<AccountResponse> accounts;

}
