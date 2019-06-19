package com.cpi.workflow.web.rest;

import com.cpi.workflow.service.ActivitiWorkflowFileService;
import com.cpi.workflow.web.rest.errors.BadRequestAlertException;
import com.cpi.workflow.service.dto.ActivitiWorkflowFileDTO;
import com.cpi.workflow.service.dto.ActivitiWorkflowFileCriteria;
import com.cpi.workflow.service.ActivitiWorkflowFileQueryService;

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
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.cpi.workflow.domain.ActivitiWorkflowFile}.
 */
@RestController
@RequestMapping("/api")
public class ActivitiWorkflowFileResource {

    private final Logger log = LoggerFactory.getLogger(ActivitiWorkflowFileResource.class);

    private static final String ENTITY_NAME = "cpiworkflowActivitiWorkflowFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivitiWorkflowFileService activitiWorkflowFileService;

    private final ActivitiWorkflowFileQueryService activitiWorkflowFileQueryService;

    public ActivitiWorkflowFileResource(ActivitiWorkflowFileService activitiWorkflowFileService, ActivitiWorkflowFileQueryService activitiWorkflowFileQueryService) {
        this.activitiWorkflowFileService = activitiWorkflowFileService;
        this.activitiWorkflowFileQueryService = activitiWorkflowFileQueryService;
    }

    /**
     * {@code POST  /activiti-workflow-files} : Create a new activitiWorkflowFile.
     *
     * @param activitiWorkflowFileDTO the activitiWorkflowFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activitiWorkflowFileDTO, or with status {@code 400 (Bad Request)} if the activitiWorkflowFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/activiti-workflow-files")
    public ResponseEntity<ActivitiWorkflowFileDTO> createActivitiWorkflowFile(@RequestBody ActivitiWorkflowFileDTO activitiWorkflowFileDTO) throws URISyntaxException {
        log.debug("REST request to save ActivitiWorkflowFile : {}", activitiWorkflowFileDTO);
        if (activitiWorkflowFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new activitiWorkflowFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivitiWorkflowFileDTO result = activitiWorkflowFileService.save(activitiWorkflowFileDTO);
        return ResponseEntity.created(new URI("/api/activiti-workflow-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /activiti-workflow-files} : Updates an existing activitiWorkflowFile.
     *
     * @param activitiWorkflowFileDTO the activitiWorkflowFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activitiWorkflowFileDTO,
     * or with status {@code 400 (Bad Request)} if the activitiWorkflowFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activitiWorkflowFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/activiti-workflow-files")
    public ResponseEntity<ActivitiWorkflowFileDTO> updateActivitiWorkflowFile(@RequestBody ActivitiWorkflowFileDTO activitiWorkflowFileDTO) throws URISyntaxException {
        log.debug("REST request to update ActivitiWorkflowFile : {}", activitiWorkflowFileDTO);
        if (activitiWorkflowFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActivitiWorkflowFileDTO result = activitiWorkflowFileService.save(activitiWorkflowFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activitiWorkflowFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /activiti-workflow-files} : get all the activitiWorkflowFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activitiWorkflowFiles in body.
     */
    @GetMapping("/activiti-workflow-files")
    public ResponseEntity<List<ActivitiWorkflowFileDTO>> getAllActivitiWorkflowFiles(ActivitiWorkflowFileCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: {}", criteria);
        Page<ActivitiWorkflowFileDTO> page = activitiWorkflowFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /activiti-workflow-files/count} : count all the activitiWorkflowFiles.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/activiti-workflow-files/count")
    public ResponseEntity<Long> countActivitiWorkflowFiles(ActivitiWorkflowFileCriteria criteria) {
        log.debug("REST request to count ActivitiWorkflowFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(activitiWorkflowFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /activiti-workflow-files/:id} : get the "id" activitiWorkflowFile.
     *
     * @param id the id of the activitiWorkflowFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activitiWorkflowFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/activiti-workflow-files/{id}")
    public ResponseEntity<ActivitiWorkflowFileDTO> getActivitiWorkflowFile(@PathVariable Long id) {
        log.debug("REST request to get ActivitiWorkflowFile : {}", id);
        Optional<ActivitiWorkflowFileDTO> activitiWorkflowFileDTO = activitiWorkflowFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activitiWorkflowFileDTO);
    }

    /**
     * {@code DELETE  /activiti-workflow-files/:id} : delete the "id" activitiWorkflowFile.
     *
     * @param id the id of the activitiWorkflowFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/activiti-workflow-files/{id}")
    public ResponseEntity<Void> deleteActivitiWorkflowFile(@PathVariable Long id) {
        log.debug("REST request to delete ActivitiWorkflowFile : {}", id);
        activitiWorkflowFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
