package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;


import static org.junit.jupiter.api.Assertions.*;

public class IdAndPasswordsTest {

    private IdAndPasswords myIdAndPasswordTest;
    private HashMap<String, String> myTestHashMap;

    @BeforeEach
    void runBefore() {
        myIdAndPasswordTest = new IdAndPasswords();
        myTestHashMap = new HashMap<>();
        myTestHashMap.put("Hedie", "1234");
    }

    @Test
    void testConstructor() {
        assertEquals(myTestHashMap.get("Hedie"), myIdAndPasswordTest.getLoginInfo().get("Hedie"));
    }

    @Test
    void testGetLoginInfo() {
        assertEquals(myTestHashMap, myIdAndPasswordTest.getLoginInfo());
    }
}
