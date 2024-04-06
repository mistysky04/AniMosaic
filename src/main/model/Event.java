package model;

import java.util.Calendar;
import java.util.Date;

/*

    CITATIONS:
    1) Code for Event from CPSC 210 Alarm System program
    https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/blob/main/src/main/ca/ubc/cpsc210/alarm/model/Event.java

*/

/**
 * Represents an alarm system event.
 */
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

    /**
     * EFFECTS: Creates an event with the given description
     * and the current date/time stamp.
     * @param description  a description of the event
     */
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    /**
     * EFFECTS: Gets the date of this event (includes time).
     * @return  the date of the event
     */
    public Date getDate() {
        return dateLogged;
    }

    /**
     * EFFECTS: Gets the description of this event.
     * @return  the description of the event
     */
    public String getDescription() {
        return description;
    }

    // EFFECTS: Checks if two events are equal based on dateLog and description
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    // EFFECTS: Hashcode for equals
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    // EFFECTS: returns a string of both the dateLog and description of the event
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}