package com.cpi.workflow.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.cpi.workflow.domain.ActivitiWorkflowFile} entity. This class is used
 * in {@link com.cpi.workflow.web.rest.ActivitiWorkflowFileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /activiti-workflow-files?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ActivitiWorkflowFileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter workflowName;

    private InstantFilter uploadTime;

    private StringFilter uploadUser;

    public ActivitiWorkflowFileCriteria(){
    }

    public ActivitiWorkflowFileCriteria(ActivitiWorkflowFileCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.workflowName = other.workflowName == null ? null : other.workflowName.copy();
        this.uploadTime = other.uploadTime == null ? null : other.uploadTime.copy();
        this.uploadUser = other.uploadUser == null ? null : other.uploadUser.copy();
    }

    @Override
    public ActivitiWorkflowFileCriteria copy() {
        return new ActivitiWorkflowFileCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(StringFilter workflowName) {
        this.workflowName = workflowName;
    }

    public InstantFilter getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(InstantFilter uploadTime) {
        this.uploadTime = uploadTime;
    }

    public StringFilter getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(StringFilter uploadUser) {
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
        final ActivitiWorkflowFileCriteria that = (ActivitiWorkflowFileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(workflowName, that.workflowName) &&
            Objects.equals(uploadTime, that.uploadTime) &&
            Objects.equals(uploadUser, that.uploadUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        workflowName,
        uploadTime,
        uploadUser
        );
    }

    @Override
    public String toString() {
        return "ActivitiWorkflowFileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (workflowName != null ? "workflowName=" + workflowName + ", " : "") +
                (uploadTime != null ? "uploadTime=" + uploadTime + ", " : "") +
                (uploadUser != null ? "uploadUser=" + uploadUser + ", " : "") +
            "}";
    }

}
