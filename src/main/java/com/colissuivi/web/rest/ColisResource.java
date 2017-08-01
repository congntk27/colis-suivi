package com.colissuivi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.colissuivi.domain.Colis;

import com.colissuivi.repository.ColisRepository;
import com.colissuivi.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Colis.
 */
@RestController
@RequestMapping("/api")
public class ColisResource {

    private final Logger log = LoggerFactory.getLogger(ColisResource.class);

    private static final String ENTITY_NAME = "colis";

    private final ColisRepository colisRepository;

    public ColisResource(ColisRepository colisRepository) {
        this.colisRepository = colisRepository;
    }

    /**
     * POST  /colis : Create a new colis.
     *
     * @param colis the colis to create
     * @return the ResponseEntity with status 201 (Created) and with body the new colis, or with status 400 (Bad Request) if the colis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/colis")
    @Timed
    public ResponseEntity<Colis> createColis(@RequestBody Colis colis) throws URISyntaxException {
        log.debug("REST request to save Colis : {}", colis);
        if (colis.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new colis cannot already have an ID")).body(null);
        }
        Colis result = colisRepository.save(colis);
        return ResponseEntity.created(new URI("/api/colis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /colis : Updates an existing colis.
     *
     * @param colis the colis to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated colis,
     * or with status 400 (Bad Request) if the colis is not valid,
     * or with status 500 (Internal Server Error) if the colis couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/colis")
    @Timed
    public ResponseEntity<Colis> updateColis(@RequestBody Colis colis) throws URISyntaxException {
        log.debug("REST request to update Colis : {}", colis);
        if (colis.getId() == null) {
            return createColis(colis);
        }
        Colis result = colisRepository.save(colis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, colis.getId().toString()))
            .body(result);
    }

    /**
     * GET  /colis : get all the colis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of colis in body
     */
    @GetMapping("/colis")
    @Timed
    public List<Colis> getAllColis() {
        log.debug("REST request to get all Colis");
        return colisRepository.findAll();
    }

    /**
     * GET  /colis/:id : get the "id" colis.
     *
     * @param id the id of the colis to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the colis, or with status 404 (Not Found)
     */
    @GetMapping("/colis/{id}")
    @Timed
    public ResponseEntity<Colis> getColis(@PathVariable String id) {
        log.debug("REST request to get Colis : {}", id);
        Colis colis = colisRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(colis));
    }

    /**
     * DELETE  /colis/:id : delete the "id" colis.
     *
     * @param id the id of the colis to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/colis/{id}")
    @Timed
    public ResponseEntity<Void> deleteColis(@PathVariable String id) {
        log.debug("REST request to delete Colis : {}", id);
        colisRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
