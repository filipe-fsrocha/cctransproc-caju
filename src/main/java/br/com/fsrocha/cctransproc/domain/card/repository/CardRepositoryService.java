package br.com.fsrocha.cctransproc.domain.card.repository;

import br.com.fsrocha.cctransproc.domain.card.entities.CardEntity;

public interface CardRepositoryService {

    CardEntity findByCardNumber(String cardNumber);

}
