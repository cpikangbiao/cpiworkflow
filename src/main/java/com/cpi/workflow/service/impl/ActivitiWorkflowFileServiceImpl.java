package com.cpi.workflow.service.impl;

import com.cpi.workflow.service.ActivitiWorkflowFileService;
import com.cpi.workflow.domain.ActivitiWorkflowFile;
import com.cpi.workflow.repository.ActivitiWorkflowFileRepository;
import com.cpi.workflow.service.dto.ActivitiWorkflowFileDTO;
import com.cpi.workflow.service.mapper.ActivitiWorkflowFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ActivitiWorkflowFile.
 */
@Service
@Transactional
public class ActivitiWorkflowFileServiceImpl implements ActivitiWorkflowFileService {

    private final Logger log = LoggerFactory.getLogger(ActivitiWorkflowFileServiceImpl.class);

    private final ActivitiWorkflowFileRepository activitiWorkflowFileRepository;

    private final ActivitiWorkflowFileMapper activitiWorkflowFileMapper;

    public ActivitiWorkflowFileServiceImpl(ActivitiWorkflowFileRepository activitiWorkflowFileRepository, ActivitiWorkflowFileMapper activitiWorkflowFileMapper) {
        this.activitiWorkflowFileRepository = activitiWorkflowFileRepository;
        this.activitiWorkflowFileMapper = activitiWorkflowFileMapper;
    }

    /**
     * Save a activitiWorkflowFile.
     *
     * @param activitiWorkflowFileDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ActivitiWorkflowFileDTO save(ActivitiWorkflowFileDTO activitiWorkflowFileDTO) {
        log.debug("Request to save ActivitiWorkflowFile : {}", activitiWorkflowFileDTO);
        ActivitiWorkflowFile activitiWorkflowFile = activitiWorkflowFileMapper.toEntity(activitiWorkflowFileDTO);
        activitiWorkflowFile = activitiWorkflowFileRepository.save(activitiWorkflowFile);
        return activitiWorkflowFileMapper.toDto(activitiWorkflowFile);
    }

    /**
     * Get all the activitiWorkflowFiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ActivitiWorkflowFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ActivitiWorkflowFiles");
        return activitiWorkflowFileRepository.findAll(pageable)
            .map(activitiWorkflowFileMapper::toDto);
    }

    /**
     * Get one activitiWorkflowFile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ActivitiWorkflowFileDTO findOne(Long id) {
        log.debug("Request to get ActivitiWorkflowFile : {}", id);
        ActivitiWorkflowFile activitiWorkflowFile = activitiWorkflowFileRepository.findOne(id);
        return activitiWorkflowFileMapper.toDto(activitiWorkflowFile);
    }

    /**
     * Delete the activitiWorkflowFile by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActivitiWorkflowFile : {}", id);
        activitiWorkflowFileRepository.delete(id);
    }
}
