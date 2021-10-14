package com.uietsocial.kishori.model;

public class Message {
    String message,messageId,senderId,ReceiverId,read;
    private long timeStamp;


    public Message() {
    }



    public Message(String message,String messageId, String ReceiverId, String senderId, long timeStamp,String read) {
        this.message = message;
        this.ReceiverId = ReceiverId;
        this.senderId = senderId;
        this.timeStamp = timeStamp;
        this.read=read;
        this.messageId=messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
    public String getReceiverId() {
        return ReceiverId;
    }

    public void setReceiverId(String receiverId) {
        ReceiverId = receiverId;
    }
    public String getRead() {
        return read;
    }

    public void setRead(String  read) {
        this.read = read;
    }



}
