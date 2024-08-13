package br.com.fsrocha.cctransproc.domain.mcc.service;

import br.com.fsrocha.cctransproc.domain.mcc.entities.MCCEntity;
import org.springframework.data.domain.Page;

public interface MCCService {

    MCCEntity findByMcc(int mcc);

    Page<MCCEntity> listMCCS(int page, int size, String search);

}
