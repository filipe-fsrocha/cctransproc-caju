package br.com.fsrocha.cctransproc.domain.transaction.model.entities;

import br.com.fsrocha.cctransproc.domain.card.entities.AccountEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "transactions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    AccountEntity account;


    @Column(name = "amount")
    BigDecimal amount;

    @Column(name = "created_at")
    LocalDateTime createdAt;

}
