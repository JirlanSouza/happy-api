package com.dev.happyapi.orphanage.controllers;

import com.dev.happyapi.orphanage.dtos.CreateOrphanageDto;
import com.dev.happyapi.orphanage.presentations.OrphanageViewData;
import com.dev.happyapi.orphanage.services.OrphanageServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orphanages")
public class OrphanageController {
    private final OrphanageServices orphanageServices;

    public OrphanageController(OrphanageServices orphanageServices) {
        this.orphanageServices = orphanageServices;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    OrphanageViewData create(@RequestBody @Valid CreateOrphanageDto orphanageDto) {
        var orphanage = orphanageServices.createOrphanage(orphanageDto);
        return OrphanageViewData.of(orphanage);
    }

    @GetMapping
    List<OrphanageViewData> list() {
        return orphanageServices.listOrphanages().stream().map(OrphanageViewData::of).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    OrphanageViewData get(@PathVariable UUID id) {
        var orphanage = orphanageServices.getOrphanage(id);
        return OrphanageViewData.of(orphanage);
    }
}
