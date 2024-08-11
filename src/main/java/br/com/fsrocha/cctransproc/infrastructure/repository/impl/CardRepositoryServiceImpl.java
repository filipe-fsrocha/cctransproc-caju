package br.com.fsrocha.cctransproc.infrastructure.repository.impl;

import br.com.fsrocha.cctransproc.domain.card.entities.CardEntity;
import br.com.fsrocha.cctransproc.domain.card.repository.CardRepositoryService;
import br.com.fsrocha.cctransproc.domain.error.ServiceException;
import br.com.fsrocha.cctransproc.infrastructure.repository.CardRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardRepositoryServiceImpl implements CardRepositoryService {

    CardRepository cardRepository;

    @Override
    public CardEntity findByCardNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, String.format("Card %s not found.", cardNumber)));
    }
}
