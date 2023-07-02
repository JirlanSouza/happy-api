package com.dev.happyapi.orphanage.controllers;

import com.dev.happyapi.orphanage.dtos.CreateOrphanageDto;
import com.dev.happyapi.orphanage.presentations.OrphanageViewData;
import com.dev.happyapi.orphanage.services.OrphanageServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orphanages")
@ResponseStatus(HttpStatus.CREATED)
public class OrphanageController {
    private final OrphanageServices orphanageServices;

    public OrphanageController(OrphanageServices orphanageServices) {
        this.orphanageServices = orphanageServices;
    }

    @PostMapping
    OrphanageViewData create(@RequestBody @Valid CreateOrphanageDto orphanageDto) {
        var orphanage = orphanageServices.createOrphanage(orphanageDto);
        return OrphanageViewData.of(orphanage);
    }
}
