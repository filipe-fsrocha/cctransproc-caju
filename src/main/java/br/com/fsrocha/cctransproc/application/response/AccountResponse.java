package br.com.fsrocha.cctransproc.application.response;

import br.com.fsrocha.cctransproc.domain.card.AccountType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountResponse {

    UUID id;
    AccountType accountType;
    BigDecimal totalAmount;

}
