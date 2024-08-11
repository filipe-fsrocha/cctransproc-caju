package br.com.fsrocha.cctransproc.application.controller;

import br.com.fsrocha.cctransproc.application.mapper.CardMapper;
import br.com.fsrocha.cctransproc.application.response.CardResponse;
import br.com.fsrocha.cctransproc.domain.card.service.CardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardController {

    CardService cardService;
    CardMapper mapper;

    @GetMapping("/{cardNumber}")
    public ResponseEntity<CardResponse> card(@PathVariable String cardNumber) {
        var card = cardService.getCard(cardNumber);
        return ResponseEntity.ok().body(mapper.toResponse(card));
    }
}
