package com.example.predator.hi;

public class HuffazBookingClass {

    public String account, timePreference, clientAddress, choosePackage,chooseTeacher,chooseDay;

    public HuffazBookingClass(){

    }

    public HuffazBookingClass(String account, String timePreference, String clientAddress, String choosePackage, String chooseTeacher, String chooseDay) {
        this.account = account;
        this.timePreference = timePreference;
        this.clientAddress = clientAddress;
        this.choosePackage = choosePackage;
        this.chooseTeacher = chooseTeacher;
        this.chooseDay = chooseDay;
    }

    public String getAccount() {
        return account;
    }

    public String getTimePreference() {

        return timePreference;
    }

    public String getClientAddress() {

        return clientAddress;
    }

    public String getChoosePackage() {

        return choosePackage;
    }

    public String getChooseTeacher() {

        return chooseTeacher;
    }

    public String getChooseDay() {

        return chooseDay;
    }
}


