package com.cpi.workflow.repository;

import com.cpi.workflow.domain.ActivitiWorkflowFile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ActivitiWorkflowFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivitiWorkflowFileRepository extends JpaRepository<ActivitiWorkflowFile, Long>, JpaSpecificationExecutor<ActivitiWorkflowFile> {

}
