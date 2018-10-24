/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TestResource
 * Author:   admin
 * Date:     2018/8/1 14:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.workflow.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.workflow.service.activiti.common.ProcessInstanceStatusBean;
import com.cpi.workflow.service.activiti.utility.AcitivitiServiceImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/8/1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/workflow")
public class WorkflowResource {

    private final Logger log = LoggerFactory.getLogger(WorkflowResource.class);

    @Autowired
    private AcitivitiServiceImpl acitivitiService;

    @GetMapping("/create")
    @Timed
    public ResponseEntity<String> createProcessInstance(String processDefinitionKey, String entityId, String userId) {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: ");
        ProcessInstance processInstance = acitivitiService.startProcessInstance(processDefinitionKey, entityId, userId);
        return new ResponseEntity<>(processInstance.getProcessInstanceId(), HttpStatus.OK);
    }

    @GetMapping("/get-task-list/count")
    @Timed
    public ResponseEntity<Long> getTaskListCountForUserId(String processDefinitionKey, String userId) {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: ");
        return new ResponseEntity<>(acitivitiService.countApprovalInsuredVesselIdsForUserId(processDefinitionKey, userId), HttpStatus.OK);
    }

    @GetMapping("/get-task-list")
    @Timed
    public ResponseEntity<List> getTaskListForUserId(String processDefinitionKey, String userId, Pageable page) {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: ");
        return new ResponseEntity<>(acitivitiService.getApprovalEntitylIdsForUserId(processDefinitionKey, userId, page), HttpStatus.OK);
    }

    @GetMapping("/get-task-form-property")
    @Timed
    public ResponseEntity<List> getTaskFormPropertyListForProcessInstanceId(String processInstanceId) {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: ");
        return new ResponseEntity<>(acitivitiService.getTaskFormPropertyByProcessInstanceId(processInstanceId), HttpStatus.OK);
    }


    @GetMapping("/complete")
    @Timed
    public ResponseEntity completeTaskForProcessInstanceId(String processInstanceId, Map<String, Object> variables ) {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: ");

//        variables = new HashMap<>();
//        variables.put("toNextUser", "toPiaojunlong");
//        variables.put("isAgree", "false");

        acitivitiService.completeTaskByProcessInstanceId(processInstanceId, variables);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/current-image")
    @Timed
    public ResponseEntity<byte[]> getCurrentImageForProcessInstanceId(String processInstanceId) throws IOException {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: ");

        InputStream inputStream = acitivitiService.getCurrentImageForProcessInstanceId(processInstanceId);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);

//        HttpHeaders header = new HttpHeaders();
//        header.setContentType(MediaType.parseMediaType("image/png"));
//        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=");
//
//        header.setContentLength(bytes.length);

        return new ResponseEntity<>(bytes, HttpStatus.OK);
    }


    @GetMapping("/get-process-status")
    @Timed
    public ResponseEntity<ProcessInstanceStatusBean> getProcessStatusForProcessInstanceId(String processInstanceId) {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: ");
        return new ResponseEntity<>(acitivitiService.getProcessStatusForProcessInstanceId(processInstanceId), HttpStatus.OK);
    }


    @GetMapping("/activate-or-suspend")
    @Timed
    public ResponseEntity activateOrSuspendProcessInstanceById(String processInstanceId) {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: ");
        acitivitiService.activateOrSuspendProcessInstanceById(processInstanceId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/delete")
    @Timed
    public ResponseEntity deleteProcessInstanceById(String processInstanceId) {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: ");
        acitivitiService.deleteProcessInstanceById(processInstanceId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
