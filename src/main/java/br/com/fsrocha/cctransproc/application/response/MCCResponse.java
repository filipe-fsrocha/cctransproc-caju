package br.com.fsrocha.cctransproc.application.response;

import lombok.Data;

import java.util.UUID;

@Data
public class MCCResponse {

    UUID id;
    int mcc;
    String description;
    boolean active;

}
