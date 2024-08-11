package br.com.fsrocha.cctransproc.domain.company.service.impl;

import br.com.fsrocha.cctransproc.domain.company.entities.MerchantEntity;
import br.com.fsrocha.cctransproc.domain.company.repository.MerchantRepositoryService;
import br.com.fsrocha.cctransproc.domain.company.service.MerchantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MerchantServiceImpl implements MerchantService {

    MerchantRepositoryService merchantRepositoryService;

    @Override
    public MerchantEntity findById(UUID id) {
        return merchantRepositoryService.finById(id);
    }

    @Override
    public Page<MerchantEntity> listMerchants(int page, int size, String search) {
        return merchantRepositoryService.listMerchants(page, size, search);
    }
}
