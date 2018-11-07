/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: UWCertificateMessage
 * Author:   admin
 * Date:     2018/11/5 9:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.workflow.service.kafka.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/11/5
 * @since 1.0.0
 */
public class KafkaMessage implements Serializable{

    public static final String MESSAGE_TYPE_UW_CERTIFICATE = "UW-Certificate";

    private String messageType;

    private String entryId;

    private String userId;

    public KafkaMessage(String messageType, String entryId, String userId) {
        this.messageType = messageType;
        this.entryId = entryId;
        this.userId = userId;
    }

    public KafkaMessage() {

    }

    @Override
    public String toString() {
        return "KafkaMessage{" +
            "messageType='" + messageType + '\'' +
            ", entryId='" + entryId + '\'' +
            ", userId='" + userId + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KafkaMessage)) return false;
        KafkaMessage that = (KafkaMessage) o;
        return Objects.equals(getMessageType(), that.getMessageType()) &&
            Objects.equals(getEntryId(), that.getEntryId()) &&
            Objects.equals(getUserId(), that.getUserId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getMessageType(), getEntryId(), getUserId());
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
