package com.colissuivi.web.rest;

import com.colissuivi.ColisuiviApp;

import com.colissuivi.domain.Agence;
import com.colissuivi.repository.AgenceRepository;
import com.colissuivi.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AgenceResource REST controller.
 *
 * @see AgenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ColisuiviApp.class)
public class AgenceResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAgenceMockMvc;

    private Agence agence;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AgenceResource agenceResource = new AgenceResource(agenceRepository);
        this.restAgenceMockMvc = MockMvcBuilders.standaloneSetup(agenceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agence createEntity() {
        Agence agence = new Agence()
            .nom(DEFAULT_NOM)
            .adresse(DEFAULT_ADRESSE)
            .phone(DEFAULT_PHONE);
        return agence;
    }

    @Before
    public void initTest() {
        agenceRepository.deleteAll();
        agence = createEntity();
    }

    @Test
    public void createAgence() throws Exception {
        int databaseSizeBeforeCreate = agenceRepository.findAll().size();

        // Create the Agence
        restAgenceMockMvc.perform(post("/api/agences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agence)))
            .andExpect(status().isCreated());

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeCreate + 1);
        Agence testAgence = agenceList.get(agenceList.size() - 1);
        assertThat(testAgence.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAgence.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testAgence.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    public void createAgenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agenceRepository.findAll().size();

        // Create the Agence with an existing ID
        agence.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgenceMockMvc.perform(post("/api/agences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agence)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllAgences() throws Exception {
        // Initialize the database
        agenceRepository.save(agence);

        // Get all the agenceList
        restAgenceMockMvc.perform(get("/api/agences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agence.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())));
    }

    @Test
    public void getAgence() throws Exception {
        // Initialize the database
        agenceRepository.save(agence);

        // Get the agence
        restAgenceMockMvc.perform(get("/api/agences/{id}", agence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agence.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()));
    }

    @Test
    public void getNonExistingAgence() throws Exception {
        // Get the agence
        restAgenceMockMvc.perform(get("/api/agences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAgence() throws Exception {
        // Initialize the database
        agenceRepository.save(agence);
        int databaseSizeBeforeUpdate = agenceRepository.findAll().size();

        // Update the agence
        Agence updatedAgence = agenceRepository.findOne(agence.getId());
        updatedAgence
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .phone(UPDATED_PHONE);

        restAgenceMockMvc.perform(put("/api/agences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgence)))
            .andExpect(status().isOk());

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeUpdate);
        Agence testAgence = agenceList.get(agenceList.size() - 1);
        assertThat(testAgence.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAgence.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testAgence.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    public void updateNonExistingAgence() throws Exception {
        int databaseSizeBeforeUpdate = agenceRepository.findAll().size();

        // Create the Agence

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAgenceMockMvc.perform(put("/api/agences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agence)))
            .andExpect(status().isCreated());

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteAgence() throws Exception {
        // Initialize the database
        agenceRepository.save(agence);
        int databaseSizeBeforeDelete = agenceRepository.findAll().size();

        // Get the agence
        restAgenceMockMvc.perform(delete("/api/agences/{id}", agence.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agence.class);
        Agence agence1 = new Agence();
        agence1.setId("id1");
        Agence agence2 = new Agence();
        agence2.setId(agence1.getId());
        assertThat(agence1).isEqualTo(agence2);
        agence2.setId("id2");
        assertThat(agence1).isNotEqualTo(agence2);
        agence1.setId(null);
        assertThat(agence1).isNotEqualTo(agence2);
    }
}
