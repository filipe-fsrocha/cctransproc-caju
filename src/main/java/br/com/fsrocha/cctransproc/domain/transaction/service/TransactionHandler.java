package br.com.fsrocha.cctransproc.domain.transaction.service;

import br.com.fsrocha.cctransproc.domain.transaction.TransactionStatus;
import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.Transaction;
import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.TransactionCode;

public abstract class TransactionHandler {
    protected TransactionHandler next;

    public void setNextHandler(TransactionHandler nextHandler) {
        this.next = nextHandler;
    }

    public abstract TransactionCode execute(Transaction transaction);

    protected TransactionCode nextHandler(Transaction transaction) {
        if (next == null) {
            return new TransactionCode(TransactionStatus.APPROVED.getCode());
        }
        return next.execute(transaction);
    }

}
