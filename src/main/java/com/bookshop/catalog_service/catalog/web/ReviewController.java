package com.bookshop.catalog_service.catalog.web;

import com.bookshop.catalog_service.catalog.domain.Review;
import com.bookshop.catalog_service.catalog.domain.ReviewService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reviews")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewController {

    ReviewService reviewService;

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("{id}")
    public Review getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    @GetMapping("book/{bookId}")
    public List<Review> getReviewsByBookId(@PathVariable Long bookId) {
        return reviewService.getReviewsByBookId(bookId);
    }

    @PostMapping("book/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Review addReview(@PathVariable Long bookId,
                            @Valid @RequestBody ReviewRequest reviewRequest,
                            @AuthenticationPrincipal Jwt jwt) {
        return reviewService.addReviewToBook(bookId, reviewRequest, jwt);
    }

    @PutMapping("{reviewId}")
    public Review updateReview(@PathVariable Long reviewId,
                               @Valid @RequestBody ReviewRequest reviewRequest) {
        return reviewService.updateReview(reviewId, reviewRequest);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }

    @GetMapping("book/{bookId}/average-rating")
    public Double getAverageRating(@PathVariable Long bookId) {
        return reviewService.getAverageRating(bookId);
    }
}