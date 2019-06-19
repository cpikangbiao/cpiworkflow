package com.cpi.workflow.web.rest;

import com.cpi.workflow.service.CpiworkflowKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/cpiworkflow-kafka")
public class CpiworkflowKafkaResource {

    private final Logger log = LoggerFactory.getLogger(CpiworkflowKafkaResource.class);

    private CpiworkflowKafkaProducer kafkaProducer;

    public CpiworkflowKafkaResource(CpiworkflowKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
