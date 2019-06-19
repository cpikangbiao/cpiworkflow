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


import com.cpi.workflow.service.activiti.common.ProcessInstanceStatusBean;
import com.cpi.workflow.service.activiti.utility.AcitivitiServiceImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> createProcessInstance(@RequestParam(value = "processDefinitionKey", required = true) String processDefinitionKey,
                                                        @RequestParam(value = "entityId", required = true) String entityId,
                                                        @RequestParam(value = "userId", required = true) String userId) {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: ");
        ProcessInstance processInstance = acitivitiService.startProcessInstance(processDefinitionKey, entityId, userId);
        return new ResponseEntity<>(processInstance.getProcessInstanceId(), HttpStatus.OK);
    }

    @GetMapping("/get-task-list/count")
    public ResponseEntity<Long> getTaskListCountForUserId(@RequestParam(value = "processDefinitionKey", required = true) String processDefinitionKey,
                                                          @RequestParam(value = "userId", required = true) String userId) {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: ");
        return new ResponseEntity<>(acitivitiService.countApprovalInsuredVesselIdsForUserId(processDefinitionKey, userId), HttpStatus.OK);
    }

//    @GetMapping("/get-task-list")
//    @Timed
//    public ResponseEntity<List> getTaskListForUserId(@RequestParam(value = "processDefinitionKey", required = true) String processDefinitionKey,
//                                                     @RequestParam(value = "userId", required = true) String userId,
//                                                     @RequestParam(value = "page", required = true) Pageable page) {
//        log.debug("REST request to get ActivitiWorkflowFiles by criteria: ");
//        return new ResponseEntity<>(acitivitiService.getApprovalEntitylIdsForUserId(processDefinitionKey, userId, page), HttpStatus.OK);
//    }

    @GetMapping("/get-task-list")
    public ResponseEntity<List> getTaskListForUserId(@RequestParam(value = "processDefinitionKey", required = true) String processDefinitionKey,
                                                     @RequestParam(value = "userId", required = true) String userId) {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: ");
        return new ResponseEntity<>(acitivitiService.getApprovalEntitylIdsForUserId(processDefinitionKey, userId, null), HttpStatus.OK);
    }

    @GetMapping("/get-task-form-property")
    public ResponseEntity<List> getTaskFormPropertyListForProcessInstanceId(@RequestParam(value = "processInstanceId", required = true) String processInstanceId) {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: ");
        return new ResponseEntity<>(acitivitiService.getTaskFormPropertyByProcessInstanceId(processInstanceId), HttpStatus.OK);
    }


    @PostMapping("/complete")
    public ResponseEntity completeTaskForProcessInstanceId(@RequestParam("processInstanceId") String processInstanceId,
                                                           @RequestBody Map<String, Object> variables ) {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: ");

//        variables = new HashMap<>();
//        variables.put("toNextUser", "toPiaojunlong");
//        variables.put("isAgree", "false");

        acitivitiService.completeTaskByProcessInstanceId(processInstanceId, variables);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/current-image")
    public ResponseEntity<byte[]> getCurrentImageForProcessInstanceId(@RequestParam(value = "processInstanceId", required = true) String processInstanceId) throws IOException {
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
    public ResponseEntity<ProcessInstanceStatusBean> getProcessStatusForProcessInstanceId(@RequestParam(value = "processInstanceId", required = true) String processInstanceId) {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: ");
        return new ResponseEntity<>(acitivitiService.getProcessStatusForProcessInstanceId(processInstanceId), HttpStatus.OK);
    }


    @GetMapping("/activate-or-suspend")
    public ResponseEntity activateOrSuspendProcessInstanceById(@RequestParam(value = "processInstanceId", required = true) String processInstanceId) {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: ");
        acitivitiService.activateOrSuspendProcessInstanceById(processInstanceId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/delete")
    public ResponseEntity deleteProcessInstanceById(@RequestParam(value = "processInstanceId", required = true) String processInstanceId) {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: ");
        acitivitiService.deleteProcessInstanceById(processInstanceId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
