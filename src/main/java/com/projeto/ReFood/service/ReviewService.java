package com.projeto.ReFood.service;

import com.projeto.ReFood.dto.ReviewDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.Review;
import com.projeto.ReFood.repository.ReviewRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private UtilityService utilityService;

    @Transactional(readOnly = true)
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReviewDTO getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new NotFoundException());
    }

    @Transactional
    public ReviewDTO createReview(@Valid ReviewDTO reviewDTO) {
        Review review = convertToEntity(reviewDTO);
        utilityService.associateUser(review::setUser, reviewDTO.userId());
        utilityService.associateRestaurant(review::setRestaurant, reviewDTO.restaurantId());
        review = reviewRepository.save(review);
        return convertToDTO(review);
    }

    @Transactional
    public ReviewDTO updateReview(Long reviewId, @Valid ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException());

        review.setRatingNote(reviewDTO.ratingNote());
        review.setRatingDate(reviewDTO.ratingDate());
        review.setRatingComment(reviewDTO.ratingComment());

        utilityService.associateUser(review::setUser, reviewDTO.userId());
        utilityService.associateRestaurant(review::setRestaurant, reviewDTO.restaurantId());

        review = reviewRepository.save(review);
        return convertToDTO(review);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new NotFoundException();
        }
        reviewRepository.deleteById(reviewId);
    }

    private ReviewDTO convertToDTO(Review review) {
        return new ReviewDTO(
                review.getReviewId(),
                review.getRatingNote(),
                review.getRatingDate(),
                review.getRatingComment(),
                review.getUser().getUserId(),
                review.getRestaurant().getRestaurantId()
        );
    }

    private Review convertToEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setReviewId(reviewDTO.reviewId());
        review.setRatingNote(reviewDTO.ratingNote());
        review.setRatingDate(reviewDTO.ratingDate());
        review.setRatingComment(reviewDTO.ratingComment());
        utilityService.associateUser(review::setUser, reviewDTO.userId());
        utilityService.associateRestaurant(review::setRestaurant, reviewDTO.restaurantId());
        return review;
    }
}