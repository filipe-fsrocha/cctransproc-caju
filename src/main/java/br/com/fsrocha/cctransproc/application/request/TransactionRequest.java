package br.com.fsrocha.cctransproc.application.request;

import br.com.fsrocha.cctransproc.application.request.record.Account;
import br.com.fsrocha.cctransproc.domain.transaction.TransactionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode
public class TransactionRequest {

    @NotNull(message = "Account is required")
    @Valid
    Account account;

    @NotNull(message = "totalAmount is required")
    BigDecimal totalAmount;

    @NotNull(message = "type is required")
    TransactionType type;

    @NotNull(message = "mcc is required")
    int mcc;

    @NotBlank(message = "merchant is required")
    String merchant;

}
