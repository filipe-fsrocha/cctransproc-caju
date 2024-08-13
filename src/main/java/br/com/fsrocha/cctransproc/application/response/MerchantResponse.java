package br.com.fsrocha.cctransproc.application.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MerchantResponse {

    UUID id;
    String name;
    LocalDateTime createdAt;
    MCCResponse mcc;

}
