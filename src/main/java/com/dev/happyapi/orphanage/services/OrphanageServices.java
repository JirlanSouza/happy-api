package com.dev.happyapi.orphanage.services;

import com.dev.happyapi.orphanage.data.models.Orphanage;
import com.dev.happyapi.orphanage.data.models.OrphanageImage;
import com.dev.happyapi.orphanage.data.repositories.OrphanagesRepository;
import com.dev.happyapi.orphanage.dtos.CreateOrphanageImageDto;
import com.dev.happyapi.orphanage.exceptions.ExistsEntityException;
import com.dev.happyapi.orphanage.exceptions.NotFoundEntityException;
import com.dev.happyapi.orphanage.mappers.CreateOrphanageImageMapper;
import com.dev.happyapi.orphanage.mappers.CreateOrphanageMapper;
import com.dev.happyapi.orphanage.services.interfaces.CreateOrphanageDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrphanageServices {

    private final OrphanagesRepository repository;
    private final OrphanageImageUploadService imageUploadService;

    public OrphanageServices(OrphanagesRepository repository, OrphanageImageUploadService imageUploadService) {
        this.repository = repository;
        this.imageUploadService = imageUploadService;
    }

    public Orphanage createOrphanage(CreateOrphanageDto orphanageData) {
        if (repository.existsByName(orphanageData.name())) {
            throw new ExistsEntityException(
                    String.format("The orphanage with name '%s' already exists", orphanageData.name())
            );
        }

        Orphanage orphanage = CreateOrphanageMapper.toEntity(orphanageData);

        List<CreateOrphanageImageDto> imagesDto = imageUploadService.saveImages(orphanageData.imagesFiles());
        List<OrphanageImage> orphanageImages = imagesDto.stream().map(
                d -> CreateOrphanageImageMapper.toEntity(d, orphanage.getId())
        ).toList();

        orphanage.addImages(orphanageImages);

        return repository.save(orphanage);
    }

    public List<Orphanage> listOrphanages() {
        return repository.findAll();
    }

    public Orphanage getOrphanage(UUID orphanageId) {
        return repository.findById(orphanageId).orElseThrow(
                () -> new NotFoundEntityException("The orphanage with id '%s' do not found")
        );
    }
}
