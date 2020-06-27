package org.jhipster.petclinic.web.rest;

import org.jhipster.petclinic.JhpetclinicApp;
import org.jhipster.petclinic.domain.Visit;
import org.jhipster.petclinic.domain.Pet;
import org.jhipster.petclinic.repository.VisitRepository;
import org.jhipster.petclinic.service.VisitService;
import org.jhipster.petclinic.service.dto.VisitDTO;
import org.jhipster.petclinic.service.mapper.VisitMapper;
import org.jhipster.petclinic.service.dto.VisitCriteria;
import org.jhipster.petclinic.service.VisitQueryService;

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
 * Integration tests for the {@link VisitResource} REST controller.
 */
@SpringBootTest(classes = JhpetclinicApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VisitResourceIT {

    private static final LocalDate DEFAULT_VISIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VISIT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VISIT_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private VisitMapper visitMapper;

    @Autowired
    private VisitService visitService;

    @Autowired
    private VisitQueryService visitQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVisitMockMvc;

    private Visit visit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visit createEntity(EntityManager em) {
        Visit visit = new Visit()
            .visitDate(DEFAULT_VISIT_DATE)
            .description(DEFAULT_DESCRIPTION);
        return visit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visit createUpdatedEntity(EntityManager em) {
        Visit visit = new Visit()
            .visitDate(UPDATED_VISIT_DATE)
            .description(UPDATED_DESCRIPTION);
        return visit;
    }

    @BeforeEach
    public void initTest() {
        visit = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisit() throws Exception {
        int databaseSizeBeforeCreate = visitRepository.findAll().size();
        // Create the Visit
        VisitDTO visitDTO = visitMapper.toDto(visit);
        restVisitMockMvc.perform(post("/api/visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visitDTO)))
            .andExpect(status().isCreated());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeCreate + 1);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getVisitDate()).isEqualTo(DEFAULT_VISIT_DATE);
        assertThat(testVisit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createVisitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visitRepository.findAll().size();

        // Create the Visit with an existing ID
        visit.setId(1L);
        VisitDTO visitDTO = visitMapper.toDto(visit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitMockMvc.perform(post("/api/visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitRepository.findAll().size();
        // set the field null
        visit.setDescription(null);

        // Create the Visit, which fails.
        VisitDTO visitDTO = visitMapper.toDto(visit);


        restVisitMockMvc.perform(post("/api/visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visitDTO)))
            .andExpect(status().isBadRequest());

        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVisits() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList
        restVisitMockMvc.perform(get("/api/visits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visit.getId().intValue())))
            .andExpect(jsonPath("$.[*].visitDate").value(hasItem(DEFAULT_VISIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getVisit() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get the visit
        restVisitMockMvc.perform(get("/api/visits/{id}", visit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(visit.getId().intValue()))
            .andExpect(jsonPath("$.visitDate").value(DEFAULT_VISIT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getVisitsByIdFiltering() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        Long id = visit.getId();

        defaultVisitShouldBeFound("id.equals=" + id);
        defaultVisitShouldNotBeFound("id.notEquals=" + id);

        defaultVisitShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVisitShouldNotBeFound("id.greaterThan=" + id);

        defaultVisitShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVisitShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVisitsByVisitDateIsEqualToSomething() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where visitDate equals to DEFAULT_VISIT_DATE
        defaultVisitShouldBeFound("visitDate.equals=" + DEFAULT_VISIT_DATE);

        // Get all the visitList where visitDate equals to UPDATED_VISIT_DATE
        defaultVisitShouldNotBeFound("visitDate.equals=" + UPDATED_VISIT_DATE);
    }

    @Test
    @Transactional
    public void getAllVisitsByVisitDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where visitDate not equals to DEFAULT_VISIT_DATE
        defaultVisitShouldNotBeFound("visitDate.notEquals=" + DEFAULT_VISIT_DATE);

        // Get all the visitList where visitDate not equals to UPDATED_VISIT_DATE
        defaultVisitShouldBeFound("visitDate.notEquals=" + UPDATED_VISIT_DATE);
    }

    @Test
    @Transactional
    public void getAllVisitsByVisitDateIsInShouldWork() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where visitDate in DEFAULT_VISIT_DATE or UPDATED_VISIT_DATE
        defaultVisitShouldBeFound("visitDate.in=" + DEFAULT_VISIT_DATE + "," + UPDATED_VISIT_DATE);

        // Get all the visitList where visitDate equals to UPDATED_VISIT_DATE
        defaultVisitShouldNotBeFound("visitDate.in=" + UPDATED_VISIT_DATE);
    }

    @Test
    @Transactional
    public void getAllVisitsByVisitDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where visitDate is not null
        defaultVisitShouldBeFound("visitDate.specified=true");

        // Get all the visitList where visitDate is null
        defaultVisitShouldNotBeFound("visitDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllVisitsByVisitDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where visitDate is greater than or equal to DEFAULT_VISIT_DATE
        defaultVisitShouldBeFound("visitDate.greaterThanOrEqual=" + DEFAULT_VISIT_DATE);

        // Get all the visitList where visitDate is greater than or equal to UPDATED_VISIT_DATE
        defaultVisitShouldNotBeFound("visitDate.greaterThanOrEqual=" + UPDATED_VISIT_DATE);
    }

    @Test
    @Transactional
    public void getAllVisitsByVisitDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where visitDate is less than or equal to DEFAULT_VISIT_DATE
        defaultVisitShouldBeFound("visitDate.lessThanOrEqual=" + DEFAULT_VISIT_DATE);

        // Get all the visitList where visitDate is less than or equal to SMALLER_VISIT_DATE
        defaultVisitShouldNotBeFound("visitDate.lessThanOrEqual=" + SMALLER_VISIT_DATE);
    }

    @Test
    @Transactional
    public void getAllVisitsByVisitDateIsLessThanSomething() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where visitDate is less than DEFAULT_VISIT_DATE
        defaultVisitShouldNotBeFound("visitDate.lessThan=" + DEFAULT_VISIT_DATE);

        // Get all the visitList where visitDate is less than UPDATED_VISIT_DATE
        defaultVisitShouldBeFound("visitDate.lessThan=" + UPDATED_VISIT_DATE);
    }

    @Test
    @Transactional
    public void getAllVisitsByVisitDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where visitDate is greater than DEFAULT_VISIT_DATE
        defaultVisitShouldNotBeFound("visitDate.greaterThan=" + DEFAULT_VISIT_DATE);

        // Get all the visitList where visitDate is greater than SMALLER_VISIT_DATE
        defaultVisitShouldBeFound("visitDate.greaterThan=" + SMALLER_VISIT_DATE);
    }


    @Test
    @Transactional
    public void getAllVisitsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where description equals to DEFAULT_DESCRIPTION
        defaultVisitShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the visitList where description equals to UPDATED_DESCRIPTION
        defaultVisitShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllVisitsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where description not equals to DEFAULT_DESCRIPTION
        defaultVisitShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the visitList where description not equals to UPDATED_DESCRIPTION
        defaultVisitShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllVisitsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultVisitShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the visitList where description equals to UPDATED_DESCRIPTION
        defaultVisitShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllVisitsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where description is not null
        defaultVisitShouldBeFound("description.specified=true");

        // Get all the visitList where description is null
        defaultVisitShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllVisitsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where description contains DEFAULT_DESCRIPTION
        defaultVisitShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the visitList where description contains UPDATED_DESCRIPTION
        defaultVisitShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllVisitsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where description does not contain DEFAULT_DESCRIPTION
        defaultVisitShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the visitList where description does not contain UPDATED_DESCRIPTION
        defaultVisitShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllVisitsByPetIsEqualToSomething() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);
        Pet pet = PetResourceIT.createEntity(em);
        em.persist(pet);
        em.flush();
        visit.setPet(pet);
        visitRepository.saveAndFlush(visit);
        Long petId = pet.getId();

        // Get all the visitList where pet equals to petId
        defaultVisitShouldBeFound("petId.equals=" + petId);

        // Get all the visitList where pet equals to petId + 1
        defaultVisitShouldNotBeFound("petId.equals=" + (petId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVisitShouldBeFound(String filter) throws Exception {
        restVisitMockMvc.perform(get("/api/visits?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visit.getId().intValue())))
            .andExpect(jsonPath("$.[*].visitDate").value(hasItem(DEFAULT_VISIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restVisitMockMvc.perform(get("/api/visits/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVisitShouldNotBeFound(String filter) throws Exception {
        restVisitMockMvc.perform(get("/api/visits?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVisitMockMvc.perform(get("/api/visits/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingVisit() throws Exception {
        // Get the visit
        restVisitMockMvc.perform(get("/api/visits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisit() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        int databaseSizeBeforeUpdate = visitRepository.findAll().size();

        // Update the visit
        Visit updatedVisit = visitRepository.findById(visit.getId()).get();
        // Disconnect from session so that the updates on updatedVisit are not directly saved in db
        em.detach(updatedVisit);
        updatedVisit
            .visitDate(UPDATED_VISIT_DATE)
            .description(UPDATED_DESCRIPTION);
        VisitDTO visitDTO = visitMapper.toDto(updatedVisit);

        restVisitMockMvc.perform(put("/api/visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visitDTO)))
            .andExpect(status().isOk());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getVisitDate()).isEqualTo(UPDATED_VISIT_DATE);
        assertThat(testVisit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().size();

        // Create the Visit
        VisitDTO visitDTO = visitMapper.toDto(visit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisitMockMvc.perform(put("/api/visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVisit() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        int databaseSizeBeforeDelete = visitRepository.findAll().size();

        // Delete the visit
        restVisitMockMvc.perform(delete("/api/visits/{id}", visit.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
