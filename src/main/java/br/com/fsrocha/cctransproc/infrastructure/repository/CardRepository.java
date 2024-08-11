package br.com.fsrocha.cctransproc.infrastructure.repository;

import br.com.fsrocha.cctransproc.domain.card.entities.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<CardEntity, UUID> {

    Optional<CardEntity> findByCardNumber(String cardNumber);

}
