package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Member {

    private SimpleStringProperty SSN;
    private SimpleStringProperty name;
    private SimpleStringProperty address;
    private SimpleStringProperty phoneNumber;
    private SimpleStringProperty email;
    private SimpleBooleanProperty memberType;


    public Member(String SSN, String name, String address, String phoneNumber, String email, boolean memberType) {
        this.SSN = new SimpleStringProperty(SSN);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
        this.memberType = new SimpleBooleanProperty(memberType);
    }


    public String getSSN() {
        return SSN.get();
    }



    public String getName() {
        return name.get();
    }



    public String getAddress() {
        return address.get();
    }


    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public String getEmail() {
        return email.get();
    }

    public  String getPassword() {
        return  getPassword();
    }



    public boolean getMemberType() {
        return memberType.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
}





