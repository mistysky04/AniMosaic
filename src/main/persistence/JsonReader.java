package persistence;

import model.Library;
import model.Show;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// CITATION: CPSC 210 Serialization Demo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads library from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Library from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Library read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLibrary(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses library from JSON object and returns it
    private Library parseLibrary(JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        Library lb = new Library(title);
        addCompleted(lb, jsonObject);
        addWatching(lb, jsonObject);
        addPlanned(lb, jsonObject);
        addDropped(lb, jsonObject);
        return lb;
    }

    // MODIFIES: lb
    // EFFECTS: parses "completed" field from JSON object and adds them to library
    private void addCompleted(Library lb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("completed");
        for (Object json : jsonArray) {
            JSONObject nextShow = (JSONObject) json;
            addShowCompleted(lb, nextShow);
        }
    }

    // MODIFIES: lb
    // EFFECTS: parses "watching" list from JSON object and adds them to library
    private void addWatching(Library lb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("watching");
        for (Object json : jsonArray) {
            JSONObject nextShow = (JSONObject) json;
            addShowWatching(lb, nextShow);
        }
    }

    // MODIFIES: lb
    // EFFECTS: parses "planned" list from JSON object and adds them to library
    private void addPlanned(Library lb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("planned");
        for (Object json : jsonArray) {
            JSONObject nextShow = (JSONObject) json;
            addShowPlanned(lb, nextShow);
        }
    }

    // MODIFIES: lb
    // EFFECTS: parses "dropped" list from JSON object and adds them to library
    private void addDropped(Library lb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("dropped");
        for (Object json : jsonArray) {
            JSONObject nextShow = (JSONObject) json;
            addShowDropped(lb, nextShow);
        }
    }

    // MODIFIES: lb
    // EFFECTS: parses show from JSON object and adds it to "completed" list
    private void addShowCompleted(Library lb, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String genre = jsonObject.getString("genre");
        String comments = jsonObject.getString("comments");
        int ranking = jsonObject.getInt("ranking");
        int totalEp = jsonObject.getInt("totalEp");
        int currentEp = jsonObject.getInt("currentEp");

        Show show = new Show(name, genre, comments, ranking, currentEp, totalEp);
        lb.addToList(show, "completed");
    }

    // MODIFIES: lb
    // EFFECTS: parses show from JSON object and adds it to "watching" list
    private void addShowWatching(Library lb, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String genre = jsonObject.getString("genre");
        String comments = jsonObject.getString("comments");
        int ranking = jsonObject.getInt("ranking");
        int totalEp = jsonObject.getInt("totalEp");
        int currentEp = jsonObject.getInt("currentEp");

        Show show = new Show(name, genre, comments, ranking, currentEp, totalEp);
        lb.addToList(show, "watching");
    }

    // MODIFIES: lb
    // EFFECTS: parses show from JSON object and adds it to "planned" list
    private void addShowPlanned(Library lb, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String genre = jsonObject.getString("genre");
        String comments = jsonObject.getString("comments");
        int ranking = jsonObject.getInt("ranking");
        int totalEp = jsonObject.getInt("totalEp");
        int currentEp = jsonObject.getInt("currentEp");

        Show show = new Show(name, genre, comments, ranking, currentEp, totalEp);
        lb.addToList(show, "planned");
    }

    // MODIFIES: lb
    // EFFECTS: parses show from JSON object and adds it to "dropped" list
    private void addShowDropped(Library lb, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String genre = jsonObject.getString("genre");
        String comments = jsonObject.getString("comments");
        int ranking = jsonObject.getInt("ranking");
        int totalEp = jsonObject.getInt("totalEp");
        int currentEp = jsonObject.getInt("currentEp");

        Show show = new Show(name, genre, comments, ranking, currentEp, totalEp);
        lb.addToList(show, "dropped");
    }
}