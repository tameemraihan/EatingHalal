package com.eatinghalal.web;

import java.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {
  @PostMapping
  public ResponseEntity<Map<String, String>> upload(@RequestParam("photo") MultipartFile photo) {
    if (photo.isEmpty()
        || !Set.of("image/jpeg", "image/png", "image/webp")
            .contains(Objects.toString(photo.getContentType(), "")))
      return ResponseEntity.badRequest()
          .body(Map.of("message", "Choose a non-empty JPEG, PNG or WebP image."));
    return ResponseEntity.status(503)
        .body(
            Map.of(
                "message",
                "Photo assessment is not implemented yet. No image was stored or sent to an"
                    + " external service."));
  }
}
