package br.com.fsrocha.cctransproc.domain.transaction.validation;

import br.com.fsrocha.cctransproc.domain.mcc.repository.MCCRepositoryService;
import br.com.fsrocha.cctransproc.domain.merchant.repository.MerchantRepositoryService;
import br.com.fsrocha.cctransproc.domain.transaction.MCCAccountType;
import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.Transaction;
import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.TransactionCode;
import br.com.fsrocha.cctransproc.domain.transaction.service.TransactionHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CheckMCCHandler extends TransactionHandler {

    MerchantRepositoryService merchantRepositoryService;
    MCCRepositoryService mccRepositoryService;

    @Override
    public TransactionCode execute(Transaction transaction) {
        var mcc = getMCC(transaction.getMerchant(), transaction.getMcc());
        var mccTypeMap = MCCAccountType.getInstance();
        transaction.setTargetAccountType(mccTypeMap.getAccountType(mcc));
        return nextHandler(transaction);
    }

    private String getMCC(String merchantName, String mcc) {
        String mccCode = getMCC(mcc);

        if (Strings.isBlank(mccCode)) {
            var merchant = merchantRepositoryService.findByName(merchantName);
            return String.valueOf(merchant.getMcc().getMcc());
        }
        return mccCode;
    }

    private String getMCC(String mcc) {
        try {
            var foundMcc = mccRepositoryService.findByMcc(Integer.parseInt(mcc));
            return String.valueOf(foundMcc.getMcc());
        } catch (Exception e) {
            LOGGER.error("Unable to obtain MCC: {}\n", mcc, e);
            return null;
        }
    }
}
