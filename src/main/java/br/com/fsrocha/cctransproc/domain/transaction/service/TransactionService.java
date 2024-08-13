package br.com.fsrocha.cctransproc.domain.transaction.service;

import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.Transaction;
import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.TransactionCode;

public interface TransactionService {

    TransactionCode executePayment(Transaction transaction);

}
