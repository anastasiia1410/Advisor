package com.example.auditorapp.entity.user;

import com.google.gson.annotations.SerializedName;

public class User {
    private String password;
    @SerializedName("username")
    private String userName;
    private String email;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public  void clearUser(User user){
        user.setUserName(null);
        user.setEmail(null);
        user.setPassword(null);
    }

}
