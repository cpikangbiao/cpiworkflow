package com.cpi.workflow.service;

import com.cpi.workflow.service.dto.ActivitiWorkflowFileDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.cpi.workflow.domain.ActivitiWorkflowFile}.
 */
public interface ActivitiWorkflowFileService {

    /**
     * Save a activitiWorkflowFile.
     *
     * @param activitiWorkflowFileDTO the entity to save.
     * @return the persisted entity.
     */
    ActivitiWorkflowFileDTO save(ActivitiWorkflowFileDTO activitiWorkflowFileDTO);

    /**
     * Get all the activitiWorkflowFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ActivitiWorkflowFileDTO> findAll(Pageable pageable);


    /**
     * Get the "id" activitiWorkflowFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ActivitiWorkflowFileDTO> findOne(Long id);

    /**
     * Delete the "id" activitiWorkflowFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

}
