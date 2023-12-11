package com.gamebazaar.gamebazaarserver.database.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "listings")
public class Listing {

    @Id
    public String id;
    public String sellerId;
    public String[] categories;
    public Double price;
    public String title;
    public ContactInfo contactInfo;
    public String description;
    public String imagesId;

    public Listing(String id, String sellerId, String[] categories, Double price, String title, ContactInfo contactInfo, String description, String imagesId) {
        this.id = id;
        this.sellerId = sellerId;
        this.categories = categories;
        this.price = price;
        this.title = title;
        this.contactInfo = contactInfo;
        this.description = description;
        this.imagesId = imagesId;
    }
}
