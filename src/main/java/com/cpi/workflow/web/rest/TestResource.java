/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TestResource
 * Author:   admin
 * Date:     2018/8/1 14:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.workflow.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.workflow.service.activiti.utility.correspondent.CorrespondentAcitivitiService;
import com.cpi.workflow.service.dto.ActivitiWorkflowFileDTO;
import com.cpi.workflow.service.kafka.model.KafkaMessage;
import com.cpi.workflow.service.kafka.service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/8/1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/test")
public class TestResource {

    private final Logger log = LoggerFactory.getLogger(TestResource.class);

    @Autowired
    private CorrespondentAcitivitiService correspondentAcitivitiUtility;

    @Autowired
    private ProducerService producerService;

    @GetMapping("/create")
    @Timed
    public ResponseEntity<List<ActivitiWorkflowFileDTO>> createProcessInstance() {
        log.debug("REST request to get ActivitiWorkflowFiles by criteria: ");
        correspondentAcitivitiUtility.startProcessInstance();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/greetings/{count}")
    @Timed
    public void sendMessage(@PathVariable Integer count) {
        while(count > 0) {
            KafkaMessage kafkaMessage = new KafkaMessage(
                KafkaMessage.MESSAGE_TYPE_UW_CERTIFICATE,
                count.toString(),
                "生气111111"
            );
            producerService.send(kafkaMessage);

            count--;
        }
    }
}
