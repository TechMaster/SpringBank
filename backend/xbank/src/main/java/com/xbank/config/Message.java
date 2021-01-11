package com.xbank.config;

public class Message {
    private String targetId;
    private String messageText;
    private String userId;

    public Message(String targetId, String messageText, String userId) {
        this.targetId = targetId;
        this.messageText = messageText;
        this.userId = userId;
    }

    public Message() {
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
