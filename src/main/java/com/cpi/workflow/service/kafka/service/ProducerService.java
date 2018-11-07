/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ProducerService
 * Author:   admin
 * Date:     2018/11/5 10:56
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.workflow.service.kafka.service;

import com.cpi.workflow.service.kafka.channel.ProducerChannel;
import com.cpi.workflow.service.kafka.model.KafkaMessage;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/11/5
 * @since 1.0.0
 */
@Service
public class ProducerService {

    private MessageChannel channel;

    public ProducerService(ProducerChannel channel) {
        this.channel = channel.messageChannel();
    }

    public void send(KafkaMessage kafkaMessage) {
        this.channel.send(
            MessageBuilder
                .withPayload(kafkaMessage)
//                .setHeader("content-type","application/json;charset=utf-8")
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_OCTET_STREAM)
                .build());
    }
}
