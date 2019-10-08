package com.example.predator.hi;

public class HuffazClient {
    public String emailAddress, userFirstName, userLastName, usersMobNumb, userStatus;

    //To read value
    public HuffazClient(){

    }

    //Constructor to read value
    public HuffazClient(String emailAddress, String userFirstName, String userLastName, String usersMobNumb, String userStatus) {
        this.emailAddress = emailAddress;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.usersMobNumb = usersMobNumb;
        this.userStatus = userStatus;
    }

    public String getUserStatus() {
        return userStatus;
    }
}
