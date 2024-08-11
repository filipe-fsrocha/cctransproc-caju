package br.com.fsrocha.cctransproc.domain.company.entities;

import br.com.fsrocha.cctransproc.domain.mcc.model.entities.MCCEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "merchant")
public class MerchantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    UUID id;

    @Column(name = "name")
    String name;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "merchant_mcc",
            joinColumns = @JoinColumn(name = "merchant_id"),
            inverseJoinColumns = @JoinColumn(name = "mcc_id")
    )
    List<MCCEntity> mcc;
}
