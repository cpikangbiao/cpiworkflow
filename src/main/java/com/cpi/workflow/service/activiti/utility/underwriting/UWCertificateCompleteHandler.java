/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: UWCertificateAssignmentHandler
 * Author:   admin
 * Date:     2018/10/22 15:15
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.workflow.service.activiti.utility.underwriting;

import com.cpi.workflow.service.activiti.utility.ActivitiService;
import com.cpi.workflow.service.kafka.model.KafkaMessage;
import com.cpi.workflow.service.kafka.service.ProducerService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.delegate.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/10/22
 * @since 1.0.0
 */
@Component("uwCertificateCompleteHandler")
public class UWCertificateCompleteHandler implements JavaDelegate {

    @Autowired
    private ProducerService producerService;

    // Order tasks by end date and get the latest
    private HistoricTaskInstance findPreviousTask(String processInstanceId) {
        return ProcessEngines.getDefaultProcessEngine().getHistoryService().createHistoricTaskInstanceQuery().
            processInstanceId(processInstanceId).orderByHistoricTaskInstanceEndTime().desc().list().get(0);
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        // get required variables
        String entryId = (String) delegateExecution.getVariable(ActivitiService.VARIABLE_FOR_ENTITY_ID);
        HistoricTaskInstance historicTaskInstance = findPreviousTask(delegateExecution.getProcessInstanceId());
        // check for the condition
        if ( entryId != null
            && producerService != null
            && historicTaskInstance != null) {

            // set values
            KafkaMessage kafkaMessage = new KafkaMessage(
                KafkaMessage.MESSAGE_TYPE_UW_CERTIFICATE,
                entryId,
                historicTaskInstance.getAssignee()
            );

            producerService.send(kafkaMessage);
        }
    }
}
