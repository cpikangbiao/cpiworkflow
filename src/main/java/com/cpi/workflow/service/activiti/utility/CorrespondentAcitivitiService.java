/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CorrespondentAcitivitiUtility
 * Author:   admin
 * Date:     2018/8/1 14:37
 * Description: Correspondent Acitiviti Utility
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.workflow.service.activiti.utility;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈Correspondent Acitiviti Utility〉
 *
 * @author admin
 * @create 2018/8/1
 * @since 1.0.0
 */

@Service
public class CorrespondentAcitivitiService extends ActivitiService {

    private final Logger log = LoggerFactory.getLogger(CorrespondentAcitivitiService.class);

    private static String ACTIVITI_WORKFLOW_FOR_CORRSPONDENT = "CorrespondentApproval";

    public ProcessInstance startProcessInstance(){
        String processDefinitionKey = ACTIVITI_WORKFLOW_FOR_CORRSPONDENT;
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstance processInstance = processEngine.getRuntimeService()//管理流程实例和执行对象，也就是表示正在执行的操作
            .startProcessInstanceByKey(processDefinitionKey);              //按照流程定义的key启动流程实例
        return processInstance;
    }



}
