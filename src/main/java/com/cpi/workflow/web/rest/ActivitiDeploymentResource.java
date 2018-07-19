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

import com.cpi.workflow.service.activiti.manage.ActivitiDeploymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/7/19
 * @since 1.0.0
 */

@RestController
@RequestMapping("/api/deployment")
public class ActivitiDeploymentResource {

    private final Logger log = LoggerFactory.getLogger(ActivitiDeploymentResource.class);

    private static final String ENTITY_NAME = "ActivitiDeploymentResource";

    @Autowired
    private ActivitiDeploymentService activitiDeploymentService;



}
