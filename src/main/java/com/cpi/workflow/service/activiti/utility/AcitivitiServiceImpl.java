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

import com.cpi.workflow.service.activiti.common.TaskRepresentation;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈Correspondent Acitiviti Utility〉
 *
 * @author admin
 * @create 2018/8/1
 * @since 1.0.0
 */

@Service
public class AcitivitiServiceImpl extends ActivitiService {

    private final Logger log = LoggerFactory.getLogger(AcitivitiServiceImpl.class);

    private static String VARIABLE_FOR_ENTITY_ID = "entityId";

    public ProcessInstance startProcessInstance(String processDefinitionKey, String entityId, String userId){
        Map<String, Object> variables = new HashMap<>();
        variables.put(VARIABLE_FOR_ENTITY_ID, entityId);
        variables.put(VARIABLE_FOR_CREATE_USERID, userId);
        ProcessInstance processInstance = startProcessInstance(processDefinitionKey, entityId, variables);

        return processInstance;
    }


    public List<String> getApprovalEntitylIdsForUserId(String processDefinitionKey, String userId, Pageable page) {
        List<Task> tasks = getTaskList(processDefinitionKey, userId);
        List<String> taskRepresentations = new ArrayList<>();

        for (Task task : tasks) {
            taskRepresentations.add((String) getTaskVariables(task.getId(), VARIABLE_FOR_ENTITY_ID));
        }

        return taskRepresentations;
    }

    public Long countApprovalInsuredVesselIdsForUserId(String processDefinitionKey, String userId) {
        List<Task> tasks = getTaskList(processDefinitionKey, userId);
        List<TaskRepresentation> taskRepresentations = new ArrayList<>();

        for (Task task : tasks) {
            taskRepresentations.add(new TaskRepresentation(task.getId(), task.getName()));
        }
        return  new Long(taskRepresentations.size());
    }

}
