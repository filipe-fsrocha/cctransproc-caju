package br.com.fsrocha.cctransproc.domain.mcc.repository;

import br.com.fsrocha.cctransproc.domain.mcc.entities.MCCEntity;
import org.springframework.data.domain.Page;

public interface MCCRepositoryService {

    MCCEntity findByMcc(String mcc);

    Page<MCCEntity> listMCCS(int page, int size, String search);

}
