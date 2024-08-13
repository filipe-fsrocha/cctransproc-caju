package br.com.fsrocha.cctransproc.domain.transaction.validation;

import br.com.fsrocha.cctransproc.domain.card.AccountType;
import br.com.fsrocha.cctransproc.domain.card.entities.AccountEntity;
import br.com.fsrocha.cctransproc.domain.card.repository.AccountRepositoryService;
import br.com.fsrocha.cctransproc.domain.error.ServiceException;
import br.com.fsrocha.cctransproc.domain.transaction.TransactionStatus;
import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.Transaction;
import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.TransactionCode;
import br.com.fsrocha.cctransproc.domain.transaction.service.TransactionHandler;
import br.com.fsrocha.cctransproc.domain.utils.MaskUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CheckBalanceHandler extends TransactionHandler {
    private static final int ZERO = 0;

    AccountRepositoryService accountRepositoryService;

    @Override
    public TransactionCode execute(Transaction transaction) {
        try {
            var isAuthorized = isAuthorizedBalance(transaction);

            if (isAuthorized) {
                return nextHandler(transaction);
            }
            return new TransactionCode(TransactionStatus.REJECTED.getCode());
        } catch (Exception e) {
            LOGGER.error("Unable to identify account", e);
            return new TransactionCode(TransactionStatus.UNAUTHORIZED.getCode());
        }
    }

    private boolean isAuthorizedBalance(Transaction transaction) {
        var account = getAccount(transaction.getAccount().getCardNumber(), transaction.getTargetAccountType());
        var isAuthorized = authorizeAndAdjustBalance(account, transaction);

        if (!isAuthorized) {
            account = getAccount(transaction.getAccount().getCardNumber(), AccountType.CASH);
            transaction.setTargetAccountType(AccountType.CASH);
            return authorizeAndAdjustBalance(account, transaction);
        }

        return true;
    }

    boolean authorizeAndAdjustBalance(AccountEntity account, Transaction transaction) {
        var subtotal = account.getTotalAmount().subtract(transaction.getTotalAmount());

        if (subtotal.compareTo(BigDecimal.ZERO) >= ZERO) {
            transaction.setAccountId(account);
            account.setTotalAmount(subtotal);
            accountRepositoryService.save(account);
            return true;
        }
        return false;
    }

    private AccountEntity getAccount(String cardNumber, AccountType type) {
        var account = accountRepositoryService.findAccount(MaskUtils.applyMask(cardNumber), type);
        if (account == null) {
            throw new ServiceException(HttpStatus.NOT_FOUND, "Account not found");
        }
        return account;
    }

}
