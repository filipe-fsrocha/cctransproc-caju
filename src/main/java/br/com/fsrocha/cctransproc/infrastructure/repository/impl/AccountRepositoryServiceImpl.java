package br.com.fsrocha.cctransproc.infrastructure.repository.impl;

import br.com.fsrocha.cctransproc.domain.card.AccountType;
import br.com.fsrocha.cctransproc.domain.card.entities.AccountEntity;
import br.com.fsrocha.cctransproc.domain.card.repository.AccountRepositoryService;
import br.com.fsrocha.cctransproc.infrastructure.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountRepositoryServiceImpl implements AccountRepositoryService {

    AccountRepository accountRepository;

    @Override
    public void save(AccountEntity account) {
        accountRepository.save(account);
    }

    @Override
    public AccountEntity findAccount(String cardNumber, AccountType type) {
        return accountRepository.findAccount(cardNumber, type);
    }
}
