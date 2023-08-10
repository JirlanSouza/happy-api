package com.dev.happyapi.orphanage.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity()
@Table(name = "orphanages")
@Data
@RequiredArgsConstructor()
@NoArgsConstructor()
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Orphanage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @NonNull
    private String name;

    @NonNull
    private String about;

    @NonNull
    private String instructions;

    @NonNull
    private Double latitude;

    @NonNull
    private Double longitude;

    @NonNull
    private String openingHours;

    @NonNull
    private boolean openOnWeekends;

    @OneToMany(mappedBy = "orphanageId", cascade = CascadeType.ALL)
    private List<OrphanageImage> images;

    public void addImages(List<OrphanageImage> images) {
        this.images = images;
    }
}
