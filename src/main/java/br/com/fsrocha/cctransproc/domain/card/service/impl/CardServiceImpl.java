package br.com.fsrocha.cctransproc.domain.card.service.impl;

import br.com.fsrocha.cctransproc.domain.card.entities.CardEntity;
import br.com.fsrocha.cctransproc.domain.card.repository.CardRepositoryService;
import br.com.fsrocha.cctransproc.domain.card.service.CardService;
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
        var cardNumberWithMask = cardNumber.replaceAll("(.{4})", "$1 ").trim();
        return cardRepositoryService.findByCardNumber(cardNumberWithMask);
    }
}
