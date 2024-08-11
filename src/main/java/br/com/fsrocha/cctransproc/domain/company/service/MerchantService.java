package br.com.fsrocha.cctransproc.domain.company.service;

import br.com.fsrocha.cctransproc.domain.company.entities.MerchantEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface MerchantService {

    MerchantEntity findById(UUID id);

    Page<MerchantEntity> listMerchants(int page, int size, String search);

}
