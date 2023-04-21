package com.example.happymind;

public class Users {
    String profilePic,mail,name,password,userId,status;

    public Users() {
    }

    public Users(String profilePic, String mail, String name, String password, String userId, String status) {
        this.profilePic = profilePic;
        this.mail = mail;
        this.name = name;
        this.password = password;
        this.userId = userId;

        this.status = status;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
