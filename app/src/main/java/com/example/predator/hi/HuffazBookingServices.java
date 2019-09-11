package com.example.predator.hi;

public class HuffazBookingServices {

    public String eventTitle, timePreference, clientAddress, chooseReligiousPackage,chooseTeacher,chooseDay;

    public HuffazBookingServices(){

    }

    public HuffazBookingServices(String eventTitle, String timePreference, String clientAddress, String chooseReligiousPackage, String chooseTeacher, String chooseDay) {
        this.eventTitle = eventTitle;
        this.timePreference = timePreference;
        this.clientAddress = clientAddress;
        this.chooseReligiousPackage = chooseReligiousPackage;
        this.chooseTeacher = chooseTeacher;
        this.chooseDay = chooseDay;
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
}
