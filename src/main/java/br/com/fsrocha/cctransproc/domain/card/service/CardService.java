package br.com.fsrocha.cctransproc.domain.card.service;

import br.com.fsrocha.cctransproc.domain.card.entities.CardEntity;

public interface CardService {

    CardEntity getCard(String cardNumber);

}
