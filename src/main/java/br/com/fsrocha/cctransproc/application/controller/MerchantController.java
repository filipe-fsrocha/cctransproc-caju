package br.com.fsrocha.cctransproc.application.controller;

import br.com.fsrocha.cctransproc.application.mapper.MerchantMapper;
import br.com.fsrocha.cctransproc.application.response.DataResponse;
import br.com.fsrocha.cctransproc.application.response.MerchantResponse;
import br.com.fsrocha.cctransproc.domain.merchant.service.MerchantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/merchant")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MerchantController {

    MerchantService merchantService;
    MerchantMapper mapper;

    @GetMapping
    public ResponseEntity<DataResponse<MerchantResponse>> merchant(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size,
                                                                   @RequestParam(required = false) String search) {
        var merchants = merchantService.listMerchants(page, size, search);
        return ResponseEntity.ok(mapper.toDataResponse(merchants));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchantResponse> merchant(@PathVariable UUID id) {
        var merchant = merchantService.findById(id);
        return ResponseEntity.ok(mapper.toResponse(merchant));
    }

}
