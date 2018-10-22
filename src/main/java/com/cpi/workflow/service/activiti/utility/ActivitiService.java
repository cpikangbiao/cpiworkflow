/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ActivitiUtility
 * Author:   admin
 * Date:     2018/7/19 13:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.workflow.service.activiti.utility;

import com.cpi.workflow.service.activiti.manage.ActivitiDeploymentService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/7/19
 * @since 1.0.0
 */

public abstract class ActivitiService {


//
//    /**
//     * 找到指定用户和指定流程实例ID的所有任务
//     * @param user 指定用户
//     * @param processInstanceId 指定流程实例ID
//     * @return
//     * 任务实例
//     */
//    public Task findSpecialProcessTaskInstance(String userId, long processInstanceId) {
//
//    }

    /**
     * 启动流程实例
     *
     *
     * */
    public ProcessInstance startProcessInstance(String processDefinitionKey){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstance processInstance = processEngine.getRuntimeService()//管理流程实例和执行对象，也就是表示正在执行的操作
            .startProcessInstanceByKey(processDefinitionKey);              //按照流程定义的key启动流程实例
        return processInstance;
    }

    /**
     * 查看当前任务办理人的个人任务
     *
     *
     *
     */

    public List<Task> findTaskList(String assignee){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> tasks = processEngine.getTaskService()//与任务相关的Service
            .createTaskQuery()                           //创建一个任务查询对象
            .taskAssignee(assignee)
            .list();
        return tasks;
    }

    public void setProcessVariables(String processInstanceId, String assignee, HashMap<String, Object> variables){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();//获取任务的Service，设置和获取流程变量

        //查询当前办理人的任务ID
        Task task = taskService.createTaskQuery()
            .processInstanceId(processInstanceId)//使用流程实例ID
            .taskAssignee(assignee)              //任务办理人
            .singleResult();

        //设置流程变量【基本类型】
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            taskService.setVariable(task.getId(), entry.getKey(), entry.getValue());
        }

    }

    public Object getProcessVariables(String processInstanceId, String assignee, String variableKey){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        //获取当前办理人的任务ID
        Task task = taskService.createTaskQuery()
            .processInstanceId(processInstanceId)
            .taskAssignee(assignee)
            .singleResult();

        //获取流程变量【基本类型】
        return  (Object) taskService.getVariable(task.getId(), variableKey);
    }

    public void findHisProcessInstance() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<HistoricProcessInstance> list = processEngine.getHistoryService()
            .createHistoricProcessInstanceQuery()
            .processDefinitionId("testVariables:2:1704")//流程定义ID
            .list();
    }
}
