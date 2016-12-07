package com.hospital.management.web.rest;

import com.hospital.management.HospitalManagementApp;

import com.hospital.management.domain.State;
import com.hospital.management.repository.StateRepository;

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
 * Test class for the StateResource REST controller.
 *
 * @see StateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalManagementApp.class)
public class StateResourceIntTest {

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    @Inject
    private StateRepository stateRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restStateMockMvc;

    private State state;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StateResource stateResource = new StateResource();
        ReflectionTestUtils.setField(stateResource, "stateRepository", stateRepository);
        this.restStateMockMvc = MockMvcBuilders.standaloneSetup(stateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static State createEntity(EntityManager em) {
        State state = new State()
                .state(DEFAULT_STATE);
        return state;
    }

    @Before
    public void initTest() {
        state = createEntity(em);
    }

    @Test
    @Transactional
    public void createState() throws Exception {
        int databaseSizeBeforeCreate = stateRepository.findAll().size();

        // Create the State

        restStateMockMvc.perform(post("/api/states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(state)))
                .andExpect(status().isCreated());

        // Validate the State in the database
        List<State> states = stateRepository.findAll();
        assertThat(states).hasSize(databaseSizeBeforeCreate + 1);
        State testState = states.get(states.size() - 1);
        assertThat(testState.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void getAllStates() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the states
        restStateMockMvc.perform(get("/api/states?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(state.getId().intValue())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    public void getState() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get the state
        restStateMockMvc.perform(get("/api/states/{id}", state.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(state.getId().intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingState() throws Exception {
        // Get the state
        restStateMockMvc.perform(get("/api/states/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateState() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);
        int databaseSizeBeforeUpdate = stateRepository.findAll().size();

        // Update the state
        State updatedState = stateRepository.findOne(state.getId());
        updatedState
                .state(UPDATED_STATE);

        restStateMockMvc.perform(put("/api/states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedState)))
                .andExpect(status().isOk());

        // Validate the State in the database
        List<State> states = stateRepository.findAll();
        assertThat(states).hasSize(databaseSizeBeforeUpdate);
        State testState = states.get(states.size() - 1);
        assertThat(testState.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void deleteState() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);
        int databaseSizeBeforeDelete = stateRepository.findAll().size();

        // Get the state
        restStateMockMvc.perform(delete("/api/states/{id}", state.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<State> states = stateRepository.findAll();
        assertThat(states).hasSize(databaseSizeBeforeDelete - 1);
    }
}
