package br.com.fsrocha.cctransproc.application.controller;

import br.com.fsrocha.cctransproc.application.request.TransactionRequest;
import br.com.fsrocha.cctransproc.application.request.record.Account;
import br.com.fsrocha.cctransproc.domain.card.AccountType;
import br.com.fsrocha.cctransproc.domain.transaction.TransactionType;
import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.TransactionCode;
import br.com.fsrocha.cctransproc.infrastructure.repository.AccountRepository;
import br.com.fsrocha.cctransproc.infrastructure.repository.TransactionRepository;
import br.com.fsrocha.cctransproc.utils.DatabaseTest;
import br.com.fsrocha.cctransproc.utils.TransactionBuilder;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class TransactionControllerTest extends DatabaseTest {
    private static final String CARD_NUMBER = "5513 1212 6431 3829";
    private static final String AUTHORIZED_CODE = "00";
    private static final String REJECTED_CODE = "51";
    private static final String UNAUTHORIZED = "07";

    private static final String UBER_EATS = "UBER EATS                  SAO PAULO BR";
    private static final String PAG_JOSE_DA_SILVA = "PAG*JoseDaSilva         RIO DE JANEI BR";

    private static final BigDecimal BALANCE_FOOD = new BigDecimal(100);
    private static final BigDecimal BALANCE_MEAL = new BigDecimal(100);
    private static final BigDecimal BALANCE_CASH = new BigDecimal(200);

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionController controller;

    @Test
    @DisplayName("[MCC-5411] - Verificar se o sistema retorna 'code: 00' para uma transação que foi aprovada com sucesso")
    void testTransactionSuccessFood() {
        // Assemble
        var request = createDefaultTransactionRequest(BigDecimal.valueOf(10.0));

        // Act
        ResponseEntity<TransactionCode> response = controller.transaction(request);

        // Assert
        var result = response.getBody();
        var expectedAccountFood = accountRepository.findAccount(CARD_NUMBER, AccountType.FOOD);
        var expectedAccountMeal = accountRepository.findAccount(CARD_NUMBER, AccountType.MEAL);
        var expectedAccountCash = accountRepository.findAccount(CARD_NUMBER, AccountType.CASH);
        var expectedTransactional = transactionRepository.findAll()
                .stream().findFirst();

        assertNotNull(result);
        assertEquals(AUTHORIZED_CODE, result.getCode());
        assertEquals(0, expectedAccountFood.getTotalAmount().compareTo(BigDecimal.valueOf(90)));
        assertEquals(0, expectedAccountMeal.getTotalAmount().compareTo(BALANCE_MEAL));
        assertEquals(0, expectedAccountCash.getTotalAmount().compareTo(BALANCE_CASH));
        assertTrue(expectedTransactional.isPresent());
        assertEquals(0, expectedTransactional.get().getAmount().compareTo(BigDecimal.valueOf(10.0)));
    }

    @Test
    @DisplayName("[MCC-5411] - Verificar se o sistema retorna 'code: 00' para uma transação que foi aprovada na conta CASH")
    void testTransactionSuccessFoodWithFallbackToCash() {
        // Assemble
        var request = createDefaultTransactionRequest(BigDecimal.valueOf(110.0));

        // Act
        ResponseEntity<TransactionCode> response = controller.transaction(request);

        // Assert
        var result = response.getBody();
        var expectedAccountCash = accountRepository.findAccount(CARD_NUMBER, AccountType.CASH);
        var expectedAccountFood = accountRepository.findAccount(CARD_NUMBER, AccountType.FOOD);
        var expectedAccountMeal = accountRepository.findAccount(CARD_NUMBER, AccountType.MEAL);
        var expectedTransactional = transactionRepository.findAll()
                .stream().findFirst();

        assertNotNull(result);
        assertEquals(AUTHORIZED_CODE, result.getCode());
        assertEquals(0, expectedAccountCash.getTotalAmount().compareTo(BigDecimal.valueOf(90)));
        assertEquals(0, expectedAccountFood.getTotalAmount().compareTo(BALANCE_FOOD));
        assertEquals(0, expectedAccountMeal.getTotalAmount().compareTo(BALANCE_MEAL));
        assertTrue(expectedTransactional.isPresent());
        assertEquals(0, expectedTransactional.get().getAmount().compareTo(BigDecimal.valueOf(110.0)));
    }

    @Test
    @DisplayName("[MCC-5411] - Verificar se o sistema retorna 'code: 51' para uma transação rejeitada devido a saldo insuficiente")
    void testTransactionRejectedWithInsufficientBalanceFood() {
        // Assemble
        var request = createDefaultTransactionRequest(BigDecimal.valueOf(220.0));

        // Act
        ResponseEntity<TransactionCode> response = controller.transaction(request);

        // Assert
        var result = response.getBody();
        var expectedAccountFood = accountRepository.findAccount(CARD_NUMBER, AccountType.FOOD);
        var expectedAccountMeal = accountRepository.findAccount(CARD_NUMBER, AccountType.MEAL);
        var expectedAccountCash = accountRepository.findAccount(CARD_NUMBER, AccountType.CASH);
        var expectedTransactional = transactionRepository.findAll()
                .stream().findFirst();

        assertNotNull(result);
        assertEquals(REJECTED_CODE, result.getCode());
        assertEquals(0, expectedAccountFood.getTotalAmount().compareTo(BALANCE_FOOD));
        assertEquals(0, expectedAccountMeal.getTotalAmount().compareTo(BALANCE_MEAL));
        assertEquals(0, expectedAccountCash.getTotalAmount().compareTo(BALANCE_CASH));
        assertTrue(expectedTransactional.isEmpty());
    }

    @Test
    @DisplayName("[MCC-5411] - Verificar se o sistema retorna 'code: 07' para uma transação não authorizada")
    void testTransactionUnauthorizedAnyReason() {
        // Assemble
        var transaction = TransactionBuilder.builder()
                .totalAmount(BigDecimal.valueOf(10.0))
                .mcc("5411")
                .merchant(UBER_EATS)
                .passwordOrCvc("123")
                .build();

        var request = buildTransactionRequest(transaction, TransactionType.POS);

        // Act
        ResponseEntity<TransactionCode> response = controller.transaction(request);

        // Assert
        var result = response.getBody();
        var expectedAccountFood = accountRepository.findAccount(CARD_NUMBER, AccountType.FOOD);
        var expectedAccountMeal = accountRepository.findAccount(CARD_NUMBER, AccountType.MEAL);
        var expectedAccountCash = accountRepository.findAccount(CARD_NUMBER, AccountType.CASH);
        var expectedTransactional = transactionRepository.findAll()
                .stream().findFirst();

        assertNotNull(result);
        assertEquals(UNAUTHORIZED, result.getCode());
        assertEquals(0, expectedAccountFood.getTotalAmount().compareTo(BALANCE_FOOD));
        assertEquals(0, expectedAccountMeal.getTotalAmount().compareTo(BALANCE_MEAL));
        assertEquals(0, expectedAccountCash.getTotalAmount().compareTo(BALANCE_CASH));
        assertTrue(expectedTransactional.isEmpty());
    }

    @Test
    @DisplayName("[MCC-5811] - Verificar se o sistema retorna 'code: 00' para uma transação que foi aprovada com sucesso")
    void testTransactionSuccessMeal() {
        // Assemble
        var transaction = TransactionBuilder.builder()
                .totalAmount(BigDecimal.valueOf(10.0))
                .mcc("5811")
                .passwordOrCvc("610")
                .merchant(PAG_JOSE_DA_SILVA)
                .build();

        var request = buildTransactionRequest(transaction, TransactionType.ECOMMERCE);

        // Act
        ResponseEntity<TransactionCode> response = controller.transaction(request);

        // Assert
        var result = response.getBody();
        var expectedAccountFood = accountRepository.findAccount(CARD_NUMBER, AccountType.FOOD);
        var expectedAccountMeal = accountRepository.findAccount(CARD_NUMBER, AccountType.MEAL);
        var expectedAccountCash = accountRepository.findAccount(CARD_NUMBER, AccountType.CASH);
        var expectedTransactional = transactionRepository.findAll()
                .stream().findFirst();

        assertNotNull(result);
        assertEquals(AUTHORIZED_CODE, result.getCode());
        assertEquals(0, expectedAccountFood.getTotalAmount().compareTo(BALANCE_FOOD));
        assertEquals(0, expectedAccountMeal.getTotalAmount().compareTo(BigDecimal.valueOf(90)));
        assertEquals(0, expectedAccountCash.getTotalAmount().compareTo(BALANCE_CASH));
        assertTrue(expectedTransactional.isPresent());
        assertEquals(0, expectedTransactional.get().getAmount().compareTo(BigDecimal.valueOf(10)));
    }

    @Test
    @DisplayName("[MCC-0000] - Deve aprovar a transação com base no MCC do merchant")
    void testTransactionWithInvalidMCC() {
        // Assemble
        var transaction = TransactionBuilder.builder()
                .totalAmount(BigDecimal.valueOf(10.0))
                .mcc("541a")
                .passwordOrCvc("1234")
                .merchant(PAG_JOSE_DA_SILVA)
                .build();

        var request = buildTransactionRequest(transaction, TransactionType.POS);

        // Act
        ResponseEntity<TransactionCode> response = controller.transaction(request);

        // Assert
        var result = response.getBody();
        var expectedAccountFood = accountRepository.findAccount(CARD_NUMBER, AccountType.FOOD);
        var expectedAccountMeal = accountRepository.findAccount(CARD_NUMBER, AccountType.MEAL);
        var expectedAccountCash = accountRepository.findAccount(CARD_NUMBER, AccountType.CASH);
        var expectedTransactional = transactionRepository.findAll()
                .stream().findFirst();

        assertNotNull(result);
        assertEquals(AUTHORIZED_CODE, result.getCode());
        assertEquals(0, expectedAccountFood.getTotalAmount().compareTo(BigDecimal.valueOf(90)));
        assertEquals(0, expectedAccountMeal.getTotalAmount().compareTo(BALANCE_MEAL));
        assertEquals(0, expectedAccountCash.getTotalAmount().compareTo(BALANCE_CASH));
        assertTrue(expectedTransactional.isPresent());
        assertEquals(0, expectedTransactional.get().getAmount().compareTo(BigDecimal.valueOf(10)));
    }

    @Test
    @DisplayName("[MCC - 4000] - Deve aprovar a transação no conta cash")
    void testTransactionWithAnyMCC() {
        // Assemble
        var transaction = TransactionBuilder.builder()
                .totalAmount(BigDecimal.valueOf(15.80))
                .mcc("4000")
                .passwordOrCvc("1234")
                .merchant("UBER TRIP                  SAO PAULO BR")
                .build();

        var request = buildTransactionRequest(transaction, TransactionType.POS);

        // Act
        ResponseEntity<TransactionCode> response = controller.transaction(request);

        // Assert
        var result = response.getBody();
        var expectedAccountFood = accountRepository.findAccount(CARD_NUMBER, AccountType.FOOD);
        var expectedAccountMeal = accountRepository.findAccount(CARD_NUMBER, AccountType.MEAL);
        var expectedAccountCash = accountRepository.findAccount(CARD_NUMBER, AccountType.CASH);
        var expectedTransactional = transactionRepository.findAll()
                .stream().findFirst();

        assertNotNull(result);
        assertEquals(AUTHORIZED_CODE, result.getCode());
        assertEquals(0, expectedAccountFood.getTotalAmount().compareTo(BALANCE_FOOD));
        assertEquals(0, expectedAccountMeal.getTotalAmount().compareTo(BALANCE_MEAL));
        assertEquals(0, expectedAccountCash.getTotalAmount().compareTo(BigDecimal.valueOf(184.20)));
        assertTrue(expectedTransactional.isPresent());
        assertEquals(0, expectedTransactional.get().getAmount().compareTo(BigDecimal.valueOf(15.80)));
    }

    @Test
    @DisplayName("[MCC-51af] - Verificar se o sistema retorna 'code: 00' para uma transação com mcc inválido, onde deve ser considrado o mcc do merchant")
    void transactionApprovedWithInvalidMccDoingByPassMerchant() {
        // Assemble
        var transaction = TransactionBuilder.builder()
                .totalAmount(BigDecimal.valueOf(15.80))
                .mcc("51af")
                .passwordOrCvc("1234")
                .merchant("UBER TRIP                  SAO PAULO BR")
                .build();

        var request = buildTransactionRequest(transaction, TransactionType.POS);

        // Act
        ResponseEntity<TransactionCode> response = controller.transaction(request);

        // Assert
        var result = response.getBody();
        var expectedAccountFood = accountRepository.findAccount(CARD_NUMBER, AccountType.FOOD);
        var expectedAccountMeal = accountRepository.findAccount(CARD_NUMBER, AccountType.MEAL);
        var expectedAccountCash = accountRepository.findAccount(CARD_NUMBER, AccountType.CASH);
        var expectedTransactional = transactionRepository.findAll()
                .stream().findFirst();

        assertNotNull(result);
        assertEquals(AUTHORIZED_CODE, result.getCode());
        assertEquals(0, expectedAccountFood.getTotalAmount().compareTo(BALANCE_FOOD));
        assertEquals(0, expectedAccountMeal.getTotalAmount().compareTo(BALANCE_MEAL));
        assertEquals(0, expectedAccountCash.getTotalAmount().compareTo(BigDecimal.valueOf(184.20)));
        assertTrue(expectedTransactional.isPresent());
        assertEquals(0, expectedTransactional.get().getAmount().compareTo(BigDecimal.valueOf(15.80)));

    }

    private TransactionRequest createDefaultTransactionRequest(BigDecimal amount) {
        var transaction = TransactionBuilder.builder()
                .totalAmount(amount)
                .mcc("5411")
                .merchant(UBER_EATS)
                .passwordOrCvc("1234")
                .build();
        return buildTransactionRequest(transaction, TransactionType.POS);
    }

    private TransactionRequest buildTransactionRequest(TransactionBuilder builder, TransactionType type) {
        var request = new TransactionRequest();
        request.setAccount(buildAccount(builder.getPasswordOrCvc()));
        request.setMcc(builder.getMcc());
        request.setMerchant(builder.getMerchant());
        request.setTotalAmount(builder.getTotalAmount());
        request.setType(type);
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
