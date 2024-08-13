package br.com.fsrocha.cctransproc.domain.transaction.model.valueobject;

import lombok.Data;

@Data
public class TransactionCode {
    String code;

    public TransactionCode(String code) {
        this.code = code;
    }
}
