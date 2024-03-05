package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    private Library newLibrary;
    private Show s1;
    private Show s2;
    private Show s3;
    private Show s4;

    @BeforeEach
    void runBefore() {
        newLibrary = new Library("myLibrary");
        s1 = new Show("Naruto", "shounen", "",4,230, 500);
        s2 = new Show("Fruits Basket", "shoujo", "", 5, 30, 63);
        s3 = new Show("Promised Neverland", "thriller", "",4, 0, 25);
        s4 = new Show("Noragami", "shounen", "", 5, 25, 25);

    }

    @Test
    void testAddToListSingle() {
        newLibrary.addToList(s4, "completed");
        newLibrary.addToList(s1, "watching");
        newLibrary.addToList(s3, "planned");
        newLibrary.addToList(s2, "dropped");

        assertTrue(newLibrary.getCompleted().contains(s4));
        assertTrue(newLibrary.getWatching().contains(s1));
        assertTrue(newLibrary.getPlanned().contains(s3));
        assertTrue(newLibrary.getDropped().contains(s2));
    }

    @Test
    void testAddToListMultiple() {
        newLibrary.addToList(s4, "completed");
        newLibrary.addToList(s1, "completed");
        newLibrary.addToList(s3, "planned");
        newLibrary.addToList(s2, "planned");

        assertTrue(newLibrary.getCompleted().contains(s4));
        assertTrue(newLibrary.getCompleted().contains(s1));

        assertTrue(newLibrary.getPlanned().contains(s3));
        assertTrue(newLibrary.getPlanned().contains(s2));
    }

    @Test
    void testRemoveFromListSingle() {
        newLibrary.addToList(s4, "completed");
        newLibrary.addToList(s1, "watching");
        newLibrary.addToList(s3, "planned");
        newLibrary.addToList(s2, "dropped");

        newLibrary.removeFromList(s4);
        newLibrary.removeFromList(s1);
        newLibrary.removeFromList(s3);
        newLibrary.removeFromList(s2);

        assertFalse(newLibrary.getCompleted().contains(s4));
        assertFalse(newLibrary.getWatching().contains(s1));
        assertFalse(newLibrary.getPlanned().contains(s3));
        assertFalse(newLibrary.getDropped().contains(s2));

    }

    @Test
    void testRemoveFromListMultiple() {
        newLibrary.addToList(s4, "completed");
        newLibrary.addToList(s1, "completed");
        newLibrary.addToList(s3, "planned");
        newLibrary.addToList(s2, "planned");

        newLibrary.removeFromList(s4);
        newLibrary.removeFromList(s3);

        assertFalse(newLibrary.getCompleted().contains(s4));
        assertTrue(newLibrary.getCompleted().contains(s1));

        assertFalse(newLibrary.getPlanned().contains(s3));
        assertTrue(newLibrary.getPlanned().contains(s2));

    }

    @Test
    void testRemoveFromListShowNotFound() {
        newLibrary.addToList(s1, "completed");
        newLibrary.addToList(s3, "planned");
        newLibrary.addToList(s2, "planned");

        newLibrary.removeFromList(s4);

        assertEquals("That show is already not in your library", newLibrary.removeFromList(s4));
    }

    @Test
    void testFindCategoryNameReturnString() {
        newLibrary.addToList(s4, "completed");
        newLibrary.addToList(s1, "watching");
        newLibrary.addToList(s3, "planned");
        newLibrary.addToList(s2, "dropped");

        assertEquals("completed", newLibrary.findCategoryName(s4));
        assertEquals("watching", newLibrary.findCategoryName(s1));
        assertEquals("planned", newLibrary.findCategoryName(s3));
        assertEquals("dropped", newLibrary.findCategoryName(s2));

    }

    @Test
    void testFindCategoryNameReturnNull() {
        newLibrary.addToList(s4, "completed");
        newLibrary.addToList(s3, "planned");
        newLibrary.addToList(s2, "dropped");

        assertEquals("completed", newLibrary.findCategoryName(s4));
        assertNull(newLibrary.findCategoryName(s1));
        assertEquals("planned", newLibrary.findCategoryName(s3));
        assertEquals("dropped", newLibrary.findCategoryName(s2));

    }

    @Test
    void testFindShowReturnShow() {
        newLibrary.addToList(s4, "completed");
        newLibrary.addToList(s1, "watching");
        newLibrary.addToList(s3, "planned");
        newLibrary.addToList(s2, "dropped");

        assertEquals(s4, newLibrary.findShow("Noragami"));
        assertEquals(s1, newLibrary.findShow("Naruto"));
        assertEquals(s3, newLibrary.findShow("Promised Neverland"));
        assertEquals(s2, newLibrary.findShow("Fruits Basket"));
    }

    @Test
    void testFindShowReturnShowReturnNull() {
        newLibrary.addToList(s4, "completed");
        newLibrary.addToList(s3, "planned");
        newLibrary.addToList(s2, "dropped");

        assertEquals(s4, newLibrary.findShow("Noragami"));
        assertNull(newLibrary.findShow("Naruto"));
        assertEquals(s3, newLibrary.findShow("Promised Neverland"));
        assertEquals(s2, newLibrary.findShow("Fruits Basket"));
    }

    @Test
    void testGetCompleted() {
        newLibrary.addToList(s1, "completed");
        newLibrary.addToList(s2, "completed");
        assertEquals(s1, newLibrary.getCompleted().get(0));
        assertEquals(s2, newLibrary.getCompleted().get(1));
    }

    @Test
    void testGetWatching() {
        newLibrary.addToList(s1, "watching");
        newLibrary.addToList(s2, "watching");
        assertEquals(s1, newLibrary.getWatching().get(0));
        assertEquals(s2, newLibrary.getWatching().get(1));
    }

    @Test
    void testGetPlanned() {
        newLibrary.addToList(s1, "planned");
        newLibrary.addToList(s2, "planned");
        assertEquals(s1, newLibrary.getPlanned().get(0));
        assertEquals(s2, newLibrary.getPlanned().get(1));
    }

    @Test
    void testGetDropped() {
        newLibrary.addToList(s1, "dropped");
        newLibrary.addToList(s2, "dropped");
        assertEquals(s1, newLibrary.getDropped().get(0));
        assertEquals(s2, newLibrary.getDropped().get(1));
    }
}