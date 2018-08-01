/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ActivitiDeploymentResource
 * Author:   admin
 * Date:     2018/7/19 11:22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.workflow.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.workflow.web.rest.util.HeaderUtil;
import com.cpi.workflow.web.rest.util.PaginationUtil;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/7/19
 * @since 1.0.0
 */

@RestController
@RequestMapping("/api")
public class ActivitiProcessDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(ActivitiDeploymentResource.class);

    private static final String ENTITY_NAME = "ActivitiDeploymentResource";

    public ResponseEntity<List<ProcessDefinition>> getProcessDifinitionListByProcessDefinitionKey(String processDefinitionKey) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<ProcessDefinition> processDefinitions = processEngine.getRepositoryService()
            .createProcessDefinitionQuery()
            .processDefinitionKey(processDefinitionKey)// 按照流程定义的key
            .orderByProcessDefinitionVersion().desc()// 排序
            .list();// 多个结果集

        return new ResponseEntity<>(processDefinitions, HttpStatus.OK);
    }

    public ResponseEntity<List<ProcessDefinition>> getProcessDifinitionListByProcessDefinitionId(String processDefinitionId) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<ProcessDefinition> processDefinitions = processEngine.getRepositoryService()
            .createProcessDefinitionQuery()
            .processDefinitionId(processDefinitionId)//按照流程定义的ID
            .orderByProcessDefinitionVersion().desc()// 排序
            .list();// 多个结果集

        return new ResponseEntity<>(processDefinitions, HttpStatus.OK);
    }

    @DeleteMapping("/process-definition/{deploymentId}")
    @Timed
    public ResponseEntity<Void> deleteProcessDefinition(@PathVariable String deploymentId){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService().deleteDeployment(deploymentId,true);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, deploymentId)).build();
    }
}
