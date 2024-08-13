package br.com.fsrocha.cctransproc.domain.card.service.impl;

import br.com.fsrocha.cctransproc.domain.card.entities.CardEntity;
import br.com.fsrocha.cctransproc.domain.card.password.CardPassword;
import br.com.fsrocha.cctransproc.domain.card.repository.CardRepositoryService;
import br.com.fsrocha.cctransproc.domain.card.service.CardService;
import br.com.fsrocha.cctransproc.domain.transaction.TransactionType;
import br.com.fsrocha.cctransproc.domain.utils.MaskUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardServiceImpl implements CardService {

    CardRepositoryService cardRepositoryService;

    @Override
    public CardEntity getCard(String cardNumber) {
        var cardNumberWithMask = MaskUtils.applyMask(cardNumber);
        return cardRepositoryService.findByCardNumber(cardNumberWithMask);
    }

    @Override
    public boolean isValidCard(TransactionType type, String number, String passwordOrCvc) {
        var numberWithMask = MaskUtils.applyMask(number);

        try {
            var card = cardRepositoryService.findByCardNumber(numberWithMask);
            if (TransactionType.POS == type) {
                return CardPassword.match(passwordOrCvc, card.getPassword());
            }

            return passwordOrCvc != null && passwordOrCvc.equals(card.getCvc());
        } catch (Exception e) {
            return false;
        }
    }

}
