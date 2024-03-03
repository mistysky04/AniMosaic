package model;

import exceptions.NonSpecifiedCategoryException;
import exceptions.NonZeroNameLengthException;
import exceptions.NumIsOutOfRangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShowTest {
    private Show testShow;
    private String failString = "Exception should not have been thrown.";

    @BeforeEach
    void runBefore() {
        testShow = new Show("HunterxHunter", "shounen", 5, 144, 148);

    }

    @Test
    void testConstructor() {
        assertEquals("HunterxHunter", testShow.getName());
        assertEquals("shounen", testShow.getGenre());
        assertEquals(5, testShow.getRanking());
        assertEquals(144, testShow.getCurrentEp());
        assertEquals(148, testShow.getTotalEp());
    }

    @Test
    void testAddCommentsSingleNoExceptions() {
        try {
            testShow.addComments("Best show I've ever seen! Killua is the best character.");
            assertEquals("Best show I've ever seen! Killua is the best character.", testShow.getComments());
        } catch (NonZeroNameLengthException nzn) {
            fail(failString);
        }

    }

    @Test
    void testAddCommentsSingleEmptyStringExceptions() {
        try {
            testShow.addComments("");
            fail(failString);
        } catch (NonZeroNameLengthException nzn) {
            assertEquals("Must provide answers at least 1 character long. Please try again: ",
                    nzn.getMessage());
        }

    }

    @Test
    void testAddCommentsMultipleShouldOverwrite() {
        try {
            testShow.addComments("Hisoka is my favourite character.");
            testShow.addComments("The plot is really good!");
            assertEquals("The plot is really good!", testShow.getComments());
        } catch (NonZeroNameLengthException nzn) {
            fail(failString);
        }
    }

    @Test
    void testDeleteComments() {
        testAddCommentsSingleNoExceptions();

        testShow.deleteComments();
        assertTrue(testShow.getComments().isEmpty());
    }

    @Test
    void testToString() {
        testAddCommentsSingleNoExceptions();

        assertTrue(testShow.toString().contains("[ name: HunterxHunter\n" +
                "\tgenre: shounen\n" +
                "\tranking: 5\n" +
                "\tepisodes: 144/148\n" +
                "\tcomments: Best show I've ever seen! Killua is the best character. ]"));
    }

    @Test
    void testGetGenre() {
        assertEquals("shounen", testShow.getGenre());
    }
    @Test
    void testGetName() {
        assertEquals("HunterxHunter", testShow.getName());
    }
    @Test
    void testGetComments() {
        testAddCommentsSingleNoExceptions();

        assertEquals("Best show I've ever seen! Killua is the best character.", testShow.getComments());
    }
    @Test
    void testGetCurrentEp() {
        assertEquals(144, testShow.getCurrentEp());
    }

    @Test
    void testGetTotalEp() {
        assertEquals(148, testShow.getTotalEp());
    }

    @Test
    void testSetCurrentEp() {
        testShow.setCurrentEp(4);
        assertEquals(148, testShow.getCurrentEp());
    }
}
