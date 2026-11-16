import java.util.Objects;

/**
 * A patient who books an appointment at the clinic.
 *
 * <p>A patient is modelled as its own class rather than as a pair of strings
 * inside an appointment, because a patient is a real thing in the problem
 * domain with its own rules: a name and a mobile number that must be a valid
 * contact number. Keeping these rules in one place means every appointment
 * that holds a patient automatically holds a valid one.</p>
 *
 * @author  Zekai Wang (25259366)
 * @version 1.0
 */
public class Patient {

    /** The number of digits in an Australian mobile number, for example 0412345678. */
    private static final int MOBILE_DIGITS = 10;

    /** The prefix that every Australian mobile number starts with. */
    private static final String MOBILE_PREFIX = "04";

    /** The patient's full name. */
    private final String name;

    /** The patient's mobile phone number, for example "0412345678". */
    private final String mobileNumber;

    /**
     * Creates a patient with the details supplied.
     *
     * @param name         the patient's full name
     * @param mobileNumber the patient's mobile phone number, ten digits
     *                     starting with 04
     * @throws IllegalArgumentException if the name is empty or the mobile
     *         number is not a valid Australian mobile number
     */
    public Patient(String name, String mobileNumber) {
        this.name = validateName(name);
        this.mobileNumber = validateMobileNumber(mobileNumber);
    }

    /**
     * Returns the patient's full name.
     *
     * @return the patient's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the patient's mobile phone number.
     *
     * @return the mobile number as ten digits
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * Checks that the name is present and trims it.
     *
     * @param name the name supplied by the caller
     * @return the cleaned name
     * @throws IllegalArgumentException if the name is missing
     */
    private static String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("A patient must have a name.");
        }
        return name.trim();
    }

    /**
     * Checks that the number is a ten digit Australian mobile number and trims
     * it.
     *
     * @param mobileNumber the number supplied by the caller
     * @return the cleaned number
     * @throws IllegalArgumentException if the number is not a valid mobile
     *         number
     */
    private static String validateMobileNumber(String mobileNumber) {
        if (mobileNumber == null || mobileNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("A patient must have a mobile phone number.");
        }
        String trimmed = mobileNumber.trim();
        if (trimmed.length() != MOBILE_DIGITS) {
            throw new IllegalArgumentException(
                    "A mobile phone number must have " + MOBILE_DIGITS + " digits: " + trimmed);
        }
        for (int index = 0; index < trimmed.length(); index++) {
            if (!Character.isDigit(trimmed.charAt(index))) {
                throw new IllegalArgumentException("A mobile phone number may only contain digits: " + trimmed);
            }
        }
        if (!trimmed.startsWith(MOBILE_PREFIX)) {
            throw new IllegalArgumentException("An Australian mobile number must start with 04: " + trimmed);
        }
        return trimmed;
    }

    /**
     * Checks whether this patient and another object are the same patient.
     * The mobile number identifies a patient, because it is the detail the
     * clinic uses to recognise a caller.
     *
     * @param other the object to compare with
     * @return true if both objects have the same mobile number
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Patient)) {
            return false;
        }
        Patient patient = (Patient) other;
        return mobileNumber.equals(patient.mobileNumber);
    }

    /**
     * Returns a hash code that is consistent with {@link #equals(Object)}.
     *
     * @return the hash code of the mobile number
     */
    @Override
    public int hashCode() {
        return Objects.hash(mobileNumber);
    }

    /**
     * Returns the patient's details in one line.
     *
     * @return the name followed by the mobile number in brackets
     */
    @Override
    public String toString() {
        return name + " (" + mobileNumber + ")";
    }
}
