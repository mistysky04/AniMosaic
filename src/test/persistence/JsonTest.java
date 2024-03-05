package persistence;

import model.Show;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkShow(String name, Show show) {
        assertEquals(name, show.getName());
    }
}

