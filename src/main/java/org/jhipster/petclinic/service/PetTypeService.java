package org.jhipster.petclinic.service;

import org.jhipster.petclinic.service.dto.PetTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.jhipster.petclinic.domain.PetType}.
 */
public interface PetTypeService {

    /**
     * Save a petType.
     *
     * @param petTypeDTO the entity to save.
     * @return the persisted entity.
     */
    PetTypeDTO save(PetTypeDTO petTypeDTO);

    /**
     * Get all the petTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PetTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" petType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PetTypeDTO> findOne(Long id);

    /**
     * Delete the "id" petType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
