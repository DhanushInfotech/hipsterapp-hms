package com.hospital.management.web.rest;

import com.hospital.management.HospitalManagementApp;

import com.hospital.management.domain.Poc;
import com.hospital.management.repository.PocRepository;
import com.hospital.management.service.PocService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PocResource REST controller.
 *
 * @see PocResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalManagementApp.class)
public class PocResourceIntTest {

    private static final String DEFAULT_CHIEFCOMPLAINT = "AAAAAAAAAA";
    private static final String UPDATED_CHIEFCOMPLAINT = "BBBBBBBBBB";

    private static final Long DEFAULT_BP = 1L;
    private static final Long UPDATED_BP = 2L;

    private static final Long DEFAULT_PULSE = 1L;
    private static final Long UPDATED_PULSE = 2L;

    private static final Long DEFAULT_TEMPERATURE = 1L;
    private static final Long UPDATED_TEMPERATURE = 2L;

    private static final Long DEFAULT_WEIGHT = 1L;
    private static final Long UPDATED_WEIGHT = 2L;

    private static final String DEFAULT_DRUGS = "AAAAAAAAAA";
    private static final String UPDATED_DRUGS = "BBBBBBBBBB";

    private static final String DEFAULT_DOCNOTE = "AAAAAAAAAA";
    private static final String UPDATED_DOCNOTE = "BBBBBBBBBB";

    private static final String DEFAULT_DIAGNOSIS = "AAAAAAAAAA";
    private static final String UPDATED_DIAGNOSIS = "BBBBBBBBBB";

    private static final String DEFAULT_INVESTIGATION = "AAAAAAAAAA";
    private static final String UPDATED_INVESTIGATION = "BBBBBBBBBB";

    private static final String DEFAULT_PATIENTNAME = "AAAAAAAAAA";
    private static final String UPDATED_PATIENTNAME = "BBBBBBBBBB";

    @Inject
    private PocRepository pocRepository;

    @Inject
    private PocService pocService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPocMockMvc;

