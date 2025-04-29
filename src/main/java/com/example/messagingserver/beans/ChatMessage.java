package com.example.messagingserver.beans;

import lombok.*;


public class ChatMessage {

    private String sender;
    private String content;
    private String timestamp;
    private String recipient;
    private String filename;
    private String fileData;
    private String mimeType;

    public ChatMessage(String sender, String content, String timestamp, String recipient, String filename, String fileData, String mimeType) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.recipient = recipient;
        this.filename = filename;
        this.fileData = fileData;
        this.mimeType = mimeType;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFilename() {
        return filename;
    }

    public String getFileData() {
        return fileData;
    }

    public String getMimeType() {
        return mimeType;
    }

    public ChatMessage() {
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}