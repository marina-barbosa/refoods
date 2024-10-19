package com.projeto.ReFood.controller;

import com.projeto.ReFood.service.ReviewService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.ReFood.dto.ReviewDTO;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

  @Autowired
  private ReviewService reviewService;

  @GetMapping
  public ResponseEntity<List<ReviewDTO>> listAllReviews() {
    List<ReviewDTO> reviews = reviewService.getAllReviews();
    return ResponseEntity.ok(reviews);
  }

  @GetMapping("/{reviewId}")
  public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long reviewId) {
    ReviewDTO reviewDTO = reviewService.getReviewById(reviewId);
    return ResponseEntity.ok(reviewDTO);
  }

  @PostMapping
  public ResponseEntity<ReviewDTO> createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
    ReviewDTO createdReview = reviewService.createReview(reviewDTO);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{reviewId}")
        .buildAndExpand(createdReview.reviewId())
        .toUri();
    return ResponseEntity.created(location).body(createdReview);
  }

  @PutMapping("/{reviewId}")
  public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long reviewId, @Valid @RequestBody ReviewDTO reviewDTO) {
    ReviewDTO updatedReview = reviewService.updateReview(reviewId, reviewDTO);
    return ResponseEntity.ok(updatedReview);
  }

  @DeleteMapping("/{reviewId}")
  public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
    reviewService.deleteReview(reviewId);
    return ResponseEntity.noContent().build();
  }
}
