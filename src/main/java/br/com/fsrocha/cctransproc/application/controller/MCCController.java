package br.com.fsrocha.cctransproc.application.controller;

import br.com.fsrocha.cctransproc.application.mapper.MCCMapper;
import br.com.fsrocha.cctransproc.application.response.DataResponse;
import br.com.fsrocha.cctransproc.application.response.MCCResponse;
import br.com.fsrocha.cctransproc.domain.mcc.service.MCCService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mcc")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MCCController {

    MCCService mccService;
    MCCMapper mccMapper;

    @GetMapping
    public ResponseEntity<DataResponse<MCCResponse>> mcc(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam(required = false) String search) {
        var mccPage = mccService.listMCCS(page, size, search);
        return ResponseEntity.ok(mccMapper.toResponseData(mccPage));
    }

    @GetMapping("/{mcc}")
    public ResponseEntity<MCCResponse> mcc(@PathVariable int mcc) {
        var mccEntity = mccService.findByMcc(mcc);
        return ResponseEntity.ok(mccMapper.toResponse(mccEntity));
    }

}
