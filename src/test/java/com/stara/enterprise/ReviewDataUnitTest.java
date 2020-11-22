package com.stara.enterprise;

import com.stara.enterprise.dao.review.IReviewDAO;
import com.stara.enterprise.dto.review.Review;
import com.stara.enterprise.dto.review.ReviewId;
import com.stara.enterprise.service.review.IReviewService;
import com.stara.enterprise.service.review.ReviewService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ReviewDataUnitTest {
    private IReviewService reviewService;
    private Review review = new Review();

    @MockBean
    private IReviewDAO reviewDAO;

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

        Mockito.when(reviewDAO.save(review)).thenReturn(review);
        reviewService = new ReviewService(reviewDAO);
    }

    @Test
    void fetchReviewById_returnsReviewForId() throws Exception {
        givenReviewDataAreAvailable();
        whenReviewShow318AddedIsCommunity();
        whenSearchReviewWithIdShow318();
        thenReturnCommunityReviewForIdShow318();
    }

    private void whenReviewShow318AddedIsCommunity() {
        Review reviewShow = new Review();
        ReviewId reviewIdShow = new ReviewId("d41d8cd98f00b204e9800998ecf8", "Show_318");

        reviewShow.setReviewId(reviewIdShow);
        reviewShow.setRating(5);
        reviewShow.setFavoriteName("Community");
        Mockito.when(reviewDAO.fetch(reviewIdShow)).thenReturn(reviewShow);
    }

    private void whenSearchReviewWithIdShow318() {
        ReviewId reviewIdShow = new ReviewId("d41d8cd98f00b204e9800998ecf8", "Show_318");

        review = reviewDAO.fetch(reviewIdShow);
    }

    private void thenReturnCommunityReviewForIdShow318() throws Exception {
        String reviewFavoriteIdString = review.getReviewId().getFavoriteId();
        assertEquals("Show_318", reviewFavoriteIdString);
    }

    @Test
    void saveReview_validatedReturnedReview() throws Exception {
        givenReviewDataAreAvailable();
        whenUserCreatesANewReviewAndLaterSavesIt();
        thenCreateNewReviewRecordAndReturnIt();
    }

    private void whenUserCreatesANewReviewAndLaterSavesIt() {
        review.setReviewId("d41d8cd98f00b204e9800998ecf8", "Actor_40831");
        review.setRating(5);
        review.setFavoriteName("Ricky Gervais");
    }

    private void thenCreateNewReviewRecordAndReturnIt() throws Exception {
        Review createdReview = reviewService.save(review);
        assertEquals("Actor_40831", review.getReviewId().getFavoriteId());
        assertEquals(review, createdReview);
        verify(reviewDAO, atLeastOnce()).save(review);
    }
}
