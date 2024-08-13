package br.com.fsrocha.cctransproc.infrastructure.repository;

import br.com.fsrocha.cctransproc.domain.transaction.model.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
}
