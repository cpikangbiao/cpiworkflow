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

import com.cpi.workflow.service.activiti.common.FormPropertyBean;
import com.cpi.workflow.service.activiti.common.ProcessInstanceStatusBean;
import com.cpi.workflow.service.activiti.manage.ActivitiDeploymentService;
import com.hazelcast.spi.ExecutionService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.ReadOnlyProcessDefinition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.Instant;
import java.util.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/7/19
 * @since 1.0.0
 */

@Transactional
public abstract class ActivitiService {

    public static String VARIABLE_FOR_CREATE_USERID = "CreateUserId";

    public static String VARIABLE_FOR_ENTITY_ID = "entityId";

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private FormService formService;

    @Autowired
    private RepositoryServiceImpl repositoryService;

    /**
     * 启动流程实例
     *
     *
     * */
    public ProcessInstance startProcessInstance(String processDefinitionKey, String businessKey,  Map<String, Object> variables){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);              //按照流程定义的key启动流程实例

        return processInstance;
    }

    public void deleteProcessInstanceById(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance != null) {
            runtimeService.deleteProcessInstance(processInstanceId, null);
        }
    }

    private void suspendProcessInstanceById(String processInstanceId) {
        runtimeService.suspendProcessInstanceById(processInstanceId);
    }

    private void activateProcessInstanceById(String processInstanceId) {
        runtimeService.activateProcessInstanceById(processInstanceId);
    }

    public void activateOrSuspendProcessInstanceById(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

        if (processInstance.isSuspended()) {
            activateProcessInstanceById(processInstanceId);
        } else {
            suspendProcessInstanceById(processInstanceId);
        }
    }

    protected Object getTaskVariables(String taskId, String variableKey) {
        return (Object) taskService.getVariable(taskId, variableKey);
    }

    @Transactional
    public List<Task> getTaskList(String processDefinitionKey, String userId) {
        List<Task> tasks = taskService.createTaskQuery()
                                .processDefinitionKey(processDefinitionKey)
                                .taskAssignee(userId).list();
        return tasks;
    }

    @Transactional
    public Long getTaskListCount(String processDefinitionKey, String userId) {
        return taskService.createTaskQuery()
            .processDefinitionKey(processDefinitionKey)
            .taskAssignee(userId).count();
    }


    public List<FormPropertyBean> getTaskFormPropertyByProcessInstanceId(String prcessInstanceId) {
        Task task = taskService.createTaskQuery().processInstanceId(prcessInstanceId).singleResult();
        List<FormPropertyBean> formPropertyBeans = getTaskFormPropertyForTask(task);

//        List<String> possibleTransitionIds = new ArrayList<String>();
//
//        ReadOnlyProcessDefinition processDefinition1 = repositoryService.getDeployedProcessDefinition(task.getProcessDefinitionId());
//        PvmActivity activity = processDefinition1.findActivity(task.getTaskDefinitionKey());
//
//        for (PvmTransition pvmTransition : activity.getOutgoingTransitions()) {
//            possibleTransitionIds.add(pvmTransition.getId());
//        }
//
//        // 取得流程定义
//        ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTask(task);
//        ActivityImpl currencyActivity = processDefinition.findActivity( task.getTaskDefinitionKey());
//        List list = currencyActivity.getOutgoingTransitions();
//        // 创建新流向
//        TransitionImpl newTransition = currencyActivity.createOutgoingTransition();
        // 目标节点
//        ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
//        // 设置新流向的目标节点
//        newTransition.setDestination(pointActivity);


        return formPropertyBeans;
    }


    public List<FormPropertyBean> getTaskFormPropertyForTask(Task task) {
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormPropertyBean> formPropertyBeans = new ArrayList<>();

        for (FormProperty formProperty : taskFormData.getFormProperties()) {
            FormPropertyBean formPropertyBean =
                new FormPropertyBean(
                    formProperty.getId(),
                    formProperty.getName(),
                    formProperty.getType().getName(),
                    formProperty.getType().getInformation("values")
                );
            formPropertyBeans.add(formPropertyBean);
        }
        return formPropertyBeans;
    }

    public void completeTaskByProcessInstanceId(String prcessInstanceId, Map<String, Object> variables) {
        Task task = taskService.createTaskQuery().processInstanceId(prcessInstanceId).singleResult();
        if (task != null && task.getId() != null) {
            if (!task.isSuspended()) {
                taskService.complete(task.getId(), variables);
            }
        }
    }

    public ProcessInstanceStatusBean getProcessStatusForProcessInstanceId(String prcessInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(prcessInstanceId).singleResult();
        Task task = taskService.createTaskQuery().processInstanceId(prcessInstanceId).singleResult();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(prcessInstanceId).singleResult();
        List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery().processInstanceId(prcessInstanceId).list();

        String currentNode = "";
        String currentUserId = "";
        Instant processBeginTime = null;
        Instant processEndTime = null;
        Boolean isSuspended = false;
        Boolean isFinished  = false;
        String createUserId = "";

        if (historicVariableInstances != null && historicVariableInstances.size() > 0) {
            for (HistoricVariableInstance historicVariableInstance :  historicVariableInstances) {
                if (historicVariableInstance.getVariableName().equals(VARIABLE_FOR_CREATE_USERID)) {
                    createUserId = (String) historicVariableInstance.getValue();
                }
            }
        }

        if (processInstance != null) {
            isSuspended = processInstance.isSuspended();
        } else {
            isFinished = true;
        }

        if (historicProcessInstance != null) {
            if (historicProcessInstance.getStartTime() != null) {
                processBeginTime = historicProcessInstance.getStartTime().toInstant();
            }

            if (historicProcessInstance.getEndTime() != null) {
                processEndTime   = historicProcessInstance.getEndTime().toInstant();
            }
        }


        if (task != null) {
            currentNode = task.getName();
            currentUserId = task.getAssignee();
        } else {
            currentNode = "End";
            currentUserId = "";
        }

        return new ProcessInstanceStatusBean(
            prcessInstanceId,
            currentNode,
            currentUserId,
            processBeginTime,
            processEndTime,
            isSuspended,
            createUserId,
            isFinished
       );
    }

