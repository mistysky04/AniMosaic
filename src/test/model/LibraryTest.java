package model;

import exceptions.NonSpecifiedCategoryException;
import exceptions.NonZeroNameLengthException;
import exceptions.ShowNonexistentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    private Library newLibrary;
    private Show s1;
    private Show s2;
    private Show s3;
    private Show s4;

    private String failString = "Exception should not have been thrown.";

    @BeforeEach
    void runBefore() {
        newLibrary = new Library();

        s1 = new Show("Naruto", "shounen", 4, 230, 500);
        s2 = new Show("Fruits Basket", "shoujo", 5, 30, 63);
        s3 = new Show("Promised Neverland", "thriller", 4, 0, 25);
        s4 = new Show("Noragami", "shounen", 5, 25, 25);

    }

    @Test
    void testAddToListSingleNoException() {
        try {
            newLibrary.addToList(s4, "completed");
            newLibrary.addToList(s1, "watching");
            newLibrary.addToList(s3, "planned");
            newLibrary.addToList(s2, "dropped");

            assertTrue(newLibrary.getCompleted().contains(s4));
            assertTrue(newLibrary.getWatching().contains(s1));
            assertTrue(newLibrary.getPlanned().contains(s3));
            assertTrue(newLibrary.getDropped().contains(s2));
        } catch (NonSpecifiedCategoryException nse) {
            fail(failString);
        }
    }

    @Test
    void testAddToListSingleIgnoresCaseNoException() {
        try {
            newLibrary.addToList(s4, "coMpLeTed");
            newLibrary.addToList(s1, "WatCHing");
            newLibrary.addToList(s3, "PLANNED");
            newLibrary.addToList(s2, "dRopPed");

            assertTrue(newLibrary.getCompleted().contains(s4));
            assertTrue(newLibrary.getWatching().contains(s1));
            assertTrue(newLibrary.getPlanned().contains(s3));
            assertTrue(newLibrary.getDropped().contains(s2));
        } catch (NonSpecifiedCategoryException nse) {
            fail(failString);
        }
    }

    @Test
    void testAddToListSingleException() {
        try {
            newLibrary.addToList(s4, "continuing");
            fail(failString);
        } catch (NonSpecifiedCategoryException nse) {
            assertEquals("That category is not accepted. Please specify an accepted category: "
                    , nse.getMessage());
        }
    }

    @Test
    void testAddToListMultipleIgnoresCaseNoException() {
        try {
            newLibrary.addToList(s4, "completed");
            newLibrary.addToList(s1, "ComPlEted");
            newLibrary.addToList(s3, "planned");
            newLibrary.addToList(s2, "pLaNNed");

            assertTrue(newLibrary.getCompleted().contains(s4));
            assertTrue(newLibrary.getCompleted().contains(s1));

            assertTrue(newLibrary.getPlanned().contains(s3));
            assertTrue(newLibrary.getPlanned().contains(s2));
        } catch (NonSpecifiedCategoryException nse) {
            fail(failString);
        }
    }

    @Test
    void testAddToListMultipleSingleWrongCategoryException() {
        try {
            newLibrary.addToList(s4, "completed");
            newLibrary.addToList(s1, "completed");
            newLibrary.addToList(s3, "planned");
            newLibrary.addToList(s2, "Schplanned");
            fail(failString);
        } catch (NonSpecifiedCategoryException nse) {
            assertEquals("That category is not accepted. Please specify an accepted category: "
                    , nse.getMessage());
            assertTrue(newLibrary.getCompleted().contains(s4));
            assertTrue(newLibrary.getCompleted().contains(s1));
            assertTrue(newLibrary.getPlanned().contains(s3));
        }
    }

    @Test
    void testRemoveFromListSingleNoException() {
        testAddToListSingleNoException();

        try {
            newLibrary.removeFromList(s4);
            newLibrary.removeFromList(s1);
            newLibrary.removeFromList(s3);
            newLibrary.removeFromList(s2);

            assertFalse(newLibrary.getCompleted().contains(s4));
            assertFalse(newLibrary.getWatching().contains(s1));
            assertFalse(newLibrary.getPlanned().contains(s3));
            assertFalse(newLibrary.getDropped().contains(s2));
        } catch (ShowNonexistentException sne) {
            fail(failString);
        }

    }

    @Test
    void testRemoveFromListMultipleNoException() {
        testAddToListMultipleIgnoresCaseNoException();

        try {
            newLibrary.removeFromList(s4);
            newLibrary.removeFromList(s3);

            assertFalse(newLibrary.getCompleted().contains(s4));
            assertTrue(newLibrary.getCompleted().contains(s1));

            assertFalse(newLibrary.getPlanned().contains(s3));
            assertTrue(newLibrary.getPlanned().contains(s2));
        } catch (ShowNonexistentException sne) {
            fail(failString);
        }

    }

    @Test
    void testRemoveFromListShowNotFoundException() {
        try {
            newLibrary.addToList(s1, "completed");
            newLibrary.addToList(s3, "planned");
            newLibrary.addToList(s2, "planned");
        } catch (NonSpecifiedCategoryException nse) {
            fail(failString);
        }

        try {
            newLibrary.removeFromList(s4);
            fail(failString);
        } catch (ShowNonexistentException sne) {
            assertEquals("That show is not in your library. Please enter a show from the specified"
                    + "list: ", sne.getMessage());
        }

    }

    @Test
    void testFindCategoryNameReturnStringNoException() {
        testAddToListSingleNoException();

        try {
            assertEquals("completed", newLibrary.findCategoryName(s4));
            assertEquals("watching", newLibrary.findCategoryName(s1));
            assertEquals("planned", newLibrary.findCategoryName(s3));
            assertEquals("dropped", newLibrary.findCategoryName(s2));
        } catch (ShowNonexistentException sne) {
            fail(failString);
        }

    }

    @Test
    void testFindCategoryNameOfNonExistentShowException() {
        try {
            newLibrary.addToList(s4, "completed");
            newLibrary.addToList(s3, "planned");
            newLibrary.addToList(s2, "dropped");
        } catch (NonSpecifiedCategoryException nse) {
            fail(failString);
        }

        try {
            assertEquals("completed", newLibrary.findCategoryName(s4));
            assertEquals("planned", newLibrary.findCategoryName(s3));
            assertEquals("dropped", newLibrary.findCategoryName(s2));
            newLibrary.findCategoryName(s1);
            fail(failString);
        } catch (ShowNonexistentException sne) {
            assertEquals("That show is not in your library. Please enter a show from the specified"
                    + "list: ", sne.getMessage());
        }
    }

    @Test
    void testFindShowReturnShowNoException() {
        testAddToListSingleNoException();

        try {
            assertEquals(s4, newLibrary.findShow("Noragami"));
            assertEquals(s1, newLibrary.findShow("Naruto"));
            assertEquals(s3, newLibrary.findShow("Promised Neverland"));
            assertEquals(s2, newLibrary.findShow("Fruits Basket"));
        } catch (ShowNonexistentException sne) {
            fail(failString);
        }

    }

    @Test
    void testFindShowReturnShowShowNotFoundException() {
        try {
            newLibrary.addToList(s4, "completed");
            newLibrary.addToList(s3, "planned");
            newLibrary.addToList(s2, "dropped");
        } catch (NonSpecifiedCategoryException nse) {
            fail(failString);
        }

        try {
            assertEquals(s4, newLibrary.findShow("Noragami"));
            assertEquals(s3, newLibrary.findShow("Promised Neverland"));
            assertEquals(s2, newLibrary.findShow("Fruits Basket"));
            assertNull(newLibrary.findShow("Naruto"));
            fail(failString);
        } catch (ShowNonexistentException sne) {
            assertEquals("That show is not in your library. Please enter a show from the specified"
                    + "list: ", sne.getMessage());
        }
    }

    @Test
    void testGetCompleted() {
        try {
            newLibrary.addToList(s1, "completed");
        } catch (NonSpecifiedCategoryException nse) {
            fail(failString);
        }

        assertEquals(s1, newLibrary.getCompleted().get(0));
    }

    @Test
    void testGetWatching() {
        try {
            newLibrary.addToList(s1, "watching");
        } catch (NonSpecifiedCategoryException nse) {
            fail(failString);
        }

        assertEquals(s1, newLibrary.getWatching().get(0));
    }

    @Test
    void testGetPlanned() {
        try {
            newLibrary.addToList(s1, "planned");
        } catch (NonSpecifiedCategoryException nse) {
            fail(failString);
        }

        assertEquals(s1, newLibrary.getPlanned().get(0));
    }

    @Test
    void testGetDropped() {
        try {
            newLibrary.addToList(s1, "dropped");
        } catch (NonSpecifiedCategoryException nse) {
            fail(failString);
        }

        assertEquals(s1, newLibrary.getDropped().get(0));
    }

}