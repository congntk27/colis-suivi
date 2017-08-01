package com.colissuivi.web.rest;

import com.colissuivi.ColisuiviApp;

import com.colissuivi.domain.Colis;
import com.colissuivi.repository.ColisRepository;
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

import com.colissuivi.domain.enumeration.LivraisonStatut;
/**
 * Test class for the ColisResource REST controller.
 *
 * @see ColisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ColisuiviApp.class)
public class ColisResourceIntTest {

    private static final LivraisonStatut DEFAULT_STATUT = LivraisonStatut.ENREGISTRE;
    private static final LivraisonStatut UPDATED_STATUT = LivraisonStatut.ENVOYE;

    @Autowired
    private ColisRepository colisRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restColisMockMvc;

    private Colis colis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ColisResource colisResource = new ColisResource(colisRepository);
        this.restColisMockMvc = MockMvcBuilders.standaloneSetup(colisResource)
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
    public static Colis createEntity() {
        Colis colis = new Colis()
            .statut(DEFAULT_STATUT);
        return colis;
    }

    @Before
    public void initTest() {
        colisRepository.deleteAll();
        colis = createEntity();
    }

    @Test
    public void createColis() throws Exception {
        int databaseSizeBeforeCreate = colisRepository.findAll().size();

        // Create the Colis
        restColisMockMvc.perform(post("/api/colis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(colis)))
            .andExpect(status().isCreated());

        // Validate the Colis in the database
        List<Colis> colisList = colisRepository.findAll();
        assertThat(colisList).hasSize(databaseSizeBeforeCreate + 1);
        Colis testColis = colisList.get(colisList.size() - 1);
        assertThat(testColis.getStatut()).isEqualTo(DEFAULT_STATUT);
    }

    @Test
    public void createColisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = colisRepository.findAll().size();

        // Create the Colis with an existing ID
        colis.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restColisMockMvc.perform(post("/api/colis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(colis)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Colis> colisList = colisRepository.findAll();
        assertThat(colisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllColis() throws Exception {
        // Initialize the database
        colisRepository.save(colis);

        // Get all the colisList
        restColisMockMvc.perform(get("/api/colis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(colis.getId())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())));
    }

    @Test
    public void getColis() throws Exception {
        // Initialize the database
        colisRepository.save(colis);

        // Get the colis
        restColisMockMvc.perform(get("/api/colis/{id}", colis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(colis.getId()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()));
    }

    @Test
    public void getNonExistingColis() throws Exception {
        // Get the colis
        restColisMockMvc.perform(get("/api/colis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateColis() throws Exception {
        // Initialize the database
        colisRepository.save(colis);
        int databaseSizeBeforeUpdate = colisRepository.findAll().size();

        // Update the colis
        Colis updatedColis = colisRepository.findOne(colis.getId());
        updatedColis
            .statut(UPDATED_STATUT);

        restColisMockMvc.perform(put("/api/colis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedColis)))
            .andExpect(status().isOk());

        // Validate the Colis in the database
        List<Colis> colisList = colisRepository.findAll();
        assertThat(colisList).hasSize(databaseSizeBeforeUpdate);
        Colis testColis = colisList.get(colisList.size() - 1);
        assertThat(testColis.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    public void updateNonExistingColis() throws Exception {
        int databaseSizeBeforeUpdate = colisRepository.findAll().size();

        // Create the Colis

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restColisMockMvc.perform(put("/api/colis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(colis)))
            .andExpect(status().isCreated());

        // Validate the Colis in the database
        List<Colis> colisList = colisRepository.findAll();
        assertThat(colisList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteColis() throws Exception {
        // Initialize the database
        colisRepository.save(colis);
        int databaseSizeBeforeDelete = colisRepository.findAll().size();

        // Get the colis
        restColisMockMvc.perform(delete("/api/colis/{id}", colis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Colis> colisList = colisRepository.findAll();
        assertThat(colisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Colis.class);
        Colis colis1 = new Colis();
        colis1.setId("id1");
        Colis colis2 = new Colis();
        colis2.setId(colis1.getId());
        assertThat(colis1).isEqualTo(colis2);
        colis2.setId("id2");
        assertThat(colis1).isNotEqualTo(colis2);
        colis1.setId(null);
        assertThat(colis1).isNotEqualTo(colis2);
    }
}
