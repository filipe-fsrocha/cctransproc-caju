package br.com.fsrocha.cctransproc.domain.transaction.repository;

import br.com.fsrocha.cctransproc.domain.transaction.model.entities.TransactionEntity;

public interface TransactionRepositoryService {

    void save(TransactionEntity entity);

}
