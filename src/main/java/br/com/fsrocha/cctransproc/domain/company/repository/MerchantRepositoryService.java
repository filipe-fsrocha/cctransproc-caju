package br.com.fsrocha.cctransproc.domain.company.repository;

import br.com.fsrocha.cctransproc.domain.company.entities.MerchantEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface MerchantRepositoryService {

    MerchantEntity finById(UUID id);

    Page<MerchantEntity> listMerchants(int page, int size, String search);

}
