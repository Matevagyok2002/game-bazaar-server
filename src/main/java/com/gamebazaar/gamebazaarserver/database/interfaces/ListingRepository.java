package com.gamebazaar.gamebazaarserver.database.interfaces;

import com.gamebazaar.gamebazaarserver.database.entities.Listing;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ListingRepository extends MongoRepository<Listing, String> {
}
