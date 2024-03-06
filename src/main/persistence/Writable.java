package persistence;

import org.json.JSONObject;

// CITATION: CPSC 210 Serialization Demo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}