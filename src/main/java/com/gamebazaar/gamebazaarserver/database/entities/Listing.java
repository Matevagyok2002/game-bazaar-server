package com.gamebazaar.gamebazaarserver.database.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "listings")
public class Listing {

    @Id
    public String id;
    public String seller;
    public String[] categories;
    public Double price;
    public String title;
    public ContactInfo contactInfo;
    public String description;
    public String imageId;

    public Listing(String id, String seller, String[] categories, Double price, String title, ContactInfo contactInfo, String description, String imageId) {
        this.id = id;
        this.seller = seller;
        this.categories = categories;
        this.price = price;
        this.title = title;
        this.contactInfo = contactInfo;
        this.description = description;
        this.imageId = imageId;
    }
}
