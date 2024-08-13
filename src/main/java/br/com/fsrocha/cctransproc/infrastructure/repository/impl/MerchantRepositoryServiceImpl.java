package br.com.fsrocha.cctransproc.infrastructure.repository.impl;

import br.com.fsrocha.cctransproc.domain.merchant.entities.MerchantEntity;
import br.com.fsrocha.cctransproc.domain.merchant.repository.MerchantRepositoryService;
import br.com.fsrocha.cctransproc.domain.error.ServiceException;
import br.com.fsrocha.cctransproc.infrastructure.repository.MerchantRepository;
import br.com.fsrocha.cctransproc.infrastructure.repository.utils.PaginateUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MerchantRepositoryServiceImpl implements MerchantRepositoryService {

    MerchantRepository merchantRepository;

    @Override
    public MerchantEntity finById(UUID id) {
        return merchantRepository.findById(id)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, String.format("Merchant %s not found.", id)));
    }

    @Override
    public MerchantEntity findByName(String name) {
        return merchantRepository.findByName(name);
    }

    @Override
    public Page<MerchantEntity> listMerchants(int page, int size, String search) {
        var pageRequest = PaginateUtils.doPaginate(page, size);

        if (Strings.isNotBlank(search)) {
            search = "%" + search.toLowerCase() + "%";
        }
        return merchantRepository.listMerchant(search, pageRequest);
    }
}
