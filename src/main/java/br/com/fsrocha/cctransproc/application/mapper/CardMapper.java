package br.com.fsrocha.cctransproc.application.mapper;

import br.com.fsrocha.cctransproc.application.response.CardResponse;
import br.com.fsrocha.cctransproc.domain.card.entities.CardEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardMapper {

    ModelMapper modelMapper;

    public CardResponse toResponse(CardEntity entity) {
        return modelMapper.map(entity, CardResponse.class);
    }

}
