package com.epam.demo.spring.dbaccess.domain;

public class User {

    private long userId;
    private String userName;
    private String userPass;

    public User(final long userId, final String userName, final String userPass) {
        this.userId = userId;
        this.userName = userName;
        this.userPass = userPass;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPass() {
        return userPass;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName=" + userName +
                ", userPass=" + userPass +
                '}';
    }
}
