package com.example.auditorapp.entity;

public enum DraftStatus {
    ReadyToSend, Sending, Sent;


    public static DraftStatus byStatus(String status) {
        DraftStatus[] draftStatuses = DraftStatus.values();
        for (DraftStatus draftStatus : draftStatuses) {
            if (draftStatus.name().equals(status)) return draftStatus;
        }
        throw new IllegalArgumentException("Status with name: " + status + " is not find");
    }
}
