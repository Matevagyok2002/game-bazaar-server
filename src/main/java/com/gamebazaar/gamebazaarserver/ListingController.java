package com.gamebazaar.gamebazaarserver;

import com.gamebazaar.gamebazaarserver.database.entities.Listing;
import com.gamebazaar.gamebazaarserver.database.entities.User;
import com.gamebazaar.gamebazaarserver.database.services.ListingService;
import com.gamebazaar.gamebazaarserver.database.services.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ListingController {

    public final UserService userService;
    public final ListingService listingService;

    public Gson gson = new Gson();

    @Autowired
    public ListingController(UserService userService, ListingService listingService) {
        this.userService = userService;
        this.listingService = listingService;
    }

    @GetMapping("/listing/{id}")
    public ResponseEntity<String> findById(@PathVariable String id){
        return ResponseEntity.ok(gson.toJson(listingService.findById(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<String> search(@RequestParam(required = false) String category,
                                         @RequestParam(required = false) String title,
                                         @RequestParam(required = false) Double maxPrice,
                                         @RequestParam(required = false) String seller){
        return ResponseEntity.ok(gson.toJson(listingService.searchByParams(category, title, maxPrice, seller)));
    }

    @PostMapping("/post-listing")
    public ResponseEntity<String> postListing(@RequestBody Listing listing){
        listingService.create(listing);
        return ResponseEntity.ok("");
    }

    @PostMapping("/edit-listing")
    public ResponseEntity<String> editListing(@RequestBody Listing listing){
        listingService.update(listing);
        return ResponseEntity.ok("");
    }

    @DeleteMapping("/delete-listing")
    public ResponseEntity<String> deleteListing(@RequestParam String id){
        listingService.delete(id);
        return ResponseEntity.ok("");
    }
}
