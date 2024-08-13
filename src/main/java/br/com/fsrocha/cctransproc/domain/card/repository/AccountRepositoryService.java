package br.com.fsrocha.cctransproc.domain.card.repository;

import br.com.fsrocha.cctransproc.domain.card.AccountType;
import br.com.fsrocha.cctransproc.domain.card.entities.AccountEntity;

public interface AccountRepositoryService {

    void save(AccountEntity account);

    AccountEntity findAccount(String cardNumber, AccountType type);

}
