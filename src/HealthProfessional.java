import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The general health professional who works at the clinic.
 *
 * <p>This class holds only what <em>every</em> health professional has, no
 * matter what their specialty is: an ID, a name and the days of the week they
 * work. It is <strong>abstract</strong> on purpose. There is no such thing as
 * a health professional without a specialty, so the class cannot be
 * instantiated directly; every actual professional must be created through one
 * of the concrete subclasses in Parts 2 and 3. The abstract methods at the
 * bottom of the class also force every subclass to say what kind of
 * professional it is, what extra data it carries and what it does for a
 * patient, which is what makes the design robust as new types are added.</p>
 *
 * <p>The object is immutable: every field is private and final and there are
 * no setters. Callers can read the data through getters but cannot put the
 * object into an invalid state, and all validity checks happen once, inside
 * the constructor.</p>
 *
 * @author  Zekai Wang (25259366)
 * @version 1.0
 */
public abstract class HealthProfessional {

    /** The days worked when the caller does not supply any: Monday to Friday. */
    private static final Weekday[] DEFAULT_WORKING_DAYS = {
        Weekday.MONDAY, Weekday.TUESDAY, Weekday.WEDNESDAY, Weekday.THURSDAY, Weekday.FRIDAY
    };

    /** The ID of the professional, digits only, for example "1001". */
    private final String id;

    /** The full name of the professional, for example "Dr Alice Nguyen". */
    private final String name;

    /** The days of the week this professional works. */
    private final List<Weekday> workingDays;

    /**
     * Creates a health professional with an ID, a name and the days worked.
     * This is the full constructor: it is what the concrete subclasses call.
     *
     * @param id          the ID of the professional, digits only
     * @param name        the full name of the professional
     * @param workingDays the days of the week the professional works
     * @throws IllegalArgumentException if the ID is not digits only, if the
     *         name is empty, or if no working day is supplied
     */
    protected HealthProfessional(String id, String name, Weekday... workingDays) {
        if (!isNumeric(id)) {
            throw new IllegalArgumentException(
                    "A health professional ID must be digits only: " + id);
        }
        if (!hasText(name)) {
            throw new IllegalArgumentException("A health professional must have a name.");
        }
        if (workingDays == null || workingDays.length == 0) {
            throw new IllegalArgumentException("A health professional must work at least one day.");
        }

        List<Weekday> days = new ArrayList<>();
        for (Weekday day : workingDays) {
            if (day == null) {
                throw new IllegalArgumentException("A working day cannot be null.");
            }
            // A professional is simply not available twice on the same day, so
            // a repeated day is silently ignored instead of being stored twice.
            if (!days.contains(day)) {
                days.add(day);
            }
        }

        this.id = id.trim();
        this.name = name.trim();
        this.workingDays = days;
    }

    /**
     * Creates a health professional who works the standard Monday to Friday
     * week. This is the second, shorter way of constructing the object: the
     * caller who does not care about the roster gets a sensible default
     * instead of being forced to repeat the same five days every time.
     *
     * @param id   the ID of the professional, digits only
     * @param name the full name of the professional
     * @throws IllegalArgumentException if the ID is not digits only or if the
     *         name is empty
     */
    protected HealthProfessional(String id, String name) {
        this(id, name, DEFAULT_WORKING_DAYS);
    }

    /**
     * Returns the ID of the professional.
     *
     * @return the ID, digits only
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the full name of the professional.
     *
     * @return the name of the professional
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the days this professional works.
     *
     * <p>A copy is returned, so a caller cannot change the roster of a
     * professional through the collection it received.</p>
     *
     * @return a copy of the working days, in the order they were supplied
     */
    public List<Weekday> getWorkingDays() {
        return new ArrayList<>(workingDays);
    }

    /**
     * Checks whether this professional is rostered on the day supplied.
     *
     * @param day the day to check
     * @return true if the professional works that day
     */
    public boolean worksOn(Weekday day) {
        return workingDays.contains(day);
    }

    /**
     * Returns a one line summary of who this professional is.
     *
     * @return the type, the ID and the name, for example
     *         "[General Practitioner] ID 1001 - Dr Alice Nguyen"
     */
    public String toSummaryLine() {
        return "[" + getProfessionalType() + "] ID " + id + " - " + name;
    }

    /**
     * Returns the kind of health professional this object is, in words.
     * Every specific type must answer for itself, which is what allows a whole
     * group of different professionals to be printed through this one type.
     *
     * @return the name of the specialty, for example "General Practitioner"
     */
    public abstract String getProfessionalType();

    /**
     * Returns one or more lines of data that only this specialty records, for
     * example a licence number or a ward. The lines are added to the shared
     * details by {@link #toString()} so that no subclass has to repeat the
     * common data when it presents itself.
     *
     * @return the specialty specific details, one per line
     */
    public abstract String getSpecialistDetails();

    /**
     * Performs the clinical task this professional is able to do for the
     * patient. Each type answers differently, so the same call on the same
     * general type produces the behaviour of the object it is actually
     * pointed at.
     *
     * @param patient the patient being treated
     * @return a description of what the professional did for the patient
     */
    public abstract String performClinicalDuty(Patient patient);

    /**
     * Returns every detail of the professional in a readable form, so that
     * {@code System.out.println(professional)} prints the shared data and the
     * specialty data together.
     *
     * @return the details of the professional, one item per line
     */
    @Override
    public String toString() {
        StringBuilder details = new StringBuilder();
        details.append(toSummaryLine()).append(System.lineSeparator());
        details.append("    Working days: ").append(formatWorkingDays()).append(System.lineSeparator());
        details.append(indent(getSpecialistDetails()));
        return details.toString();
    }

    /**
     * Checks whether this professional and another object are the same
     * professional. Two objects are equal when their IDs are equal, because the
     * ID is what identifies a professional to the clinic; this is what lets the
     * booking code recognise a double booking.
     *
     * @param other the object to compare with
     * @return true if both objects have the same ID
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HealthProfessional)) {
            return false;
        }
        HealthProfessional professional = (HealthProfessional) other;
        return id.equals(professional.id);
    }

    /**
     * Returns a hash code that is consistent with {@link #equals(Object)}.
     *
     * @return the hash code of the ID
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Joins the working days into one readable line.
     *
     * @return the working days separated by commas
     */
    private String formatWorkingDays() {
        StringBuilder text = new StringBuilder();
        for (int index = 0; index < workingDays.size(); index++) {
            if (index > 0) {
                text.append(", ");
            }
            text.append(workingDays.get(index).getDisplayName());
        }
        return text.toString();
    }

    /**
     * Puts four spaces in front of every line of the text supplied, so that
     * the specialty details line up under the shared details.
     *
     * @param text the text to indent
     * @return the indented text
     */
    private static String indent(String text) {
        String separator = System.lineSeparator();
        return "    " + text.replace(separator, separator + "    ");
    }

    /**
     * Checks whether the text supplied contains at least one visible character.
     *
     * @param text the text to check
     * @return true if the text is not null and not only spaces
     */
    protected static boolean hasText(String text) {
        return text != null && !text.trim().isEmpty();
    }

    /**
     * Checks whether the text supplied is made up of digits only.
     *
     * @param text the text to check
     * @return true if the text is not null, not empty and contains digits only
     */
    protected static boolean isNumeric(String text) {
        if (text == null) {
            return false;
        }
        String trimmed = text.trim();
        if (trimmed.isEmpty()) {
            return false;
        }
        for (int index = 0; index < trimmed.length(); index++) {
            if (!Character.isDigit(trimmed.charAt(index))) {
                return false;
            }
        }
        return true;
    }
}
