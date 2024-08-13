package br.com.fsrocha.cctransproc.domain.transaction.validation;

import br.com.fsrocha.cctransproc.domain.transaction.TransactionStatus;
import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.Transaction;
import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.TransactionCode;
import br.com.fsrocha.cctransproc.domain.transaction.repository.TransactionRepositoryService;
import br.com.fsrocha.cctransproc.domain.transaction.service.TransactionHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegisterTransactionHandler extends TransactionHandler {

    TransactionRepositoryService transactionRepositoryService;

    @Override
    public TransactionCode execute(Transaction transaction) {
        try {
            transactionRepositoryService.save(transaction.toEntity());
            return new TransactionCode(TransactionStatus.APPROVED.getCode());
        } catch (Exception e) {
            LOGGER.error("An error occurred while recording the transaction", e);
            return new TransactionCode(TransactionStatus.UNAUTHORIZED.getCode());
        }
    }
}
