package com.zula.database.entity;

import com.zula.database.core.BaseEntity;

import java.time.LocalDateTime;

/**
 * POJO representation of the message_inbox table for use with JDBI.
 */
public class MessageInbox extends BaseEntity {

    private String messageId;

    private String messageType;

    private String sourceService;

    private String payload;

    private String status;

    private String initiatorType;

    private String initiatorId;

    private String initiatorName;

    private String initiatorPayload;

    private LocalDateTime processedAt;

    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }

    public String getMessageType() { return messageType; }
    public void setMessageType(String messageType) { this.messageType = messageType; }

    public String getSourceService() { return sourceService; }
    public void setSourceService(String sourceService) { this.sourceService = sourceService; }

    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getInitiatorType() { return initiatorType; }
    public void setInitiatorType(String initiatorType) { this.initiatorType = initiatorType; }

    public String getInitiatorId() { return initiatorId; }
    public void setInitiatorId(String initiatorId) { this.initiatorId = initiatorId; }

    public String getInitiatorName() { return initiatorName; }
    public void setInitiatorName(String initiatorName) { this.initiatorName = initiatorName; }

    public String getInitiatorPayload() { return initiatorPayload; }
    public void setInitiatorPayload(String initiatorPayload) { this.initiatorPayload = initiatorPayload; }

    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }
}
