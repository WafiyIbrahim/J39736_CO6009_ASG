package com.example.predator.hi.Models;

public class HuffazBookingServices {

    public String account, eventTitle, timePreference, clientAddress, chooseReligiousPackage,chooseTeacher,chooseDay, bookingStatus;

    public HuffazBookingServices(){

    }

    public HuffazBookingServices(String account, String eventTitle, String timePreference, String clientAddress, String chooseReligiousPackage, String chooseTeacher, String chooseDay, String bookingStatus) {
        this.account = account;
        this.eventTitle = eventTitle;
        this.timePreference = timePreference;
        this.clientAddress = clientAddress;
        this.chooseReligiousPackage = chooseReligiousPackage;
        this.chooseTeacher = chooseTeacher;
        this.chooseDay = chooseDay;
        this.bookingStatus = bookingStatus;
    }

    public String getAccount() {

        return account;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getTimePreference() {
        return timePreference;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public String getChooseReligiousPackage() {
        return chooseReligiousPackage;
    }

    public String getChooseTeacher() {
        return chooseTeacher;
    }

    public String getChooseDay() {
        return chooseDay;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }
}
