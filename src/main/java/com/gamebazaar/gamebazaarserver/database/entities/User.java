package com.gamebazaar.gamebazaarserver.database.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    public String id;

    public String username;

    public String password;

    public ContactInfo contactInfo;

    public User(String id, String username, String password, ContactInfo contactInfo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.contactInfo = contactInfo;
    }
}
