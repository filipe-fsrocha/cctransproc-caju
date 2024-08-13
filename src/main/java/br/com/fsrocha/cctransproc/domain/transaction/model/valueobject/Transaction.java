package br.com.fsrocha.cctransproc.domain.transaction.model.valueobject;

import br.com.fsrocha.cctransproc.domain.card.AccountType;
import br.com.fsrocha.cctransproc.domain.card.entities.AccountEntity;
import br.com.fsrocha.cctransproc.domain.transaction.TransactionType;
import br.com.fsrocha.cctransproc.domain.transaction.model.entities.TransactionEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction {

    Account account;
    BigDecimal totalAmount;
    String mcc;
    String merchant;
    TransactionType type;
    AccountType targetAccountType;
    AccountEntity accountId;

    public TransactionEntity toEntity() {
        var entity = new TransactionEntity();
        entity.setAccount(accountId);
        entity.setAmount(totalAmount);
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }
}
