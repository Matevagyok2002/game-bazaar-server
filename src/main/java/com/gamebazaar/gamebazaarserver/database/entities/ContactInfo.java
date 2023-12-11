package com.gamebazaar.gamebazaarserver.database.entities;

public class ContactInfo {

    public String phoneNumber;
    public String firstName;
    public String lastName;
    public String email;
    public String address;
    public String postalCode;

    public ContactInfo(String phoneNumber, String firstName, String lastName, String email, String address, String postalCode) {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.postalCode = postalCode;
    }
}
