package persistence;

import model.Show;
import model.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    private Show s1;
    private Show s2;
    private Show s3;
    private Show s4;

    @BeforeEach
    void runBefore() {
        s1 = new Show("Naruto", "shounen", 4,230, 500);
        s2 = new Show("Fruits Basket", "shoujo", 5, 30, 63);
        s3 = new Show("Promised Neverland", "thriller",4, 0, 25);
        s4 = new Show("Noragami", "shounen", 5, 25, 25);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            Library lb = new Library("My Library");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyLibrary() {
        try {
            Library lb = new Library("My Library");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLibrary.json");
            writer.open();
            writer.write(lb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLibrary.json");
            lb = reader.read();
            assertEquals("My Library", lb.getTitle());
            assertEquals(0, lb.getCompleted().size());
            assertEquals(0, lb.getWatching().size());
            assertEquals(0, lb.getPlanned().size());
            assertEquals(0, lb.getDropped().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralLibrary() {
        try {
            Library lb = new Library("My Library");
            lb.addToList(s1, "watching");
            lb.addToList(s2, "dropped");
            lb.addToList(s3, "planned");
            lb.addToList(s4, "completed");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLibrary.json");
            writer.open();
            writer.write(lb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralLibrary.json");
            lb = reader.read();
            assertEquals("My Library", lb.getTitle());
            List<Show> completedShows = lb.getCompleted();
            List<Show> plannedShows = lb.getPlanned();
            List<Show> watchingShows = lb.getWatching();
            List<Show> droppedShows = lb.getDropped();

            assertEquals(1, completedShows.size());
            assertEquals(1, plannedShows.size());
            assertEquals(1, watchingShows.size());
            assertEquals(1, droppedShows.size());
            checkShow("Noragami", completedShows.get(0));
            checkShow("Promised Neverland", plannedShows.get(0));
            checkShow("Naruto", watchingShows.get(0));
            checkShow("Fruits Basket", droppedShows.get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}