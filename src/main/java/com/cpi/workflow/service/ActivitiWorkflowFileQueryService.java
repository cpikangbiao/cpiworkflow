package com.cpi.workflow.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.cpi.workflow.domain.ActivitiWorkflowFile;
import com.cpi.workflow.domain.*; // for static metamodels
import com.cpi.workflow.repository.ActivitiWorkflowFileRepository;
import com.cpi.workflow.service.dto.ActivitiWorkflowFileCriteria;

import com.cpi.workflow.service.dto.ActivitiWorkflowFileDTO;
import com.cpi.workflow.service.mapper.ActivitiWorkflowFileMapper;

/**
 * Service for executing complex queries for ActivitiWorkflowFile entities in the database.
 * The main input is a {@link ActivitiWorkflowFileCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ActivitiWorkflowFileDTO} or a {@link Page} of {@link ActivitiWorkflowFileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ActivitiWorkflowFileQueryService extends QueryService<ActivitiWorkflowFile> {

    private final Logger log = LoggerFactory.getLogger(ActivitiWorkflowFileQueryService.class);


    private final ActivitiWorkflowFileRepository activitiWorkflowFileRepository;

    private final ActivitiWorkflowFileMapper activitiWorkflowFileMapper;

    public ActivitiWorkflowFileQueryService(ActivitiWorkflowFileRepository activitiWorkflowFileRepository, ActivitiWorkflowFileMapper activitiWorkflowFileMapper) {
        this.activitiWorkflowFileRepository = activitiWorkflowFileRepository;
        this.activitiWorkflowFileMapper = activitiWorkflowFileMapper;
    }

    /**
     * Return a {@link List} of {@link ActivitiWorkflowFileDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ActivitiWorkflowFileDTO> findByCriteria(ActivitiWorkflowFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ActivitiWorkflowFile> specification = createSpecification(criteria);
        return activitiWorkflowFileMapper.toDto(activitiWorkflowFileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ActivitiWorkflowFileDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ActivitiWorkflowFileDTO> findByCriteria(ActivitiWorkflowFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ActivitiWorkflowFile> specification = createSpecification(criteria);
        final Page<ActivitiWorkflowFile> result = activitiWorkflowFileRepository.findAll(specification, page);
        return result.map(activitiWorkflowFileMapper::toDto);
    }

    /**
     * Function to convert ActivitiWorkflowFileCriteria to a {@link Specifications}
     */
    private Specifications<ActivitiWorkflowFile> createSpecification(ActivitiWorkflowFileCriteria criteria) {
        Specifications<ActivitiWorkflowFile> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ActivitiWorkflowFile_.id));
            }
            if (criteria.getWorkflowName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWorkflowName(), ActivitiWorkflowFile_.workflowName));
            }
            if (criteria.getUploadTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUploadTime(), ActivitiWorkflowFile_.uploadTime));
            }
            if (criteria.getUploadUser() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUploadUser(), ActivitiWorkflowFile_.uploadUser));
            }
        }
        return specification;
    }

}
