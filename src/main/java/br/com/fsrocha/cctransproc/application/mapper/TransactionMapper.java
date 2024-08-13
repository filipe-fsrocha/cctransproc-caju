package br.com.fsrocha.cctransproc.application.mapper;

import br.com.fsrocha.cctransproc.application.request.TransactionRequest;
import br.com.fsrocha.cctransproc.domain.transaction.model.valueobject.Transaction;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionMapper {

    ModelMapper modelMapper;

    public Transaction toModel(TransactionRequest request) {
        return modelMapper.map(request, Transaction.class);
    }

}