//    public List<> getProcessInstanceLog(String prcessInstanceId) {
//        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceId(prcessInstanceId).list();
//
//    }





    private ProcessDefinitionEntity findProcessDefinitionEntityByTask(Task task)  {
        return (ProcessDefinitionEntity) repositoryService.getDeployedProcessDefinition(task.getProcessDefinitionId());
    }

//        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(prcessInstanceId).singleResult();
//        Task task = taskService.createTaskQuery().processInstanceId(prcessInstanceId).singleResult();

//        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(prcessInstanceId).singleResult();
//
//        List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(prcessInstanceId).list();
//
//        taskService.complete(task.getId());
//
//        executions = runtimeService.createExecutionQuery().processInstanceId(prcessInstanceId).list();

//        StartFormData startFormData = formService.getStartFormData(processInstance.getProcessDefinitionId());//拿取流程启动前的表单字段。
//        EnumFormProperty
//        List<FormProperty> formProperties = startFormData.getFormProperties();//获取表单字段值
//        for (FormProperty formProperty : formProperties) {
//            FormPropertyBean formPropertyBean = new FormPropertyBean(formProperty);
//            formPropertyBeans.add(formPropertyBean);
//        }

//    @Transactional
//    protected List<SequenceFlow> findSequenceFlowsForTaskId(Task task) {
//        List<SequenceFlow> sequenceFlows = new ArrayList<>();
//
//
//        ExecutionEntity exe = (ExecutionEntity)runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
//
//        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getDeployedProcessDefinition(task.getProcessDefinitionId();
//        List<PvmTransition> pvmTransitions = processDefinitionEntity.findActivity(exe.getActivityId()).getOutgoingTransitions();
//
//
//
//        Execution parentExec = runtimeService.createProcessInstanceQuery().variableValueEquals(variableName, variableValue).singleResult();
//        RepositoryServiceImpl repoServiceImpl = (RepositoryServiceImpl) getProcessEngine().getRepositoryService();
//
//        for (Execution e : rt.createExecutionQuery().processInstanceId(parentExec.getProcessInstanceId()).list()) {
//            ExecutionEntity ee = (ExecutionEntity) e;
//            ReadOnlyProcessDefinition processDef = repoServiceImpl.getDeployedProcessDefinition(ee.getProcessDefinitionId());
//            PvmActivity activity = processDef.findActivity(ee.getActivityId());
//            List<org.activiti.engine.impl.pvm.PvmTransition> transitions = activity.getOutgoingTransitions();
//        }
//
//        return sequenceFlows;
//    }
//
//    // https://stackoverflow.com/questions/22216348/define-multiple-possible-paths-in-workflow-activiti
//    // Source: http://forums.activiti.org/content/how-get-all-possible-flows-current-activity
//    public List<String> getPossibleTransitionIds(long processInstanceId, String taskId) {
//        RepositoryServiceImpl repoServiceImpl = (RepositoryServiceImpl) repositoryService;
//
//        List<String> possibleTransitionIds = new ArrayList<String>();
//
//        ReadOnlyProcessDefinition processDef = repoServiceImpl.getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
//        PvmActivity activity = processDef.findActivity(taskId);
//
//        for (PvmTransition pvmTransition : activity.getOutgoingTransitions()) {
//            String transitionId = extractTransitionId(pvmTransition);
//            if (transitionId != null) {
//                possibleTransitionIds.add(transitionId);
//            }
//        }
//
//        return possibleTransitionIds;
//    }
//







    //查看流程图功能
    public InputStream getCurrentImageForProcessInstanceId(String processInstanceId) {
        InputStream inputStream = null;
        //获得流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
            .processInstanceId(processInstanceId).singleResult();

        String processDefinitionId;

        if (processInstance == null) {
            //查询已经结束的流程实例
            HistoricProcessInstance processInstanceHistory = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
            if (processInstanceHistory == null) {
                return null;
            } else {
                processDefinitionId = processInstanceHistory.getProcessDefinitionId();
            }
        } else {
            processDefinitionId = processInstance.getProcessDefinitionId();
        }

        //使用宋体
        String fontName = "宋体";
        //获取BPMN模型对象
        BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);

        //获取流程实例当前的节点，需要高亮显示
        List<String> currentActivities = new ArrayList<>();

        if (processInstance != null) {
            currentActivities = runtimeService.getActiveActivityIds(processInstance.getId());
        }


        inputStream =  processEngine.getProcessEngineConfiguration()
            .getProcessDiagramGenerator()
            .generateDiagram(model, "png", currentActivities,
                new ArrayList<String>(), fontName, fontName, fontName, null, 1.0);

        return inputStream;
    }



    /**
     * 查看当前任务办理人的个人任务
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

//    public void findHistoryProcessInstance() {
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        List<HistoricProcessInstance> list = processEngine.getHistoryService()
//            .createHistoricProcessInstanceQuery()
//            .processDefinitionId("testVariables:2:1704")//流程定义ID
//            .list();
//    }


    public Map<String, Object> currentTaskService(String currentTaskId) {
        Map<String, Object> taskMap = new HashMap<>();
        Task currentTask = taskService.createTaskQuery().taskId(currentTaskId).singleResult();
        HistoricTaskInstance previousTask = findPreviousTask(currentTask.getProcessInstanceId());

        taskMap.put("Current task name: ", currentTask.getName());
        taskMap.put("Previous task name: ", previousTask.getName());

        return taskMap;
    }

    // Order tasks by end date and get the latest
    public HistoricTaskInstance findPreviousTask(String processInstanceId) {
        return historyService.createHistoricTaskInstanceQuery().
            processInstanceId(processInstanceId).orderByHistoricTaskInstanceEndTime().desc().list().get(0);
    }
}
