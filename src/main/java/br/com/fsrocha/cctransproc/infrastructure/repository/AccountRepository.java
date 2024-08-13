package br.com.fsrocha.cctransproc.infrastructure.repository;

import br.com.fsrocha.cctransproc.domain.card.AccountType;
import br.com.fsrocha.cctransproc.domain.card.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    @Query("SELECT a FROM AccountEntity a JOIN a.card ac ON ac.id = a.card.id " +
            "   WHERE ac.cardNumber = :cardNumber AND a.accountType = :type")
    AccountEntity findAccount(@Param("cardNumber") String cardNumber, @Param("type") AccountType type);

}
