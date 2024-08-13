package br.com.fsrocha.cctransproc.domain.merchant.repository;

import br.com.fsrocha.cctransproc.domain.merchant.entities.MerchantEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface MerchantRepositoryService {

    MerchantEntity finById(UUID id);

    MerchantEntity findByName(String name);

    Page<MerchantEntity> listMerchants(int page, int size, String search);

}
