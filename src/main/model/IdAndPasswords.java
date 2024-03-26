package model;

import java.util.HashMap;
import java.util.Map;

public class IdAndPasswords {

    Map<String, String> loginInfo;

    public IdAndPasswords() {
        loginInfo = new HashMap<>();
        loginInfo.put("Hedie", "1234");
    }

    public Map getLoginInfo() {
        return loginInfo;
    }
}
