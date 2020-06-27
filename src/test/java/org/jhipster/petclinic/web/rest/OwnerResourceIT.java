package org.jhipster.petclinic.web.rest;

import org.jhipster.petclinic.JhpetclinicApp;
import org.jhipster.petclinic.domain.Owner;
import org.jhipster.petclinic.domain.Pet;
import org.jhipster.petclinic.repository.OwnerRepository;
import org.jhipster.petclinic.service.OwnerService;
import org.jhipster.petclinic.service.dto.OwnerDTO;
import org.jhipster.petclinic.service.mapper.OwnerMapper;
import org.jhipster.petclinic.service.dto.OwnerCriteria;
import org.jhipster.petclinic.service.OwnerQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OwnerResource} REST controller.
 */
@SpringBootTest(classes = JhpetclinicApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OwnerResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private OwnerMapper ownerMapper;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private OwnerQueryService ownerQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOwnerMockMvc;

    private Owner owner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Owner createEntity(EntityManager em) {
        Owner owner = new Owner()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .telephone(DEFAULT_TELEPHONE);
        return owner;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Owner createUpdatedEntity(EntityManager em) {
        Owner owner = new Owner()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .telephone(UPDATED_TELEPHONE);
        return owner;
    }

    @BeforeEach
    public void initTest() {
        owner = createEntity(em);
    }

    @Test
    @Transactional
    public void createOwner() throws Exception {
        int databaseSizeBeforeCreate = ownerRepository.findAll().size();
        // Create the Owner
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);
        restOwnerMockMvc.perform(post("/api/owners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ownerDTO)))
            .andExpect(status().isCreated());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeCreate + 1);
        Owner testOwner = ownerList.get(ownerList.size() - 1);
        assertThat(testOwner.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testOwner.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testOwner.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testOwner.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testOwner.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
    }

    @Test
    @Transactional
    public void createOwnerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ownerRepository.findAll().size();

        // Create the Owner with an existing ID
        owner.setId(1L);
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOwnerMockMvc.perform(post("/api/owners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ownerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ownerRepository.findAll().size();
        // set the field null
        owner.setFirstName(null);

        // Create the Owner, which fails.
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);


        restOwnerMockMvc.perform(post("/api/owners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ownerDTO)))
            .andExpect(status().isBadRequest());

        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ownerRepository.findAll().size();
        // set the field null
        owner.setLastName(null);

        // Create the Owner, which fails.
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);


        restOwnerMockMvc.perform(post("/api/owners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ownerDTO)))
            .andExpect(status().isBadRequest());

        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = ownerRepository.findAll().size();
        // set the field null
        owner.setAddress(null);

        // Create the Owner, which fails.
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);


        restOwnerMockMvc.perform(post("/api/owners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ownerDTO)))
            .andExpect(status().isBadRequest());

        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = ownerRepository.findAll().size();
        // set the field null
        owner.setCity(null);

        // Create the Owner, which fails.
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);


        restOwnerMockMvc.perform(post("/api/owners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ownerDTO)))
            .andExpect(status().isBadRequest());

        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = ownerRepository.findAll().size();
        // set the field null
        owner.setTelephone(null);

        // Create the Owner, which fails.
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);


        restOwnerMockMvc.perform(post("/api/owners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ownerDTO)))
            .andExpect(status().isBadRequest());

        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOwners() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList
        restOwnerMockMvc.perform(get("/api/owners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(owner.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)));
    }
    
    @Test
    @Transactional
    public void getOwner() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get the owner
        restOwnerMockMvc.perform(get("/api/owners/{id}", owner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(owner.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE));
    }


    @Test
    @Transactional
    public void getOwnersByIdFiltering() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        Long id = owner.getId();

        defaultOwnerShouldBeFound("id.equals=" + id);
        defaultOwnerShouldNotBeFound("id.notEquals=" + id);

        defaultOwnerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOwnerShouldNotBeFound("id.greaterThan=" + id);

        defaultOwnerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOwnerShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOwnersByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where firstName equals to DEFAULT_FIRST_NAME
        defaultOwnerShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the ownerList where firstName equals to UPDATED_FIRST_NAME
        defaultOwnerShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllOwnersByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where firstName not equals to DEFAULT_FIRST_NAME
        defaultOwnerShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the ownerList where firstName not equals to UPDATED_FIRST_NAME
        defaultOwnerShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllOwnersByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultOwnerShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the ownerList where firstName equals to UPDATED_FIRST_NAME
        defaultOwnerShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllOwnersByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where firstName is not null
        defaultOwnerShouldBeFound("firstName.specified=true");

        // Get all the ownerList where firstName is null
        defaultOwnerShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllOwnersByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where firstName contains DEFAULT_FIRST_NAME
        defaultOwnerShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the ownerList where firstName contains UPDATED_FIRST_NAME
        defaultOwnerShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllOwnersByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where firstName does not contain DEFAULT_FIRST_NAME
        defaultOwnerShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the ownerList where firstName does not contain UPDATED_FIRST_NAME
        defaultOwnerShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllOwnersByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where lastName equals to DEFAULT_LAST_NAME
        defaultOwnerShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the ownerList where lastName equals to UPDATED_LAST_NAME
        defaultOwnerShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllOwnersByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where lastName not equals to DEFAULT_LAST_NAME
        defaultOwnerShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the ownerList where lastName not equals to UPDATED_LAST_NAME
        defaultOwnerShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllOwnersByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultOwnerShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the ownerList where lastName equals to UPDATED_LAST_NAME
        defaultOwnerShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllOwnersByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where lastName is not null
        defaultOwnerShouldBeFound("lastName.specified=true");

        // Get all the ownerList where lastName is null
        defaultOwnerShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllOwnersByLastNameContainsSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where lastName contains DEFAULT_LAST_NAME
        defaultOwnerShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the ownerList where lastName contains UPDATED_LAST_NAME
        defaultOwnerShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllOwnersByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where lastName does not contain DEFAULT_LAST_NAME
        defaultOwnerShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the ownerList where lastName does not contain UPDATED_LAST_NAME
        defaultOwnerShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllOwnersByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where address equals to DEFAULT_ADDRESS
        defaultOwnerShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the ownerList where address equals to UPDATED_ADDRESS
        defaultOwnerShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllOwnersByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where address not equals to DEFAULT_ADDRESS
        defaultOwnerShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the ownerList where address not equals to UPDATED_ADDRESS
        defaultOwnerShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllOwnersByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultOwnerShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the ownerList where address equals to UPDATED_ADDRESS
        defaultOwnerShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllOwnersByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where address is not null
        defaultOwnerShouldBeFound("address.specified=true");

        // Get all the ownerList where address is null
        defaultOwnerShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllOwnersByAddressContainsSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where address contains DEFAULT_ADDRESS
        defaultOwnerShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the ownerList where address contains UPDATED_ADDRESS
        defaultOwnerShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllOwnersByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where address does not contain DEFAULT_ADDRESS
        defaultOwnerShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the ownerList where address does not contain UPDATED_ADDRESS
        defaultOwnerShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllOwnersByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where city equals to DEFAULT_CITY
        defaultOwnerShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the ownerList where city equals to UPDATED_CITY
        defaultOwnerShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllOwnersByCityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where city not equals to DEFAULT_CITY
        defaultOwnerShouldNotBeFound("city.notEquals=" + DEFAULT_CITY);

        // Get all the ownerList where city not equals to UPDATED_CITY
        defaultOwnerShouldBeFound("city.notEquals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllOwnersByCityIsInShouldWork() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where city in DEFAULT_CITY or UPDATED_CITY
        defaultOwnerShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the ownerList where city equals to UPDATED_CITY
        defaultOwnerShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllOwnersByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where city is not null
        defaultOwnerShouldBeFound("city.specified=true");

        // Get all the ownerList where city is null
        defaultOwnerShouldNotBeFound("city.specified=false");
    }
                @Test
    @Transactional
    public void getAllOwnersByCityContainsSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where city contains DEFAULT_CITY
        defaultOwnerShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the ownerList where city contains UPDATED_CITY
        defaultOwnerShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllOwnersByCityNotContainsSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where city does not contain DEFAULT_CITY
        defaultOwnerShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the ownerList where city does not contain UPDATED_CITY
        defaultOwnerShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }


    @Test
    @Transactional
    public void getAllOwnersByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where telephone equals to DEFAULT_TELEPHONE
        defaultOwnerShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the ownerList where telephone equals to UPDATED_TELEPHONE
        defaultOwnerShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllOwnersByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where telephone not equals to DEFAULT_TELEPHONE
        defaultOwnerShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the ownerList where telephone not equals to UPDATED_TELEPHONE
        defaultOwnerShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllOwnersByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultOwnerShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the ownerList where telephone equals to UPDATED_TELEPHONE
        defaultOwnerShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllOwnersByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where telephone is not null
        defaultOwnerShouldBeFound("telephone.specified=true");

        // Get all the ownerList where telephone is null
        defaultOwnerShouldNotBeFound("telephone.specified=false");
    }
                @Test
    @Transactional
    public void getAllOwnersByTelephoneContainsSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where telephone contains DEFAULT_TELEPHONE
        defaultOwnerShouldBeFound("telephone.contains=" + DEFAULT_TELEPHONE);

        // Get all the ownerList where telephone contains UPDATED_TELEPHONE
        defaultOwnerShouldNotBeFound("telephone.contains=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllOwnersByTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where telephone does not contain DEFAULT_TELEPHONE
        defaultOwnerShouldNotBeFound("telephone.doesNotContain=" + DEFAULT_TELEPHONE);

        // Get all the ownerList where telephone does not contain UPDATED_TELEPHONE
        defaultOwnerShouldBeFound("telephone.doesNotContain=" + UPDATED_TELEPHONE);
    }


    @Test
    @Transactional
    public void getAllOwnersByPetsIsEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);
        Pet pets = PetResourceIT.createEntity(em);
        em.persist(pets);
        em.flush();
        owner.addPets(pets);
        ownerRepository.saveAndFlush(owner);
        Long petsId = pets.getId();

        // Get all the ownerList where pets equals to petsId
        defaultOwnerShouldBeFound("petsId.equals=" + petsId);

        // Get all the ownerList where pets equals to petsId + 1
        defaultOwnerShouldNotBeFound("petsId.equals=" + (petsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOwnerShouldBeFound(String filter) throws Exception {
        restOwnerMockMvc.perform(get("/api/owners?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(owner.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)));

        // Check, that the count call also returns 1
        restOwnerMockMvc.perform(get("/api/owners/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOwnerShouldNotBeFound(String filter) throws Exception {
        restOwnerMockMvc.perform(get("/api/owners?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOwnerMockMvc.perform(get("/api/owners/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOwner() throws Exception {
        // Get the owner
        restOwnerMockMvc.perform(get("/api/owners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOwner() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        int databaseSizeBeforeUpdate = ownerRepository.findAll().size();

        // Update the owner
        Owner updatedOwner = ownerRepository.findById(owner.getId()).get();
        // Disconnect from session so that the updates on updatedOwner are not directly saved in db
        em.detach(updatedOwner);
        updatedOwner
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .telephone(UPDATED_TELEPHONE);
        OwnerDTO ownerDTO = ownerMapper.toDto(updatedOwner);

        restOwnerMockMvc.perform(put("/api/owners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ownerDTO)))
            .andExpect(status().isOk());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeUpdate);
        Owner testOwner = ownerList.get(ownerList.size() - 1);
        assertThat(testOwner.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testOwner.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testOwner.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testOwner.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testOwner.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingOwner() throws Exception {
        int databaseSizeBeforeUpdate = ownerRepository.findAll().size();

        // Create the Owner
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOwnerMockMvc.perform(put("/api/owners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ownerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOwner() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        int databaseSizeBeforeDelete = ownerRepository.findAll().size();

        // Delete the owner
        restOwnerMockMvc.perform(delete("/api/owners/{id}", owner.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
