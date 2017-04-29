package com.example.yukicalendar.model;


public class UserCalendar {

    private String displayName;
    private String accountName = null;
    private String ownerName = null;
    private long calID;

    public UserCalendar(long calID, String displayName, String accountName, String ownerName) {
        this.calID = calID;
        this.displayName = displayName;
        this.accountName = accountName;
        this.ownerName = ownerName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getCalID() {
        return calID;
    }

}
