package br.com.fsrocha.cctransproc.domain.transaction.service.impl;

import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.Transaction;
import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.TransactionCode;
import br.com.fsrocha.cctransproc.domain.transaction.service.TransactionService;
import br.com.fsrocha.cctransproc.domain.transaction.validation.CheckBalanceHandler;
import br.com.fsrocha.cctransproc.domain.transaction.validation.CheckMCCAndMerchantHandler;
import br.com.fsrocha.cctransproc.domain.transaction.validation.RegisterTransactionHandler;
import br.com.fsrocha.cctransproc.domain.transaction.validation.ValidateCardHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionServiceImpl implements TransactionService {

    ValidateCardHandler validateCard;
    CheckMCCAndMerchantHandler checkMCC;
    CheckBalanceHandler checkBalance;
    RegisterTransactionHandler registerTransaction;

    @Override
    @Transactional
    public TransactionCode executePayment(Transaction transaction) {
        validateCard.setNextHandler(checkMCC);
        checkMCC.setNextHandler(checkBalance);
        checkBalance.setNextHandler(registerTransaction);
        return validateCard.execute(transaction);
    }
}
