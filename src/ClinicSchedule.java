import java.util.ArrayList;
import java.util.List;

/**
 * The fixed daily timetable that the clinic books appointments into.
 *
 * <p>The timetable is not hard-coded into the appointment code. It is built
 * once from a start hour, an end hour and a slot length, and the rest of the
 * system only ever asks this object whether a time is a bookable slot. If the
 * clinic ever changes its hours, only the arguments given to this constructor
 * have to change; the booking logic keeps working unchanged.</p>
 *
 * @author  Zekai Wang (25259366)
 * @version 1.0
 */
public class ClinicSchedule {

    /** The hour the clinic opens by default. */
    public static final int DEFAULT_START_HOUR = 8;

    /** The hour the clinic closes by default. */
    public static final int DEFAULT_END_HOUR = 16;

    /** The length of one appointment slot, in minutes, by default. */
    public static final int DEFAULT_SLOT_MINUTES = 30;

    /** Number of minutes in one hour. */
    private static final int MINUTES_PER_HOUR = 60;

    /** The hour the first appointment of the day starts. */
    private final int startHour;

    /** The hour by which the last appointment of the day has finished. */
    private final int endHour;

    /** The length of one appointment slot, in minutes. */
    private final int slotMinutes;

    /** Every bookable slot of the day, in time order. */
    private final List<TimeSlot> slots;

    /**
     * Creates the standard clinic timetable of 08:00 to 16:00 in half-hour
     * slots.
     */
    public ClinicSchedule() {
        this(DEFAULT_START_HOUR, DEFAULT_END_HOUR, DEFAULT_SLOT_MINUTES);
    }

    /**
     * Creates a timetable from the clinic hours and slot length supplied.
     *
     * @param startHour   the hour the clinic opens
     * @param endHour     the hour the clinic closes
     * @param slotMinutes the length of one slot, in minutes
     * @throws IllegalArgumentException if the slot length is not positive, if
     *         the clinic closes before it opens, or if the hours produce no
     *         usable slot
     */
    public ClinicSchedule(int startHour, int endHour, int slotMinutes) {
        if (slotMinutes <= 0) {
            throw new IllegalArgumentException("Slot length must be at least one minute: " + slotMinutes);
        }
        if (startHour < 0 || startHour > 23 || endHour < 1 || endHour > 24 || endHour <= startHour) {
            throw new IllegalArgumentException(
                    "The clinic must open and close within one day: " + startHour + " to " + endHour);
        }

        this.startHour = startHour;
        this.endHour = endHour;
        this.slotMinutes = slotMinutes;
        this.slots = new ArrayList<>();

        for (int minutes = startHour * MINUTES_PER_HOUR;
                minutes < endHour * MINUTES_PER_HOUR;
                minutes += slotMinutes) {
            slots.add(new TimeSlot(minutes / MINUTES_PER_HOUR, minutes % MINUTES_PER_HOUR));
        }
        if (slots.isEmpty()) {
            throw new IllegalArgumentException("The clinic hours do not contain a single slot.");
        }
    }

    /**
     * Returns every bookable slot of the day, in time order.
     *
     * <p>A copy is returned so that calling code cannot remove or add slots
     * and thereby change the clinic timetable behind the manager's back.</p>
     *
     * @return a copy of the slots of the day
     */
    public List<TimeSlot> getSlots() {
        return new ArrayList<>(slots);
    }

    /**
     * Checks whether the time supplied is one of the clinic's slots.
     *
     * @param slot the time to check
     * @return true if the clinic books appointments at that time today
     */
    public boolean hasSlot(TimeSlot slot) {
        return slot != null && slots.contains(slot);
    }

    /**
     * Returns the hour the clinic opens.
     *
     * @return the opening hour
     */
    public int getStartHour() {
        return startHour;
    }

    /**
     * Returns the hour the clinic closes.
     *
     * @return the closing hour
     */
    public int getEndHour() {
        return endHour;
    }

    /**
     * Returns the length of one appointment slot.
     *
     * @return the slot length, in minutes
     */
    public int getSlotMinutes() {
        return slotMinutes;
    }

    /**
     * Describes the timetable in one line, for example
     * "08:00-16:00 in 30-minute slots (08:00 ... 15:30)".
     *
     * @return a readable description of the timetable
     */
    public String describe() {
        return String.format("%02d:00-%02d:00 in %d-minute slots (%s ... %s)",
                startHour, endHour, slotMinutes, slots.get(0), slots.get(slots.size() - 1));
    }

    /**
     * Returns the description of the timetable.
     *
     * @return a readable description of the timetable
     */
    @Override
    public String toString() {
        return describe();
    }
}
