package br.com.fsrocha.cctransproc.domain.card.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "card")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    UUID id;

    @Column(name = "card_number")
    String cardNumber;

    @Column(name = "expiration_date")
    LocalDate expirationDte;

    @Column(name = "ccv")
    int ccv;

    @Column(name = "password")
    String password;

    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER, orphanRemoval = true)
    List<AccountEntity> accounts;
}
