package com.cpi.workflow.repository;

import com.cpi.workflow.domain.ActivitiWorkflowFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ActivitiWorkflowFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivitiWorkflowFileRepository extends JpaRepository<ActivitiWorkflowFile, Long>, JpaSpecificationExecutor<ActivitiWorkflowFile> {

}
