/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ConsumerService
 * Author:   admin
 * Date:     2018/11/5 10:55
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.workflow.service.kafka.service;

import com.cpi.workflow.service.kafka.channel.ConsumerChannel;
import com.cpi.workflow.service.kafka.model.KafkaMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    private final Logger log = LoggerFactory.getLogger(ConsumerService.class);


//    @StreamListener(ConsumerChannel.CHANNEL)
//    public void consume(String kafkaMessage) {
//
//        log.info("Received message: {}.", kafkaMessage);
//    }
}
