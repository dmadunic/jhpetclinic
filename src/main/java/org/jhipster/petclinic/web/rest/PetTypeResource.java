package org.jhipster.petclinic.web.rest;

import org.jhipster.petclinic.service.PetTypeService;
import org.jhipster.petclinic.web.rest.errors.BadRequestAlertException;
import org.jhipster.petclinic.service.dto.PetTypeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.jhipster.petclinic.domain.PetType}.
 */
@RestController
@RequestMapping("/api")
public class PetTypeResource {

    private final Logger log = LoggerFactory.getLogger(PetTypeResource.class);

    private static final String ENTITY_NAME = "petType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PetTypeService petTypeService;

    public PetTypeResource(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    /**
     * {@code POST  /pet-types} : Create a new petType.
     *
     * @param petTypeDTO the petTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new petTypeDTO, or with status {@code 400 (Bad Request)} if the petType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pet-types")
    public ResponseEntity<PetTypeDTO> createPetType(@Valid @RequestBody PetTypeDTO petTypeDTO) throws URISyntaxException {
        log.debug("REST request to save PetType : {}", petTypeDTO);
        if (petTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new petType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PetTypeDTO result = petTypeService.save(petTypeDTO);
        return ResponseEntity.created(new URI("/api/pet-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pet-types} : Updates an existing petType.
     *
     * @param petTypeDTO the petTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated petTypeDTO,
     * or with status {@code 400 (Bad Request)} if the petTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the petTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pet-types")
    public ResponseEntity<PetTypeDTO> updatePetType(@Valid @RequestBody PetTypeDTO petTypeDTO) throws URISyntaxException {
        log.debug("REST request to update PetType : {}", petTypeDTO);
        if (petTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PetTypeDTO result = petTypeService.save(petTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, petTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pet-types} : get all the petTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of petTypes in body.
     */
    @GetMapping("/pet-types")
    public ResponseEntity<List<PetTypeDTO>> getAllPetTypes(Pageable pageable) {
        log.debug("REST request to get a page of PetTypes");
        Page<PetTypeDTO> page = petTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pet-types/:id} : get the "id" petType.
     *
     * @param id the id of the petTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the petTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pet-types/{id}")
    public ResponseEntity<PetTypeDTO> getPetType(@PathVariable Long id) {
        log.debug("REST request to get PetType : {}", id);
        Optional<PetTypeDTO> petTypeDTO = petTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(petTypeDTO);
    }

    /**
     * {@code DELETE  /pet-types/:id} : delete the "id" petType.
     *
     * @param id the id of the petTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pet-types/{id}")
    public ResponseEntity<Void> deletePetType(@PathVariable Long id) {
        log.debug("REST request to delete PetType : {}", id);
        petTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
