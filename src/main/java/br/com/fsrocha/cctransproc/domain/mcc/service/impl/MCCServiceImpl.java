package br.com.fsrocha.cctransproc.domain.mcc.service.impl;

import br.com.fsrocha.cctransproc.domain.mcc.entities.MCCEntity;
import br.com.fsrocha.cctransproc.domain.mcc.repository.MCCRepositoryService;
import br.com.fsrocha.cctransproc.domain.mcc.service.MCCService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MCCServiceImpl implements MCCService {

    MCCRepositoryService mccRepositoryService;

    @Override
    public MCCEntity findByMcc(String mcc) {
        return mccRepositoryService.findByMcc(mcc);
    }

    @Override
    public Page<MCCEntity> listMCCS(int page, int size, String search) {
        return mccRepositoryService.listMCCS(page, size, search);
    }
}
