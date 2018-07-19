/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ActivitiDeploymentService
 * Author:   admin
 * Date:     2018/4/17 11:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.workflow.service.activiti;

import com.cpi.workflow.service.dto.ActivitiWorkflowFileDTO;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/4/17
 * @since 1.0.0
 */

@Service
public class ActivitiDeploymentService {

    private final Logger log = LoggerFactory.getLogger(ActivitiDeploymentService.class);

//    @Autowired
//    private RepositoryService repositoryService;
//
//    @Autowired
//    ManagementService managementService;
//    @Autowired
//    protected RuntimeService runtimeService;
//    @Autowired
//    ProcessEngineConfiguration processEngineConfiguration;
//    @Autowired
//    ProcessEngineFactoryBean processEngine;
//    @Autowired
//    HistoryService historyService;
//    @Autowired
//    TaskService taskService;

    public void deployProcessDefinition(ActivitiWorkflowFileDTO activitiWorkflowFileDTO) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        InputStream bpmnfileInputStream  = new ByteArrayInputStream(activitiWorkflowFileDTO.getProcessDefinition());
        InputStream pngfileInputStream   = new ByteArrayInputStream(activitiWorkflowFileDTO.getProcessImage());

        repositoryService.createDeployment()
            .name(activitiWorkflowFileDTO.getWorkflowName())
            .addInputStream(activitiWorkflowFileDTO.getWorkflowName()+".bpmn", bpmnfileInputStream)
            .addInputStream(activitiWorkflowFileDTO.getWorkflowName() + ".png", pngfileInputStream)
            .deploy();

        log.info("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
    }
}
