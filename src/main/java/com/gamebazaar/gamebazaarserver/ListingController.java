package com.gamebazaar.gamebazaarserver;

import com.gamebazaar.gamebazaarserver.database.entities.Listing;
import com.gamebazaar.gamebazaarserver.database.entities.User;
import com.gamebazaar.gamebazaarserver.database.services.ListingService;
import com.gamebazaar.gamebazaarserver.database.services.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;

@RestController
@CrossOrigin
public class ListingController {

    public final UserService userService;
    public final ListingService listingService;
    private static final String IMAGE_DIRECTORY = "src\\main\\resources\\images\\";

    private static final String[] IMAGE_EXTENSIONS = new String[]{"jpeg","png","jpg"};
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

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

    @PostMapping("/upload-image/{imageId}")
    public ResponseEntity<String> uploadImage(@RequestBody MultipartFile file,
                                              @PathVariable String imageId) {
        try {
            // Check if the file is not empty
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please upload a file");
            }

            // Get the file bytes
            byte[] bytes = file.getBytes();
            String fileExtension = file.getOriginalFilename().split("\\.")[1];

            // Define a file path to save the uploaded file (you can change this to your desired path)
            String filePath = IMAGE_DIRECTORY + imageId + "." + fileExtension;

            // Create a new file and write the bytes to it
            File newFile = new File(filePath);
            FileOutputStream fos = new FileOutputStream(newFile);
            fos.write(bytes);
            fos.close();

            return ResponseEntity.ok("File uploaded successfully: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload the file: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<String> search(@RequestParam(required = false) String category,
                                         @RequestParam(required = false) String title,
                                         @RequestParam(required = false) Double maxPrice,
                                         @RequestParam(required = false) String seller){
        return ResponseEntity.ok(gson.toJson(listingService.searchByParams(category, title, maxPrice, seller)));
    }

    @GetMapping("get-image/{imageId}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageId) {
        try {
            Path imagePath = Paths.get(IMAGE_DIRECTORY).resolve(imageId + ".png");
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.IMAGE_PNG) // Change MediaType based on your image type
                        .body(resource);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/post-listing")
    public ResponseEntity<String> postListing(@RequestBody Listing listing){
        String imageId = generateId();
        listing.imageId = imageId;
        listingService.create(listing);
        return ResponseEntity.ok(imageId);
    }

    @PostMapping("/update-listing")
    public ResponseEntity<String> editListing(@RequestBody Listing listing){
        listingService.update(listing);
        return ResponseEntity.ok("");
    }

    @DeleteMapping("/delete-listing")
    public ResponseEntity<String> deleteListing(@RequestParam String id){
        listingService.delete(id);
        return ResponseEntity.ok("");
    }

    public static String generateId() {
        StringBuilder sb = new StringBuilder(20);
        for (int i = 0; i < 20; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
}
