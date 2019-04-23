package org.openjfx.Entity;

public class User {
    private String userName;
    private String password;
    private int autoLogOut;

    public User(String password) {
        this.password = password;
    }

    public User(String username, String password, int autoLogOut) {
        this.userName = username;
        this.password = password;
        this.autoLogOut = autoLogOut;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAutoLogOut() {
        return autoLogOut;
    }

    public void setAutoLogOut(int autoLogOut) {
        this.autoLogOut = autoLogOut;
    }
}
