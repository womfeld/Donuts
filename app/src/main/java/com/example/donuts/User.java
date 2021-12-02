package com.example.donuts;

import java.io.Serializable;

public class User implements Serializable {


    private int id;
    private String first;
    private String last;
    private String userName;
    private String password;
    private String isBanned;
    private int rewardPoints;
    private String role;

    private String invalidLoginMessage;




    public User(String userName, String password, String first, String last, String isBanned, int rewardPoints, String role) {

        this.userName = userName;
        this.password = password;
        this.first = first;
        this.last = last;
        this.isBanned = isBanned;
        this.rewardPoints = rewardPoints;
        this.role = role;

        //Not in parameters but best to leave it blank
        invalidLoginMessage = "";

    }

    public User (String invalidLogin) {

        this.invalidLoginMessage = invalidLogin;

    }

    public User() {

        invalidLoginMessage = "";

    }

    public int getId() {
        return this.id;
    }

    public String getFirst() {
        return this.first;
    }

    public String getLast() {
        return this.last;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getIsBanned(){
        return this.isBanned;
    }

    public int getRewardPoints() {
        return this.rewardPoints;
    }

    public String getRole() {
        return this.role;
    }

    public void setId(int i) {
        this.id = i;
    }

    public void setFirst(String f) {
        this.first = f;
    }

    public void setLast(String l) {
        this.last = l;
    }

    public void setUserName(String u) {
        this.userName = u;
    }

    public void setPassword(String p) {
        this.password = p;
    }

    public void setIsBanned(String b) {
        this.isBanned = b;
    }

    public void setRewardPoints(int r) {
        this.rewardPoints = r;
    }

    private void setRole(String r) {
        this.role = r;
    }


}
