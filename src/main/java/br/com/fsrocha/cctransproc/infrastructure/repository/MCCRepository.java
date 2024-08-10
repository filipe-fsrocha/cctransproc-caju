package br.com.fsrocha.cctransproc.infrastructure.repository;

import br.com.fsrocha.cctransproc.domain.mcc.model.entities.MCCEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface MCCRepository extends JpaRepository<MCCEntity, UUID> {

    @Query("select m from MCCEntity m where :search is null or lower(m.description) like :search")
    Page<MCCEntity> listMCCS(@Param("search") String search, Pageable pageable);

    Optional<MCCEntity> findByMcc(int mcc);

}
