package br.com.fsrocha.cctransproc.domain.merchant.entities;

import br.com.fsrocha.cctransproc.domain.mcc.entities.MCCEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mcc_id", unique = true)
    @ToString.Exclude
    MCCEntity mcc;

}
