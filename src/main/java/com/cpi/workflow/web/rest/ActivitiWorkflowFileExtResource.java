package com.cpi.workflow.web.rest;


import com.cpi.workflow.service.ActivitiWorkflowFileQueryService;
import com.cpi.workflow.service.ActivitiWorkflowFileService;
import com.cpi.workflow.service.activiti.manage.ActivitiDeploymentService;
import com.cpi.workflow.service.dto.ActivitiWorkflowFileCriteria;
import com.cpi.workflow.service.dto.ActivitiWorkflowFileDTO;
import com.cpi.workflow.web.rest.errors.BadRequestAlertException;
import com.cpi.workflow.web.rest.util.CpiHeaderUtil;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.cpi.workflow.domain.ActivitiWorkflowFile}.
 */
@RestController
@RequestMapping("/api")
public class ActivitiWorkflowFileExtResource {

    private final Logger log = LoggerFactory.getLogger(ActivitiWorkflowFileExtResource.class);

    private static final String ENTITY_NAME = "cpiworkflowActivitiWorkflowFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private ActivitiDeploymentService activitiDeploymentService;

    private final ActivitiWorkflowFileService activitiWorkflowFileService;

    private final ActivitiWorkflowFileQueryService activitiWorkflowFileQueryService;

    public ActivitiWorkflowFileExtResource(ActivitiWorkflowFileService activitiWorkflowFileService, ActivitiWorkflowFileQueryService activitiWorkflowFileQueryService) {
        this.activitiWorkflowFileService = activitiWorkflowFileService;
        this.activitiWorkflowFileQueryService = activitiWorkflowFileQueryService;
    }


    /**
     * Deploy  /act-workflow-files/:id : deploy the "id" actWorkflowFile.
     *
     * @param id the id of the actWorkflowFileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PutMapping("/activiti-workflow-files/{id}/deploy")
    public ResponseEntity<Void> depolyActivitiWorkflowFile(@PathVariable Long id) {
        log.debug("REST request to delete ActWorkflowFile : {}", id);

        if (activitiWorkflowFileService.findOne(id) != null) {
            activitiDeploymentService.deployProcessDefinition(activitiWorkflowFileService.findOne(id).get());
        }

        return ResponseEntity.ok().headers(CpiHeaderUtil.createEntityDeployAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
