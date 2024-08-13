package br.com.fsrocha.cctransproc.domain.card.service;

import br.com.fsrocha.cctransproc.domain.card.entities.CardEntity;
import br.com.fsrocha.cctransproc.domain.transaction.TransactionType;

public interface CardService {

    CardEntity getCard(String cardNumber);

    boolean isValidCard(TransactionType type, String number, String passwordOrCvc);

}
