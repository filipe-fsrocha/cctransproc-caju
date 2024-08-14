package br.com.fsrocha.cctransproc.application.controller;

import br.com.fsrocha.cctransproc.application.request.TransactionRequest;
import br.com.fsrocha.cctransproc.application.request.record.Account;
import br.com.fsrocha.cctransproc.domain.card.AccountType;
import br.com.fsrocha.cctransproc.domain.card.entities.AccountEntity;
import br.com.fsrocha.cctransproc.domain.card.repository.AccountRepositoryService;
import br.com.fsrocha.cctransproc.domain.merchant.repository.MerchantRepositoryService;
import br.com.fsrocha.cctransproc.domain.transaction.TransactionType;
import br.com.fsrocha.cctransproc.domain.transaction.model.entities.TransactionEntity;
import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.TransactionCode;
import br.com.fsrocha.cctransproc.domain.transaction.repository.TransactionRepositoryService;
import br.com.fsrocha.cctransproc.utils.DatabaseTest;
import br.com.fsrocha.cctransproc.utils.TransactionBuilder;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@FieldDefaults(level = AccessLevel.PRIVATE)
class TransactionControllerMockTest extends DatabaseTest {

    private static final String UBER_EATS = "UBER EATS                  SAO PAULO BR";

    @MockBean
    AccountRepositoryService accountRepositoryService;

    @MockBean
    TransactionRepositoryService transactionRepositoryService;

    @MockBean
    MerchantRepositoryService merchantRepositoryService;

    @Autowired
    TransactionController controller;

    @Test
    @DisplayName("Transação não autorizada devido a conta não encontrada")
    void testTransactionWithAccountNotFound() {
        // Assemble
        var request = createDefaultTransactionRequest(BigDecimal.valueOf(10.0));
        Mockito.when(accountRepositoryService.findAccount(any(), any())).thenReturn(null);

        // Act
        ResponseEntity<TransactionCode> response = controller.transaction(request);

        // Assert
        var result = response.getBody();

        assertNotNull(result);
        assertEquals("07", result.getCode());
        Mockito.verify(accountRepositoryService).findAccount(any(), any());
    }

    @Test
    @DisplayName("Transação não autorizada devido falha no registro da transação")
    void testErrorRegisterTransaction() {
        // Assemble
        var account = new AccountEntity();
        account.setTotalAmount(BigDecimal.valueOf(10.0));
        account.setAccountType(AccountType.FOOD);

        var request = createDefaultTransactionRequest(BigDecimal.valueOf(10.0));

        Mockito.when(accountRepositoryService.findAccount(any(), any())).thenReturn(account);
        Mockito.doThrow(new RuntimeException("Error"))
                .when(transactionRepositoryService).save(any(TransactionEntity.class));

        // Act
        ResponseEntity<TransactionCode> response = controller.transaction(request);

        // Assert
        var result = response.getBody();

        assertNotNull(result);
        assertEquals("07", result.getCode());

        Mockito.verify(transactionRepositoryService).save(any(TransactionEntity.class));
    }

    @Test
    @DisplayName("Transação não autorizada devido falha a recuperar os dados do merchant")
    void testTransactionRejectWithMerchantNotFound() {
        // Assemble
        var account = new AccountEntity();
        account.setTotalAmount(BigDecimal.valueOf(10.0));
        account.setAccountType(AccountType.FOOD);

        var request = createDefaultTransactionRequest(BigDecimal.valueOf(10.0));
        request.setMcc("541ae");

        Mockito.when(merchantRepositoryService.findByName(anyString())).thenThrow(new RuntimeException("Not found"));

        // Act
        ResponseEntity<TransactionCode> response = controller.transaction(request);

        // Assert
        var result = response.getBody();

        assertNotNull(result);
        assertEquals("07", result.getCode());

        Mockito.verify(merchantRepositoryService).findByName(anyString());
    }

    private TransactionRequest createDefaultTransactionRequest(BigDecimal amount) {
        var transaction = TransactionBuilder.builder()
                .totalAmount(amount)
                .mcc("5411")
                .merchant(UBER_EATS)
                .passwordOrCvc("1234")
                .build();
        return buildTransactionRequest(transaction);
    }

    private TransactionRequest buildTransactionRequest(TransactionBuilder builder) {
        var request = new TransactionRequest();
        request.setAccount(buildAccount(builder.getPasswordOrCvc()));
        request.setMcc(builder.getMcc());
        request.setMerchant(builder.getMerchant());
        request.setTotalAmount(builder.getTotalAmount());
        request.setType(TransactionType.POS);
        return request;
    }

    private Account buildAccount(String passwordOrCvc) {
        var account = new Account();
        account.setCardNumber("5513121264313829");
        account.setCvc(passwordOrCvc);
        account.setPassword(passwordOrCvc);
        return account;
    }
}
