package com.cpi.workflow.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ActivitiWorkflowFile.
 */
@Entity
@Table(name = "activiti_workflow_file")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ActivitiWorkflowFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "workflow_name")
    private String workflowName;

    @Lob
    @Column(name = "process_definition")
    private byte[] processDefinition;

    @Column(name = "process_definition_content_type")
    private String processDefinitionContentType;

    @Lob
    @Column(name = "process_image")
    private byte[] processImage;

    @Column(name = "process_image_content_type")
    private String processImageContentType;

    @Column(name = "upload_time")
    private Instant uploadTime;

    @Column(name = "upload_user")
    private String uploadUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public ActivitiWorkflowFile workflowName(String workflowName) {
        this.workflowName = workflowName;
        return this;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public byte[] getProcessDefinition() {
        return processDefinition;
    }

    public ActivitiWorkflowFile processDefinition(byte[] processDefinition) {
        this.processDefinition = processDefinition;
        return this;
    }

    public void setProcessDefinition(byte[] processDefinition) {
        this.processDefinition = processDefinition;
    }

    public String getProcessDefinitionContentType() {
        return processDefinitionContentType;
    }

    public ActivitiWorkflowFile processDefinitionContentType(String processDefinitionContentType) {
        this.processDefinitionContentType = processDefinitionContentType;
        return this;
    }

    public void setProcessDefinitionContentType(String processDefinitionContentType) {
        this.processDefinitionContentType = processDefinitionContentType;
    }

    public byte[] getProcessImage() {
        return processImage;
    }

    public ActivitiWorkflowFile processImage(byte[] processImage) {
        this.processImage = processImage;
        return this;
    }

    public void setProcessImage(byte[] processImage) {
        this.processImage = processImage;
    }

    public String getProcessImageContentType() {
        return processImageContentType;
    }

    public ActivitiWorkflowFile processImageContentType(String processImageContentType) {
        this.processImageContentType = processImageContentType;
        return this;
    }

    public void setProcessImageContentType(String processImageContentType) {
        this.processImageContentType = processImageContentType;
    }

    public Instant getUploadTime() {
        return uploadTime;
    }

    public ActivitiWorkflowFile uploadTime(Instant uploadTime) {
        this.uploadTime = uploadTime;
        return this;
    }

    public void setUploadTime(Instant uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUploadUser() {
        return uploadUser;
    }

    public ActivitiWorkflowFile uploadUser(String uploadUser) {
        this.uploadUser = uploadUser;
        return this;
    }

    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivitiWorkflowFile)) {
            return false;
        }
        return id != null && id.equals(((ActivitiWorkflowFile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ActivitiWorkflowFile{" +
            "id=" + getId() +
            ", workflowName='" + getWorkflowName() + "'" +
            ", processDefinition='" + getProcessDefinition() + "'" +
            ", processDefinitionContentType='" + getProcessDefinitionContentType() + "'" +
            ", processImage='" + getProcessImage() + "'" +
            ", processImageContentType='" + getProcessImageContentType() + "'" +
            ", uploadTime='" + getUploadTime() + "'" +
            ", uploadUser='" + getUploadUser() + "'" +
            "}";
    }
}
