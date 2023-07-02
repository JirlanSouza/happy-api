package com.dev.happyapi.orphanage.services;

import com.dev.happyapi.orphanage.data.models.Orphanage;
import com.dev.happyapi.orphanage.data.models.OrphanageImage;
import com.dev.happyapi.orphanage.data.repositories.OrphanagesRepository;
import com.dev.happyapi.orphanage.dtos.CreateOrphanageDto;
import com.dev.happyapi.orphanage.exceptions.ExistsEntityException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrphanageServices {

    private final OrphanagesRepository repository;

    public OrphanageServices(OrphanagesRepository repository) {
        this.repository = repository;
    }

    public Orphanage createOrphanage(CreateOrphanageDto orphanageData) {
        if (repository.existsByName(orphanageData.name())) {
            throw new ExistsEntityException(
                String.format("The orphanage with name '%s' already exists", orphanageData.name())
            );
        }

        var orphanage = new Orphanage(
                UUID.randomUUID(),
                orphanageData.name(),
                orphanageData.about(),
                orphanageData.latitude(),
                orphanageData.longitude(),
                orphanageData.openingHours(),
                orphanageData.openingOnWeekends(),
                orphanageData.images().stream().map(
                    i -> new OrphanageImage(
                        UUID.randomUUID(),
                        i.name(),
                        i.size(),
                        i.mimeType(),
                        i.url()
                    )
                ).collect(Collectors.toList())

        );

        return  repository.save(orphanage);
    }

    public List<Orphanage> listOrphanages() {
        return repository.findAll();
    }
}
