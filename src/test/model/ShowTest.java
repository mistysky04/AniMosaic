package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShowTest {
    private Show testShow;

    @BeforeEach
    void runBefore() {
        testShow = new Show("HunterxHunter", "shounen", 5, 148, 148);
    }

    @Test
    void testConstructor() {
        assertEquals("HunterxHunter", testShow.getName());
        assertEquals("shounen", testShow.getGenre());
        assertEquals(5, testShow.getRanking());
        assertEquals(148, testShow.getCurrentEp());
        assertEquals(148, testShow.getTotalEp());
    }

    @Test
    void testAddCommentsSingle() {
        testShow.addComments("Best show I've ever seen! Killua is the best character.");
        assertEquals("Best show I've ever seen! Killua is the best character.", testShow.getComments());
    }

    @Test
    void testAddCommentsMultiple() {
        testShow.addComments("Hisoka is my favourite character.");
        testShow.addComments("The plot is really good!");
        assertEquals("The plot is really good!", testShow.getComments());
    }

    @Test
    void testDeleteComments() {
        testShow.addComments("Hisoka is my favourite character.");
        testShow.deleteComments();
        assertTrue(testShow.getComments().isEmpty());
    }

    @Test
    void testToString() {
        testShow.addComments("Best show ever!");
        assertTrue(testShow.toString().contains("[ name = HunterxHunter, genre = shounen, ranking = 5,  " +
                "current episode = 148/148, comments = Best show ever! ]"));
    }
}
