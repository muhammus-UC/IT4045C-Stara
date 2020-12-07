package com.stara.enterprise;

import com.stara.enterprise.dto.ImageURL;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ImageURLDataUnitTest {
    // Test ImageURL object created by using constructor
    @Test
    void confirmConstructor_outputsConstructor() {
        ImageURL imageURL = new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/17/44211.jpg");

        assertEquals("http://static.tvmaze.com/uploads/images/medium_portrait/17/44211.jpg", imageURL.getMedium());
        assertEquals(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/17/44211.jpg"), imageURL);
    }

    // Test ImageURL object created by using setters
    @Test
    void confirmSetter_outputsSetter() {
        ImageURL imageURL = new ImageURL();
        imageURL.setMedium("http://static.tvmaze.com/uploads/images/medium_portrait/17/44211.jpg");

        assertEquals("http://static.tvmaze.com/uploads/images/medium_portrait/17/44211.jpg", imageURL.getMedium());
        assertEquals(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/17/44211.jpg"), imageURL);
    }
}
