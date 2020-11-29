package com.stara.enterprise;

import com.stara.enterprise.dto.ImageURLs;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ImageURLsDataUnitTest {
    // Test ImageURLs object created by using constructor
    @Test
    void confirmConstructor_outputsConstructor() {
        ImageURLs imageURLs = new ImageURLs("http://static.tvmaze.com/uploads/images/medium_portrait/17/44211.jpg");

        assertEquals("http://static.tvmaze.com/uploads/images/medium_portrait/17/44211.jpg", imageURLs.getMedium());
        assertEquals(new ImageURLs("http://static.tvmaze.com/uploads/images/medium_portrait/17/44211.jpg"), imageURLs);
    }

    // Test ImageURLs object created by using setters
    @Test
    void confirmSetter_outputsSetter() {
        ImageURLs imageURLs = new ImageURLs();
        imageURLs.setMedium("http://static.tvmaze.com/uploads/images/medium_portrait/17/44211.jpg");

        assertEquals("http://static.tvmaze.com/uploads/images/medium_portrait/17/44211.jpg", imageURLs.getMedium());
        assertEquals(new ImageURLs("http://static.tvmaze.com/uploads/images/medium_portrait/17/44211.jpg"), imageURLs);
    }
}
