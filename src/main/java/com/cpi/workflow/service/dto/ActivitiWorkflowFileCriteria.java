package com.cpi.workflow.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the ActivitiWorkflowFile entity. This class is used in ActivitiWorkflowFileResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /activiti-workflow-files?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ActivitiWorkflowFileCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter workflowName;

    private InstantFilter uploadTime;

    private StringFilter uploadUser;

    public ActivitiWorkflowFileCriteria() {
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
    public String toString() {
        return "ActivitiWorkflowFileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (workflowName != null ? "workflowName=" + workflowName + ", " : "") +
                (uploadTime != null ? "uploadTime=" + uploadTime + ", " : "") +
                (uploadUser != null ? "uploadUser=" + uploadUser + ", " : "") +
            "}";
    }

}
