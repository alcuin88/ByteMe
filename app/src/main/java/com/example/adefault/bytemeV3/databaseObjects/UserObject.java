package com.example.adefault.bytemeV3.databaseObjects;

import java.util.List;

public class UserObject {

    private String userName;
    private String emailAddress;
    private String password;
    private String profileImage;
    private List<String> biteImage;
    private List<String> scanImage;
    private List<String> biteResult;

    public List<String> getBiteImage() {
        return biteImage;
    }

    public void setBiteImage(List<String> biteImage) {
        this.biteImage = biteImage;
    }

    public List<String> getScanImage() {
        return scanImage;
    }

    public void setScanImage(List<String> scanImage) {
        this.scanImage = scanImage;
    }

    public List<String> getBiteResult() {
        return biteResult;
    }

    public void setBiteResult(List<String> biteResult) {
        this.biteResult = biteResult;
    }

    public List<String> getScanResult() {
        return scanResult;
    }

    public void setScanResult(List<String> scanResult) {
        this.scanResult = scanResult;
    }

    private List<String> scanResult;

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void UserObject(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
