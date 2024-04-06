package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/*

    CITATIONS:
    1) Code for EventLog from CPSC 210 Alarm System program
    https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/blob/main/src/main/ca/ubc/cpsc210/alarm/model/EventLog.java

*/

/**
 * Represents a log of alarm system events.
 * We use the Singleton Design Pattern to ensure that there is only
 * one EventLog in the system and that the system has global access
 * to the single instance of the EventLog.
 */
public class EventLog implements Iterable<Event> {
    /** the only EventLog in the system (Singleton Design Pattern) */
    private static EventLog theLog;
    private Collection<Event> events;

    /**
     * EFFECTS: Prevent external construction.
     * (Singleton Design Pattern).
     */
    private EventLog() {
        events = new ArrayList<Event>();
    }

    /**
     * EFFECTS: Gets instance of EventLog - creates it
     * if it doesn't already exist.
     * (Singleton Design Pattern)
     * @return  instance of EventLog
     */
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }

        return theLog;
    }

    /**
     * MODIFIES: This
     * EFFECTS: Adds an event to the event log.
     * @param e the event to be added
     */
    public void logEvent(Event e) {
        events.add(e);
    }

    /**
     * MODIFIES: this
     * EFFECTS: Clears the event log and logs the event.
     */
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    // EFFECTS: returns all events in eventlog
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}