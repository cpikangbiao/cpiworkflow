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
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/10/22
 * @since 1.0.0
 */
public class UWCertificateAssignmentHandler implements TaskListener {

    public void notify(DelegateTask delegateTask) {
        // get required variables
        String createUserId = (String) delegateTask.getVariable(ActivitiService.VARIABLE_FOR_CREATE_USERID);
        // check for the condition
        if (null != createUserId) {
            // set values
            delegateTask.setAssignee(createUserId);
        }
    }

}
