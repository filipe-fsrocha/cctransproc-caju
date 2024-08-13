package br.com.fsrocha.cctransproc.application.controller;

import br.com.fsrocha.cctransproc.application.mapper.TransactionMapper;
import br.com.fsrocha.cctransproc.application.request.TransactionRequest;
import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.TransactionCode;
import br.com.fsrocha.cctransproc.domain.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController {

    TransactionMapper mapper;
    TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionCode> transaction(@Valid @RequestBody TransactionRequest request) {
        var transaction = mapper.toModel(request);
        return ResponseEntity.ok(transactionService.executePayment(transaction));
    }

}
