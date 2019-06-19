package com.cpi.workflow.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.workflow.service.ActivitiWorkflowFileService;
import com.cpi.workflow.web.rest.errors.BadRequestAlertException;
import com.cpi.workflow.web.rest.util.HeaderUtil;
import com.cpi.workflow.web.rest.util.PaginationUtil;
import com.cpi.workflow.service.dto.ActivitiWorkflowFileDTO;
import com.cpi.workflow.service.dto.ActivitiWorkflowFileCriteria;
import com.cpi.workflow.service.ActivitiWorkflowFileQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ActivitiWorkflowFile.
 */
@RestController
@RequestMapping("/api")
public class ActivitiWorkflowFileResource {

    private final Logger log = LoggerFactory.getLogger(ActivitiWorkflowFileResource.class);

    private static final String ENTITY_NAME = "activitiWorkflowFile";

    private final ActivitiWorkflowFileService activitiWorkflowFileService;

    private final ActivitiWorkflowFileQueryService activitiWorkflowFileQueryService;

    public ActivitiWorkflowFileResource(ActivitiWorkflowFileService activitiWorkflowFileService, ActivitiWorkflowFileQueryService activitiWorkflowFileQueryService) {
        this.activitiWorkflowFileService = activitiWorkflowFileService;
        this.activitiWorkflowFileQueryService = activitiWorkflowFileQueryService;
    }

    /**
     * POST  /activiti-workflow-files : Create a new activitiWorkflowFile.
     *
     * @param activitiWorkflowFileDTO the activitiWorkflowFileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new activitiWorkflowFileDTO, or with status 400 (Bad Request) if the activitiWorkflowFile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/activiti-workflow-files")
    @Timed
    public ResponseEntity<ActivitiWorkflowFileDTO> createActivitiWorkflowFile(@RequestBody ActivitiWorkflowFileDTO activitiWorkflowFileDTO) throws URISyntaxException {
        log.debug("REST request to save ActivitiWorkflowFile : {}", activitiWorkflowFileDTO);
        if (activitiWorkflowFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new activitiWorkflowFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivitiWorkflowFileDTO result = activitiWorkflowFileService.save(activitiWorkflowFileDTO);
        return ResponseEntity.created(new URI("/api/activiti-workflow-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /activiti-workflow-files : Updates an existing activitiWorkflowFile.
     *
     * @param activitiWorkflowFileDTO the activitiWorkflowFileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated activitiWorkflowFileDTO,
     * or with status 400 (Bad Request) if the activitiWorkflowFileDTO is not valid,
     * or with status 500 (Internal Server Error) if the activitiWorkflowFileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/activiti-workflow-files")
    @Timed
    public ResponseEntity<ActivitiWorkflowFileDTO> updateActivitiWorkflowFile(@RequestBody ActivitiWorkflowFileDTO activitiWorkflowFileDTO) throws URISyntaxException {
        log.debug("REST request to update ActivitiWorkflowFile : {}", activitiWorkflowFileDTO);
        if (activitiWorkflowFileDTO.getId() == null) {
            return createActivitiWorkflowFile(activitiWorkflowFileDTO);
        }
        ActivitiWorkflowFileDTO result = activitiWorkflowFileService.save(activitiWorkflowFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, activitiWorkflowFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /activiti-workflow-files : get all the activitiWorkflowFiles.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of activitiWorkflowFiles in body
     */
    @GetMapping("/activiti-workflow-files")
    @Timed
    public ResponseEntity<List<ActivitiWorkflowFileDTO>> getAllActivitiWorkflowFiles(ActivitiWorkflowFileCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: {}", criteria);
        Page<ActivitiWorkflowFileDTO> page = activitiWorkflowFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/activiti-workflow-files");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /activiti-workflow-files/:id : get the "id" activitiWorkflowFile.
     *
     * @param id the id of the activitiWorkflowFileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the activitiWorkflowFileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/activiti-workflow-files/{id}")
    @Timed
    public ResponseEntity<ActivitiWorkflowFileDTO> getActivitiWorkflowFile(@PathVariable Long id) {
        log.debug("REST request to get ActivitiWorkflowFile : {}", id);
        ActivitiWorkflowFileDTO activitiWorkflowFileDTO = activitiWorkflowFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(activitiWorkflowFileDTO));
    }

    /**
     * DELETE  /activiti-workflow-files/:id : delete the "id" activitiWorkflowFile.
     *
     * @param id the id of the activitiWorkflowFileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/activiti-workflow-files/{id}")
    @Timed
    public ResponseEntity<Void> deleteActivitiWorkflowFile(@PathVariable Long id) {
        log.debug("REST request to delete ActivitiWorkflowFile : {}", id);
        activitiWorkflowFileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
