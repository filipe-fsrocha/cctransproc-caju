package br.com.fsrocha.cctransproc.domain.mcc.model.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Entity
@Table(name = "mcc")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MCCEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    UUID id;

    @Column(name = "mcc", nullable = false)
    int mcc;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "active")
    boolean active;
}
