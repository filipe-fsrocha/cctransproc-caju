package br.com.fsrocha.cctransproc.utils;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionBuilder {
    int mcc;
    BigDecimal totalAmount;
    String merchant;
    String passwordOrCvc;
}
