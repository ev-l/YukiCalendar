package com.example.yukicalendar.model;


public class UserAccount {

    private String accountName;

    public UserAccount(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccount that = (UserAccount) o;

        return accountName != null ? accountName.equals(that.accountName) : that.accountName == null;
    }

    @Override
    public int hashCode() {
        return accountName != null ? accountName.hashCode() : 0;
    }
}
