/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ProducerChannel
 * Author:   admin
 * Date:     2018/11/5 10:55
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.workflow.service.kafka.channel;

import org.springframework.messaging.MessageChannel;

public interface ProducerChannel {

    String CHANNEL = "messageChannel";

//    @Output
//    MessageChannel messageChannel();
}
