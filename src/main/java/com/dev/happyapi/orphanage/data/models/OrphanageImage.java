package com.dev.happyapi.orphanage.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrphanageImage {
    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private Long size;
    private String mimeType;
    private String url;
}
