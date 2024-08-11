package br.com.fsrocha.cctransproc.domain.card.entities;

import br.com.fsrocha.cctransproc.domain.card.AccountType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    CardEntity card;

    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    AccountType accountType;

    @Column(name = "total_amount")
    BigDecimal totalAmount;

}
