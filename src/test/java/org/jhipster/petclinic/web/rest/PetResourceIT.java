package org.jhipster.petclinic.web.rest;

import org.jhipster.petclinic.JhpetclinicApp;
import org.jhipster.petclinic.domain.Pet;
import org.jhipster.petclinic.domain.PetType;
import org.jhipster.petclinic.domain.Owner;
import org.jhipster.petclinic.repository.PetRepository;
import org.jhipster.petclinic.service.PetService;
import org.jhipster.petclinic.service.dto.PetDTO;
import org.jhipster.petclinic.service.mapper.PetMapper;
import org.jhipster.petclinic.service.dto.PetCriteria;
import org.jhipster.petclinic.service.PetQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PetResource} REST controller.
 */
@SpringBootTest(classes = JhpetclinicApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BIRTH_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetMapper petMapper;

    @Autowired
    private PetService petService;

    @Autowired
    private PetQueryService petQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPetMockMvc;

    private Pet pet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pet createEntity(EntityManager em) {
        Pet pet = new Pet()
            .name(DEFAULT_NAME)
            .birthDate(DEFAULT_BIRTH_DATE);
        return pet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pet createUpdatedEntity(EntityManager em) {
        Pet pet = new Pet()
            .name(UPDATED_NAME)
            .birthDate(UPDATED_BIRTH_DATE);
        return pet;
    }

    @BeforeEach
    public void initTest() {
        pet = createEntity(em);
    }

    @Test
    @Transactional
    public void createPet() throws Exception {
        int databaseSizeBeforeCreate = petRepository.findAll().size();
        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);
        restPetMockMvc.perform(post("/api/pets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(petDTO)))
            .andExpect(status().isCreated());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeCreate + 1);
        Pet testPet = petList.get(petList.size() - 1);
        assertThat(testPet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPet.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void createPetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = petRepository.findAll().size();

        // Create the Pet with an existing ID
        pet.setId(1L);
        PetDTO petDTO = petMapper.toDto(pet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPetMockMvc.perform(post("/api/pets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(petDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = petRepository.findAll().size();
        // set the field null
        pet.setName(null);

        // Create the Pet, which fails.
        PetDTO petDTO = petMapper.toDto(pet);


        restPetMockMvc.perform(post("/api/pets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(petDTO)))
            .andExpect(status().isBadRequest());

        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPets() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the petList
        restPetMockMvc.perform(get("/api/pets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPet() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get the pet
        restPetMockMvc.perform(get("/api/pets/{id}", pet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pet.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()));
    }


    @Test
    @Transactional
    public void getPetsByIdFiltering() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        Long id = pet.getId();

        defaultPetShouldBeFound("id.equals=" + id);
        defaultPetShouldNotBeFound("id.notEquals=" + id);

        defaultPetShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPetShouldNotBeFound("id.greaterThan=" + id);

        defaultPetShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPetShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPetsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the petList where name equals to DEFAULT_NAME
        defaultPetShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the petList where name equals to UPDATED_NAME
        defaultPetShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPetsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the petList where name not equals to DEFAULT_NAME
        defaultPetShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the petList where name not equals to UPDATED_NAME
        defaultPetShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPetsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the petList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPetShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the petList where name equals to UPDATED_NAME
        defaultPetShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPetsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the petList where name is not null
        defaultPetShouldBeFound("name.specified=true");

        // Get all the petList where name is null
        defaultPetShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPetsByNameContainsSomething() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the petList where name contains DEFAULT_NAME
        defaultPetShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the petList where name contains UPDATED_NAME
        defaultPetShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPetsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the petList where name does not contain DEFAULT_NAME
        defaultPetShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the petList where name does not contain UPDATED_NAME
        defaultPetShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPetsByBirthDateIsEqualToSomething() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the petList where birthDate equals to DEFAULT_BIRTH_DATE
        defaultPetShouldBeFound("birthDate.equals=" + DEFAULT_BIRTH_DATE);

        // Get all the petList where birthDate equals to UPDATED_BIRTH_DATE
        defaultPetShouldNotBeFound("birthDate.equals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllPetsByBirthDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the petList where birthDate not equals to DEFAULT_BIRTH_DATE
        defaultPetShouldNotBeFound("birthDate.notEquals=" + DEFAULT_BIRTH_DATE);

        // Get all the petList where birthDate not equals to UPDATED_BIRTH_DATE
        defaultPetShouldBeFound("birthDate.notEquals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllPetsByBirthDateIsInShouldWork() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the petList where birthDate in DEFAULT_BIRTH_DATE or UPDATED_BIRTH_DATE
        defaultPetShouldBeFound("birthDate.in=" + DEFAULT_BIRTH_DATE + "," + UPDATED_BIRTH_DATE);

        // Get all the petList where birthDate equals to UPDATED_BIRTH_DATE
        defaultPetShouldNotBeFound("birthDate.in=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllPetsByBirthDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the petList where birthDate is not null
        defaultPetShouldBeFound("birthDate.specified=true");

        // Get all the petList where birthDate is null
        defaultPetShouldNotBeFound("birthDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPetsByBirthDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the petList where birthDate is greater than or equal to DEFAULT_BIRTH_DATE
        defaultPetShouldBeFound("birthDate.greaterThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the petList where birthDate is greater than or equal to UPDATED_BIRTH_DATE
        defaultPetShouldNotBeFound("birthDate.greaterThanOrEqual=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllPetsByBirthDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the petList where birthDate is less than or equal to DEFAULT_BIRTH_DATE
        defaultPetShouldBeFound("birthDate.lessThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the petList where birthDate is less than or equal to SMALLER_BIRTH_DATE
        defaultPetShouldNotBeFound("birthDate.lessThanOrEqual=" + SMALLER_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllPetsByBirthDateIsLessThanSomething() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the petList where birthDate is less than DEFAULT_BIRTH_DATE
        defaultPetShouldNotBeFound("birthDate.lessThan=" + DEFAULT_BIRTH_DATE);

        // Get all the petList where birthDate is less than UPDATED_BIRTH_DATE
        defaultPetShouldBeFound("birthDate.lessThan=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllPetsByBirthDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the petList where birthDate is greater than DEFAULT_BIRTH_DATE
        defaultPetShouldNotBeFound("birthDate.greaterThan=" + DEFAULT_BIRTH_DATE);

        // Get all the petList where birthDate is greater than SMALLER_BIRTH_DATE
        defaultPetShouldBeFound("birthDate.greaterThan=" + SMALLER_BIRTH_DATE);
    }


    @Test
    @Transactional
    public void getAllPetsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);
        PetType type = PetTypeResourceIT.createEntity(em);
        em.persist(type);
        em.flush();
        pet.setType(type);
        petRepository.saveAndFlush(pet);
        Long typeId = type.getId();

        // Get all the petList where type equals to typeId
        defaultPetShouldBeFound("typeId.equals=" + typeId);

        // Get all the petList where type equals to typeId + 1
        defaultPetShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }


    @Test
    @Transactional
    public void getAllPetsByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);
        Owner owner = OwnerResourceIT.createEntity(em);
        em.persist(owner);
        em.flush();
        pet.setOwner(owner);
        petRepository.saveAndFlush(pet);
        Long ownerId = owner.getId();

        // Get all the petList where owner equals to ownerId
        defaultPetShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the petList where owner equals to ownerId + 1
        defaultPetShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPetShouldBeFound(String filter) throws Exception {
        restPetMockMvc.perform(get("/api/pets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())));

        // Check, that the count call also returns 1
        restPetMockMvc.perform(get("/api/pets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPetShouldNotBeFound(String filter) throws Exception {
        restPetMockMvc.perform(get("/api/pets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPetMockMvc.perform(get("/api/pets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPet() throws Exception {
        // Get the pet
        restPetMockMvc.perform(get("/api/pets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePet() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        int databaseSizeBeforeUpdate = petRepository.findAll().size();

        // Update the pet
        Pet updatedPet = petRepository.findById(pet.getId()).get();
        // Disconnect from session so that the updates on updatedPet are not directly saved in db
        em.detach(updatedPet);
        updatedPet
            .name(UPDATED_NAME)
            .birthDate(UPDATED_BIRTH_DATE);
        PetDTO petDTO = petMapper.toDto(updatedPet);

        restPetMockMvc.perform(put("/api/pets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(petDTO)))
            .andExpect(status().isOk());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeUpdate);
        Pet testPet = petList.get(petList.size() - 1);
        assertThat(testPet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPet.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPet() throws Exception {
        int databaseSizeBeforeUpdate = petRepository.findAll().size();

        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPetMockMvc.perform(put("/api/pets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(petDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePet() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        int databaseSizeBeforeDelete = petRepository.findAll().size();

        // Delete the pet
        restPetMockMvc.perform(delete("/api/pets/{id}", pet.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
