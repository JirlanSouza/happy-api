package com.dev.happyapi.orphanage.data.repositories;

import com.dev.happyapi.orphanage.data.models.Orphanage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrphanagesRepository extends JpaRepository<Orphanage, UUID> {
    boolean existsByName(String name);
}
