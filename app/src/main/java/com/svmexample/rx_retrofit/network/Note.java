package com.svmexample.rx_retrofit.network;

public class Note extends BaseResponse {
    int id;
    String note;
    String timestamp;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
