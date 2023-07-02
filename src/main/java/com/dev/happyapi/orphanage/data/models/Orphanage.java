package com.dev.happyapi.orphanage.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity()
@Table(name = "orphanages")
@Data
@AllArgsConstructor()
@NoArgsConstructor()
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Orphanage {
    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private String about;
    private Float latitude;
    private Float longitude;
    private String openingHours;
    private boolean openingOnWeekends;

    @OneToMany(mappedBy = "orphanageId", cascade = CascadeType.ALL)
    private List<OrphanageImage> images;
}
