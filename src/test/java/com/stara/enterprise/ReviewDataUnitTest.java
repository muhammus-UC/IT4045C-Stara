package com.stara.enterprise;

import com.stara.enterprise.dao.review.IReviewDAO;
import com.stara.enterprise.dto.review.Review;
import com.stara.enterprise.dto.review.ReviewId;
import com.stara.enterprise.service.review.IReviewService;
import com.stara.enterprise.service.review.ReviewService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ReviewDataUnitTest {
    @Autowired
    private IReviewService reviewService;
    private Review review;
    private ReviewId reviewId;

    @Test
    void confirmShowReview_outputsShowReview() {
        Review showReview = new Review();
        showReview.setReviewId("d41d8cd98f00b204e9800998ecf8", "Show_1641");
        showReview.setRating(5);
        showReview.setFavoriteName("Black Books");

        assertEquals("d41d8cd98f00b204e9800998ecf8", showReview.getReviewId().getUid());
        assertEquals("Show_1641", showReview.getReviewId().getFavoriteId());
        assertEquals(5, showReview.getRating());
        assertEquals("Black Books", showReview.getFavoriteName());
    }

    @Test
    void confirmActorReview_outputsActorReview() {
        Review actorReview = new Review();
        actorReview.setReviewId("d41d8cd98f00b204e9800998ecf8", "Actor_40831");
        actorReview.setRating(1);
        actorReview.setFavoriteName("Ricky Gervais");

        assertEquals("d41d8cd98f00b204e9800998ecf8", actorReview.getReviewId().getUid());
        assertEquals("Actor_40831", actorReview.getReviewId().getFavoriteId());
        assertEquals(1, actorReview.getRating());
        assertEquals("Ricky Gervais", actorReview.getFavoriteName());
    }

    private void givenReviewDataAreAvailable() throws Exception {
        // Reinitialize variable to ensure data across tests doesn't cause false passes
        review = new Review();
        reviewId = new ReviewId();
    }

    @Test
    void fetchReviewById_returnsReviewForId() throws Exception {
        givenReviewDataAreAvailable();
        whenReviewShow318AddedIsCommunity();
        whenSearchReviewWithIdShow318();
        thenReturnCommunityReviewForIdShow318();
    }

    private void whenReviewShow318AddedIsCommunity() throws Exception {
        Review reviewShow = new Review();
        ReviewId reviewIdShow = new ReviewId("d41d8cd98f00b204e9800998ecf8", "Show_318");

        reviewShow.setReviewId(reviewIdShow);
        reviewShow.setRating(5);
        reviewShow.setFavoriteName("Community");

        reviewService.save(reviewShow);
    }

    private void whenSearchReviewWithIdShow318() {
        ReviewId reviewIdShow = new ReviewId("d41d8cd98f00b204e9800998ecf8", "Show_318");

        review = reviewService.fetch(reviewIdShow);
    }

    private void thenReturnCommunityReviewForIdShow318() throws Exception {
        String reviewFavoriteIdString = review.getReviewId().getFavoriteId();
        assertEquals("Show_318", reviewFavoriteIdString);
    }

    @Test
    void saveReview_validatedReturnedReview() throws Exception {
        givenReviewDataAreAvailable();
        whenUserCreatesANewReviewAndSavesIt();
        thenCreateNewReviewRecordAndReturnIt();
    }

    private void whenUserCreatesANewReviewAndSavesIt() throws Exception {
        reviewId = new ReviewId("d41d8cd98f00b204e9800998ecf8", "Actor_40831");

        review.setReviewId(reviewId);
        review.setRating(5);
        review.setFavoriteName("Ricky Gervais");

        reviewService.save(review);
    }

    private void thenCreateNewReviewRecordAndReturnIt() throws Exception {
        Review createdReview = reviewService.fetch(reviewId);

        assertEquals("Actor_40831", review.getReviewId().getFavoriteId());
        assertEquals(review, createdReview);
    }
}
