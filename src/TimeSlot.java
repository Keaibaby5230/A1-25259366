import java.util.Objects;

/**
 * A single appointment time, held as a whole hour plus a number of minutes.
 *
 * <p>The time is stored as two integers instead of as a string such as
 * "09:30". A string cannot be ordered correctly - "10:00" would sort before
 * "09:30" - whereas the integers can be turned into a single count of minutes
 * past midnight and compared numerically. The class is immutable and
 * implements {@link Comparable}, so a collection of appointments can be sorted
 * by time with the standard Java sorting tools.</p>
 *
 * @author  Zekai Wang (25259366)
 * @version 1.0
 */
public final class TimeSlot implements Comparable<TimeSlot> {

    /** Number of minutes in one hour. */
    private static final int MINUTES_PER_HOUR = 60;

    /** The largest legal hour of the day. */
    private static final int LAST_HOUR = 23;

    /** The largest legal minute of the hour. */
    private static final int LAST_MINUTE = 59;

    /** The hour of the day, from 0 to 23. */
    private final int hour;

    /** The minute of the hour, from 0 to 59. */
    private final int minute;

    /**
     * Creates a time slot on the hour and minute supplied.
     *
     * @param hour   the hour of the day, from 0 to 23
     * @param minute the minute of the hour, from 0 to 59
     * @throws IllegalArgumentException if the hour or the minute is out of range
     */
    public TimeSlot(int hour, int minute) {
        if (hour < 0 || hour > LAST_HOUR) {
            throw new IllegalArgumentException("Hour must be between 0 and 23: " + hour);
        }
        if (minute < 0 || minute > LAST_MINUTE) {
            throw new IllegalArgumentException("Minute must be between 0 and 59: " + minute);
        }
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Creates a time slot that falls exactly on the hour.
     * This is the second way of constructing the object: the caller who only
     * cares about the hour does not have to supply the minutes.
     *
     * @param hour the hour of the day, from 0 to 23
     * @throws IllegalArgumentException if the hour is out of range
     */
    public TimeSlot(int hour) {
        this(hour, 0);
    }

    /**
     * Returns the hour of this slot.
     *
     * @return the hour of the day, from 0 to 23
     */
    public int getHour() {
        return hour;
    }

    /**
     * Returns the minute of this slot.
     *
     * @return the minute of the hour, from 0 to 59
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Converts this slot into one number so that two slots can be compared.
     *
     * @return the number of minutes between midnight and this slot
     */
    public int toMinutesSinceMidnight() {
        return hour * MINUTES_PER_HOUR + minute;
    }

    /**
     * Compares this slot with another slot in time order.
     *
     * @param other the slot to compare with
     * @return a negative number if this slot is earlier, zero if the two slots
     *         are at the same time, and a positive number if this slot is later
     * @throws IllegalArgumentException if {@code other} is null
     */
    @Override
    public int compareTo(TimeSlot other) {
        if (other == null) {
            throw new IllegalArgumentException("A time slot cannot be compared with null.");
        }
        return Integer.compare(toMinutesSinceMidnight(), other.toMinutesSinceMidnight());
    }

    /**
     * Checks whether this slot and another object are the same time.
     *
     * @param other the object to compare with
     * @return true if both objects describe the same hour and minute
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TimeSlot)) {
            return false;
        }
        TimeSlot slot = (TimeSlot) other;
        return hour == slot.hour && minute == slot.minute;
    }

    /**
     * Returns a hash code that is consistent with {@link #equals(Object)}.
     *
     * @return the hash code of this slot
     */
    @Override
    public int hashCode() {
        return Objects.hash(hour, minute);
    }

    /**
     * Returns the time in the 24-hour form used by the clinic, for example
     * "09:30".
     *
     * @return the time of this slot as text
     */
    @Override
    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }
}
