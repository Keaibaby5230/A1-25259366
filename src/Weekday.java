/**
 * The days of the week on which the clinic operates and on which a health
 * professional can be rostered.
 *
 * <p>An enumeration is used rather than free text because a day can then only
 * ever hold one of the seven legal values. A typo such as "Munday" is caught by
 * the compiler instead of by a patient at the reception desk, which means the
 * working-days data of a health professional can never become invalid.</p>
 *
 * @author  Zekai Wang (25259366)
 * @version 1.0
 */
public enum Weekday {

    /** Monday. */
    MONDAY("Monday"),
    /** Tuesday. */
    TUESDAY("Tuesday"),
    /** Wednesday. */
    WEDNESDAY("Wednesday"),
    /** Thursday. */
    THURSDAY("Thursday"),
    /** Friday. */
    FRIDAY("Friday"),
    /** Saturday. */
    SATURDAY("Saturday"),
    /** Sunday. */
    SUNDAY("Sunday");

    /** The human-readable name that is shown on screen. */
    private final String displayName;

    /**
     * Creates a weekday with the name that is printed for it.
     *
     * @param displayName the readable name of the day
     */
    Weekday(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the readable name of the day.
     *
     * @return the name of the day, for example "Monday"
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the readable name of the day so that printing a weekday shows
     * something a human can read.
     *
     * @return the name of the day, for example "Monday"
     */
    @Override
    public String toString() {
        return displayName;
    }
}
