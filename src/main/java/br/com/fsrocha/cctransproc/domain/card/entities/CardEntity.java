package br.com.fsrocha.cctransproc.domain.card.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

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
    String expirationDate;

    @Column(name = "ccv")
    String cvc;

    @Column(name = "password")
    String password;

    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    List<AccountEntity> accounts;
}
