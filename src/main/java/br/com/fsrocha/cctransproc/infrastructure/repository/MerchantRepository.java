package br.com.fsrocha.cctransproc.infrastructure.repository;

import br.com.fsrocha.cctransproc.domain.company.entities.MerchantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface MerchantRepository extends JpaRepository<MerchantEntity, UUID> {

    @Query("select m from MerchantEntity m where :search is null or lower(m.name) like :search")
    Page<MerchantEntity> listMerchant(@Param("search") String search, Pageable pageable);

}
