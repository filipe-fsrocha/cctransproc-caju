package br.com.fsrocha.cctransproc.domain.transaction;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    APPROVED("00", "Approved"),
    REJECTED("51", "Insufficient balance"),
    UNAUTHORIZED("07", "Transaction canceled");

    private final String code;
    private final String message;

    TransactionStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
