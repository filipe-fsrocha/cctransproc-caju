package br.com.fsrocha.cctransproc.domain.transaction.validation;

import br.com.fsrocha.cctransproc.domain.card.service.CardService;
import br.com.fsrocha.cctransproc.domain.transaction.TransactionStatus;
import br.com.fsrocha.cctransproc.domain.transaction.TransactionType;
import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.Account;
import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.Transaction;
import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.TransactionCode;
import br.com.fsrocha.cctransproc.domain.transaction.service.TransactionHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ValidateCardHandler extends TransactionHandler {

    CardService cardService;

    @Override
    public TransactionCode execute(Transaction transaction) {
        var isValidCard = isValidCard(transaction.getType(), transaction.getAccount());

        if (isValidCard) {
            return nextHandler(transaction);
        }
        return new TransactionCode(TransactionStatus.UNAUTHORIZED.getCode());
    }

    private boolean isValidCard(TransactionType type, Account account) {
        if (TransactionType.POS == type) {
            return cardService.isValidCard(type, account.getCardNumber(), account.getPassword());
        }
        return cardService.isValidCard(type, account.getCardNumber(), account.getCvc());
    }

}
