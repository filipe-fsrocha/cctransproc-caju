package br.com.fsrocha.cctransproc.infrastructure.repository.impl;

import br.com.fsrocha.cctransproc.domain.transaction.model.entities.TransactionEntity;
import br.com.fsrocha.cctransproc.domain.transaction.repository.TransactionRepositoryService;
import br.com.fsrocha.cctransproc.infrastructure.repository.TransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionRepositoryServiceImpl implements TransactionRepositoryService {

    TransactionRepository transactionRepository;

    @Override
    public void save(TransactionEntity entity) {
        transactionRepository.save(entity);
    }
}
