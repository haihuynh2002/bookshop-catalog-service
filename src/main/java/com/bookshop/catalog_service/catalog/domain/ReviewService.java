package com.bookshop.catalog_service.catalog.domain;

import com.bookshop.catalog_service.catalog.web.ReviewRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewService {

    ReviewRepository reviewRepository;
    BookRepository bookRepository;
    ReviewMapper reviewMapper;

    public List<Review> getReviewsByBookId(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException(bookId.toString());
        }
        return reviewRepository.findByBookId(bookId);
    }

    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId.toString()));
    }

    @Transactional
    public Review addReviewToBook(Long bookId, ReviewRequest reviewRequest, Jwt jwt) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId.toString()));

        Review review = reviewMapper.toReview(reviewRequest);
        review.setBook(book);
        review.setFirstName(jwt.getClaim(StandardClaimNames.GIVEN_NAME));
        review.setLastName(jwt.getClaim(StandardClaimNames.FAMILY_NAME));
        review.setEmail(jwt.getClaim(StandardClaimNames.EMAIL));
        return reviewRepository.save(review);
    }

    @Transactional
    public Review updateReview(Long reviewId, ReviewRequest reviewRequest) {
        return reviewRepository.findById(reviewId)
                .map(existingReview -> {
                    reviewMapper.updateReview(existingReview, reviewRequest);
                    return reviewRepository.save(existingReview);
                })
                .orElseThrow(() -> new ReviewNotFoundException(reviewId.toString()));
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new ReviewNotFoundException(reviewId.toString());
        }
        reviewRepository.deleteById(reviewId);
    }

    public Double getAverageRating(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException(bookId.toString());
        }

        List<Review> reviews = reviewRepository.findByBookId(bookId);
        if (reviews.isEmpty()) {
            return 0.0;
        }

        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
}