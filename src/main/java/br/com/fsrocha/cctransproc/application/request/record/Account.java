package br.com.fsrocha.cctransproc.application.request.record;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Account {

    @NotBlank(message = "cardNumber is required")
    String cardNumber;
    String cvc;
    String password;

}
