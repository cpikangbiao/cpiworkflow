/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: FormPropertyBean
 * Author:   admin
 * Date:     2018/10/23 10:04
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.workflow.service.activiti.common;

import java.io.Serializable;
import java.time.Instant;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/10/23
 * @since 1.0.0
 */
public class ProcessInstanceStatusBean implements Serializable {

    private String processInstanceId;

    private String currentNode;

    private String currentUserId;

    private Instant processBeginTime;

    private Instant processEndTime;

    private Boolean isSuspended;

    private Boolean isFinished;

    private String createUserId;

    public ProcessInstanceStatusBean() {
        this.processInstanceId = null;
        this.currentNode = null;
        this.currentUserId = null;
        this.processBeginTime = null;
        this.processEndTime = null;
        this.isSuspended = false;
        this.isFinished = false;
        this.createUserId = null;
    }
    public ProcessInstanceStatusBean(String processInstanceId, String currentNode, String currentUserId, Instant processBeginTime, Instant processEndTime, Boolean isSuspended, String createUserId, Boolean isFinished) {
        this.processInstanceId = processInstanceId;
        this.currentNode = currentNode;
        this.currentUserId = currentUserId;
        this.processBeginTime = processBeginTime;
        this.processEndTime = processEndTime;
        this.isSuspended = isSuspended;
        this.isFinished = isFinished;
        this.createUserId = createUserId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(String currentNode) {
        this.currentNode = currentNode;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public Instant getProcessBeginTime() {
        return processBeginTime;
    }

    public void setProcessBeginTime(Instant processBeginTime) {
        this.processBeginTime = processBeginTime;
    }

    public Instant getProcessEndTime() {
        return processEndTime;
    }

    public void setProcessEndTime(Instant processEndTime) {
        this.processEndTime = processEndTime;
    }

    public Boolean getSuspended() {
        return isSuspended;
    }

    public void setSuspended(Boolean suspended) {
        isSuspended = suspended;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }
}
