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

import com.cpi.workflow.service.activiti.utility.AcitivitiServiceImpl;
import com.cpi.workflow.service.activiti.utility.ActivitiService;
import com.cpi.workflow.service.kafka.model.KafkaMessage;
import com.cpi.workflow.service.kafka.service.ProducerService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/10/22
 * @since 1.0.0
 */
public class UWCertificateCompleteHandler implements TaskListener {

    @Autowired
    private AcitivitiServiceImpl acitivitiService;

    @Autowired
    private ProducerService producerService;

    public void notify(DelegateTask delegateTask) {
        // get required variables
        String entryId = (String) delegateTask.getVariable(ActivitiService.VARIABLE_FOR_ENTITY_ID);
        // check for the condition
        if (null != entryId) {
            // set values
            KafkaMessage kafkaMessage = new KafkaMessage(
                KafkaMessage.MESSAGE_TYPE_UW_CERTIFICATE,
                entryId,
                acitivitiService.findPreviousTask(delegateTask.getProcessInstanceId()).getAssignee()
            );

            producerService.send(kafkaMessage);
        }
    }

}
