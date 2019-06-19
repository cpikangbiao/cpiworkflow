package com.cpi.workflow.service.mapper;

import com.cpi.workflow.domain.*;
import com.cpi.workflow.service.dto.ActivitiWorkflowFileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ActivitiWorkflowFile} and its DTO {@link ActivitiWorkflowFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ActivitiWorkflowFileMapper extends EntityMapper<ActivitiWorkflowFileDTO, ActivitiWorkflowFile> {



    default ActivitiWorkflowFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        ActivitiWorkflowFile activitiWorkflowFile = new ActivitiWorkflowFile();
        activitiWorkflowFile.setId(id);
        return activitiWorkflowFile;
    }
}
