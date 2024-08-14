package br.com.fsrocha.cctransproc.utils;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionBuilder {
    String mcc;
    BigDecimal totalAmount;
    String merchant;
    String passwordOrCvc;
}