    private Poc poc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PocResource pocResource = new PocResource();
        ReflectionTestUtils.setField(pocResource, "pocService", pocService);
        this.restPocMockMvc = MockMvcBuilders.standaloneSetup(pocResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Poc createEntity(EntityManager em) {
        Poc poc = new Poc()
                .chiefcomplaint(DEFAULT_CHIEFCOMPLAINT)
                .bp(DEFAULT_BP)
                .pulse(DEFAULT_PULSE)
                .temperature(DEFAULT_TEMPERATURE)
                .weight(DEFAULT_WEIGHT)
                .drugs(DEFAULT_DRUGS)
                .docnote(DEFAULT_DOCNOTE)
                .diagnosis(DEFAULT_DIAGNOSIS)
                .investigation(DEFAULT_INVESTIGATION)
                .patientname(DEFAULT_PATIENTNAME);
        return poc;
    }

    @Before
    public void initTest() {
        poc = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoc() throws Exception {
        int databaseSizeBeforeCreate = pocRepository.findAll().size();

        // Create the Poc

        restPocMockMvc.perform(post("/api/pocs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(poc)))
                .andExpect(status().isCreated());

        // Validate the Poc in the database
        List<Poc> pocs = pocRepository.findAll();
        assertThat(pocs).hasSize(databaseSizeBeforeCreate + 1);
        Poc testPoc = pocs.get(pocs.size() - 1);
        assertThat(testPoc.getChiefcomplaint()).isEqualTo(DEFAULT_CHIEFCOMPLAINT);
        assertThat(testPoc.getBp()).isEqualTo(DEFAULT_BP);
        assertThat(testPoc.getPulse()).isEqualTo(DEFAULT_PULSE);
        assertThat(testPoc.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testPoc.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testPoc.getDrugs()).isEqualTo(DEFAULT_DRUGS);
        assertThat(testPoc.getDocnote()).isEqualTo(DEFAULT_DOCNOTE);
        assertThat(testPoc.getDiagnosis()).isEqualTo(DEFAULT_DIAGNOSIS);
        assertThat(testPoc.getInvestigation()).isEqualTo(DEFAULT_INVESTIGATION);
        assertThat(testPoc.getPatientname()).isEqualTo(DEFAULT_PATIENTNAME);
    }

    @Test
    @Transactional
    public void getAllPocs() throws Exception {
        // Initialize the database
        pocRepository.saveAndFlush(poc);

        // Get all the pocs
        restPocMockMvc.perform(get("/api/pocs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(poc.getId().intValue())))
                .andExpect(jsonPath("$.[*].chiefcomplaint").value(hasItem(DEFAULT_CHIEFCOMPLAINT.toString())))
                .andExpect(jsonPath("$.[*].bp").value(hasItem(DEFAULT_BP.intValue())))
                .andExpect(jsonPath("$.[*].pulse").value(hasItem(DEFAULT_PULSE.intValue())))
                .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.intValue())))
                .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
                .andExpect(jsonPath("$.[*].drugs").value(hasItem(DEFAULT_DRUGS.toString())))
                .andExpect(jsonPath("$.[*].docnote").value(hasItem(DEFAULT_DOCNOTE.toString())))
                .andExpect(jsonPath("$.[*].diagnosis").value(hasItem(DEFAULT_DIAGNOSIS.toString())))
                .andExpect(jsonPath("$.[*].investigation").value(hasItem(DEFAULT_INVESTIGATION.toString())))
                .andExpect(jsonPath("$.[*].patientname").value(hasItem(DEFAULT_PATIENTNAME.toString())));
    }

    @Test
    @Transactional
    public void getPoc() throws Exception {
        // Initialize the database
        pocRepository.saveAndFlush(poc);

        // Get the poc
        restPocMockMvc.perform(get("/api/pocs/{id}", poc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(poc.getId().intValue()))
            .andExpect(jsonPath("$.chiefcomplaint").value(DEFAULT_CHIEFCOMPLAINT.toString()))
            .andExpect(jsonPath("$.bp").value(DEFAULT_BP.intValue()))
            .andExpect(jsonPath("$.pulse").value(DEFAULT_PULSE.intValue()))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.intValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.intValue()))
            .andExpect(jsonPath("$.drugs").value(DEFAULT_DRUGS.toString()))
            .andExpect(jsonPath("$.docnote").value(DEFAULT_DOCNOTE.toString()))
            .andExpect(jsonPath("$.diagnosis").value(DEFAULT_DIAGNOSIS.toString()))
            .andExpect(jsonPath("$.investigation").value(DEFAULT_INVESTIGATION.toString()))
            .andExpect(jsonPath("$.patientname").value(DEFAULT_PATIENTNAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPoc() throws Exception {
        // Get the poc
        restPocMockMvc.perform(get("/api/pocs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoc() throws Exception {
        // Initialize the database
        pocService.save(poc);

        int databaseSizeBeforeUpdate = pocRepository.findAll().size();

        // Update the poc
        Poc updatedPoc = pocRepository.findOne(poc.getId());
        updatedPoc
                .chiefcomplaint(UPDATED_CHIEFCOMPLAINT)
                .bp(UPDATED_BP)
                .pulse(UPDATED_PULSE)
                .temperature(UPDATED_TEMPERATURE)
                .weight(UPDATED_WEIGHT)
                .drugs(UPDATED_DRUGS)
                .docnote(UPDATED_DOCNOTE)
                .diagnosis(UPDATED_DIAGNOSIS)
                .investigation(UPDATED_INVESTIGATION)
                .patientname(UPDATED_PATIENTNAME);

        restPocMockMvc.perform(put("/api/pocs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPoc)))
                .andExpect(status().isOk());

        // Validate the Poc in the database
        List<Poc> pocs = pocRepository.findAll();
        assertThat(pocs).hasSize(databaseSizeBeforeUpdate);
        Poc testPoc = pocs.get(pocs.size() - 1);
        assertThat(testPoc.getChiefcomplaint()).isEqualTo(UPDATED_CHIEFCOMPLAINT);
        assertThat(testPoc.getBp()).isEqualTo(UPDATED_BP);
        assertThat(testPoc.getPulse()).isEqualTo(UPDATED_PULSE);
        assertThat(testPoc.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testPoc.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testPoc.getDrugs()).isEqualTo(UPDATED_DRUGS);
        assertThat(testPoc.getDocnote()).isEqualTo(UPDATED_DOCNOTE);
        assertThat(testPoc.getDiagnosis()).isEqualTo(UPDATED_DIAGNOSIS);
        assertThat(testPoc.getInvestigation()).isEqualTo(UPDATED_INVESTIGATION);
        assertThat(testPoc.getPatientname()).isEqualTo(UPDATED_PATIENTNAME);
    }

    @Test
    @Transactional
    public void deletePoc() throws Exception {
        // Initialize the database
        pocService.save(poc);

        int databaseSizeBeforeDelete = pocRepository.findAll().size();

        // Get the poc
        restPocMockMvc.perform(delete("/api/pocs/{id}", poc.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Poc> pocs = pocRepository.findAll();
        assertThat(pocs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
