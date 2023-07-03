package com.dev.happyapi.orphanage.data.repositories;

import com.dev.happyapi.orphanage.data.models.Orphanage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrphanagesRepository extends JpaRepository<Orphanage, UUID> {
    boolean existsByName(String name);

    @Query(value = "from Orphanage o join fetch o.images")
    List<Orphanage> findAll();

    @Query(value = "from Orphanage o join fetch o.images where o.id = :id")
    Optional<Orphanage> findById(@Param("id") UUID id);
}
