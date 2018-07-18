package com.cpi.workflow.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the ActivitiWorkflowFile entity.
 */
public class ActivitiWorkflowFileDTO implements Serializable {

    private Long id;

    private String workflowName;

    @Lob
    private byte[] processDefinition;
    private String processDefinitionContentType;

    @Lob
    private byte[] processImage;
    private String processImageContentType;

    private Instant uploadTime;

    private String uploadUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public byte[] getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(byte[] processDefinition) {
        this.processDefinition = processDefinition;
    }

    public String getProcessDefinitionContentType() {
        return processDefinitionContentType;
    }

    public void setProcessDefinitionContentType(String processDefinitionContentType) {
        this.processDefinitionContentType = processDefinitionContentType;
    }

    public byte[] getProcessImage() {
        return processImage;
    }

    public void setProcessImage(byte[] processImage) {
        this.processImage = processImage;
    }

    public String getProcessImageContentType() {
        return processImageContentType;
    }

    public void setProcessImageContentType(String processImageContentType) {
        this.processImageContentType = processImageContentType;
    }

    public Instant getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Instant uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActivitiWorkflowFileDTO activitiWorkflowFileDTO = (ActivitiWorkflowFileDTO) o;
        if(activitiWorkflowFileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activitiWorkflowFileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActivitiWorkflowFileDTO{" +
            "id=" + getId() +
            ", workflowName='" + getWorkflowName() + "'" +
            ", processDefinition='" + getProcessDefinition() + "'" +
            ", processImage='" + getProcessImage() + "'" +
            ", uploadTime='" + getUploadTime() + "'" +
            ", uploadUser='" + getUploadUser() + "'" +
            "}";
    }
}
