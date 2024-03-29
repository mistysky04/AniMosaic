package model;

import java.util.HashMap;
import java.util.Map;

/*
    CITATIONS:

    1) Bro Code "Java Login System" tutorial --> guided creation of IdAndPasswords class
    https://www.youtube.com/watch?v=Hiv3gwJC5kw

 */

// Class holding IdAndPasswords of users (currently only 1)
public class IdAndPasswords {

    Map<String, String> loginInfo;

    // EFFECTS: creates new instance of IdAndPasswords with singular unique entry in HashMap
    public IdAndPasswords() {
        loginInfo = new HashMap<>();
        loginInfo.put("Hedie", "1234");
    }

    // EFFECTS: returns hashmap of login information
    public Map getLoginInfo() {
        return loginInfo;
    }
}
