package org.jhipster.petclinic.service;

import org.jhipster.petclinic.service.dto.VetDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.jhipster.petclinic.domain.Vet}.
 */
public interface VetService {

    /**
     * Save a vet.
     *
     * @param vetDTO the entity to save.
     * @return the persisted entity.
     */
    VetDTO save(VetDTO vetDTO);

    /**
     * Get all the vets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VetDTO> findAll(Pageable pageable);

    /**
     * Get all the vets with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<VetDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" vet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VetDTO> findOne(Long id);

    /**
     * Delete the "id" vet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
