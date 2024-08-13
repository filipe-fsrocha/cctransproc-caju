package br.com.fsrocha.cctransproc.infrastructure.repository.impl;

import br.com.fsrocha.cctransproc.domain.error.ServiceException;
import br.com.fsrocha.cctransproc.domain.mcc.entities.MCCEntity;
import br.com.fsrocha.cctransproc.domain.mcc.repository.MCCRepositoryService;
import br.com.fsrocha.cctransproc.infrastructure.repository.MCCRepository;
import br.com.fsrocha.cctransproc.infrastructure.repository.utils.PaginateUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MCCRepositoryServiceImpl implements MCCRepositoryService {

    MCCRepository mccRepository;

    @Override
    public MCCEntity findByMcc(int mcc) {
        return mccRepository.findByMcc(mcc)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, String.format("MCC %s not found.", mcc)));
    }

    @Override
    public Page<MCCEntity> listMCCS(int page, int size, String search) {
        var pageRequest = PaginateUtils.doPaginate(page, size);

        if (Strings.isNotBlank(search)) {
            search = "%" + search.toLowerCase() + "%";
        }
        return mccRepository.listMCCS(search, pageRequest);
    }


}
