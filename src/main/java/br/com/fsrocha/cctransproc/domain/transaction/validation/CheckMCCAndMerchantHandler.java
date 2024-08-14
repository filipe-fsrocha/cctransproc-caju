package br.com.fsrocha.cctransproc.domain.transaction.validation;

import br.com.fsrocha.cctransproc.domain.merchant.repository.MerchantRepositoryService;
import br.com.fsrocha.cctransproc.domain.transaction.MCCAccountType;
import br.com.fsrocha.cctransproc.domain.transaction.TransactionStatus;
import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.Transaction;
import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.TransactionCode;
import br.com.fsrocha.cctransproc.domain.transaction.service.TransactionHandler;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CheckMCCAndMerchantHandler extends TransactionHandler {

    MerchantRepositoryService merchantRepositoryService;

    @Override
    public TransactionCode execute(Transaction transaction) {
        if (isValidMCC(transaction)) {
            return nextHandler(transaction);
        }
        return new TransactionCode(TransactionStatus.UNAUTHORIZED.getCode());
    }

    private boolean isValidMCC(Transaction transaction) {
        try {
            String mcc = transaction.getMcc();

            if (!isValidMCC(mcc)) {
                var merchant = merchantRepositoryService.findByName(transaction.getMerchant());
                mcc = merchant.getMcc().getMcc();
            }

            var mccTargetAccount = MCCAccountType.getInstance();
            transaction.setTargetAccountType(mccTargetAccount.getAccountType(mcc));
            return true;
        } catch (Exception e) {
            LOGGER.error("Unable to get mcc", e);
            return false;
        }
    }

    private boolean isValidMCC(@NonNull String mcc) {
        return mcc.matches("\\d+");
    }
}
