package com.gamebazaar.gamebazaarserver.database.services;

import com.gamebazaar.gamebazaarserver.database.entities.Listing;
import com.gamebazaar.gamebazaarserver.database.entities.User;
import com.gamebazaar.gamebazaarserver.database.interfaces.ListingRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final MongoOperations mongoOperations;

    @Autowired
    public ListingService(ListingRepository listingRepository,
                          MongoOperations mongoOperations) {
        this.listingRepository = listingRepository;
        this.mongoOperations = mongoOperations;}

    public List<Listing> searchByParams(String category, String title, String maxPrice, String seller){
        Query query = new Query();

        if (category != null && !category.equals("-")) {
            query.addCriteria(Criteria.where("categories").in(category));
        }

        if (title != null && !title.equals("-")) {
            Pattern regex = Pattern.compile(title, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
            query.addCriteria(Criteria.where("title").regex(regex));
        }

        if (maxPrice != null && !maxPrice.equals("-")) {
            query.addCriteria(Criteria.where("price").lte(Double.parseDouble(maxPrice)));
        }

        if (seller != null && !seller.equals("-")) {
            query.addCriteria(Criteria.where("seller").is(seller));
        }

        return mongoOperations.find(query, Listing.class);
    }

    public Listing findById(String id){return listingRepository.findById(id).get();}

    public void create(Listing listing) {
        listingRepository.save(listing);
    }

    public void update(Listing listing) {
        listing.id = new ObjectId(listing.id).toString();
        listingRepository.save(listing);
    }

    public void delete(String id) {
        listingRepository.deleteById(new ObjectId(id).toString());
    }

    public List<Listing> findAll() {
        return listingRepository.findAll();
    }
}
