package persistence;

import model.Show;
import model.Library;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Library lb = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyLibrary() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLibrary.json");
        try {
            Library lb = reader.read();
            assertEquals("My Library", lb.getTitle());
            assertEquals(0, lb.getCompleted().size());
            assertEquals(0, lb.getPlanned().size());
            assertEquals(0, lb.getWatching().size());
            assertEquals(0, lb.getDropped().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralLibrary() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralLibrary.json");
        try {
            Library lb = reader.read();
            assertEquals("My Library", lb.getTitle());
            List<Show> completedShows = lb.getCompleted();
            List<Show> plannedShows = lb.getPlanned();
            List<Show> watchingShows = lb.getWatching();
            List<Show> droppedShows = lb.getDropped();

            assertEquals(1, completedShows.size());
            assertEquals(1, plannedShows.size());
            assertEquals(1, watchingShows.size());
            assertEquals(1, droppedShows.size());
            checkShow("Naruto", completedShows.get(0));
            checkShow("Attack On Titan", plannedShows.get(0));
            checkShow("Noragami", watchingShows.get(0));
            checkShow("Hetalia", droppedShows.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
