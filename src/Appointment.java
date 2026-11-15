import java.util.List;
import java.util.Objects;

/**
 * A single appointment: one patient, one health professional, one day and one
 * time slot.
 *
 * <p>The class holds a {@link Patient} and a {@link HealthProfessional} rather
 * than loose text, which means an appointment can be made with any type of
 * professional the system knows about - a general practitioner, a cardiologist,
 * a nurse, a dietitian - without any extra code here. Because the professional
 * is held through the general type, the appointment does not have to know or
 * care which specialty it is booked with.</p>
 *
 * <p>An appointment is only meaningful when all four details are present, so
 * the constructor refuses to build one that is missing any of them. Once
 * created, the appointment cannot be changed: fields are private and final.</p>
 *
 * <p>The class implements {@link Comparable} so that an appointment knows how
 * it orders against another one. Two appointments are compared by their time
 * slot only, which lets a whole collection be sorted from earliest to latest;
 * appointments at the same time are left in whatever order they were found,
 * which is exactly what the clinic requires.</p>
 *
 * @author  Dong Xinyang (25259366)
 * @version 1.0
 */
public class Appointment implements Comparable<Appointment> {

    /** The patient who booked the appointment. */
    private final Patient patient;

    /** The health professional the patient is booked to see. */
    private final HealthProfessional healthProfessional;

    /** The day of the appointment, which is always the current day. */
    private final Weekday day;

    /** The time of the appointment. */
    private final TimeSlot timeSlot;

    /**
     * Creates an appointment from the four details supplied.
     *
     * @param patient            the patient who booked the appointment
     * @param healthProfessional the professional the patient will see
     * @param day                the day of the appointment
     * @param timeSlot           the time of the appointment
     * @throws IllegalArgumentException if any of the four details is missing
     */
    public Appointment(Patient patient, HealthProfessional healthProfessional,
            Weekday day, TimeSlot timeSlot) {
        if (patient == null) {
            throw new IllegalArgumentException("An appointment must have a patient.");
        }
        if (healthProfessional == null) {
            throw new IllegalArgumentException("An appointment must have a health professional.");
        }
        if (day == null) {
            throw new IllegalArgumentException("An appointment must have a day.");
        }
        if (timeSlot == null) {
            throw new IllegalArgumentException("An appointment must have a time slot.");
        }
        this.patient = patient;
        this.healthProfessional = healthProfessional;
        this.day = day;
        this.timeSlot = timeSlot;
    }

    /**
     * Returns the patient who booked the appointment.
     *
     * @return the patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Returns the health professional the patient is booked to see.
     *
     * @return the health professional
     */
    public HealthProfessional getHealthProfessional() {
        return healthProfessional;
    }

    /**
     * Returns the day of the appointment.
     *
     * @return the day of the appointment
     */
    public Weekday getDay() {
        return day;
    }

    /**
     * Returns the time of the appointment.
     *
     * @return the time slot of the appointment
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * Checks whether this appointment is booked with the professional whose ID
     * is supplied.
     *
     * @param professionalId the ID to look for, digits only
     * @return true if this appointment belongs to that professional
     */
    public boolean isForProfessionalWithId(String professionalId) {
        return professionalId != null && healthProfessional.getId().equals(professionalId.trim());
    }

    /**
     * Checks whether this appointment is booked with the professional supplied.
     *
     * @param professional the professional to look for
     * @return true if this appointment belongs to that professional
     */
    public boolean isForProfessional(HealthProfessional professional) {
        return professional != null && healthProfessional.equals(professional);
    }

    /**
     * Checks whether this appointment fills the time slot supplied.
     *
     * @param slot the time to look for
     * @return true if this appointment is at that time
     */
    public boolean isAt(TimeSlot slot) {
        return slot != null && timeSlot.equals(slot);
    }

    /**
     * Checks whether this appointment belongs to the patient whose mobile
     * number is supplied.
     *
     * @param mobileNumber the mobile number to look for
     * @return true if this appointment belongs to that patient
     */
    public boolean isForPatientWithMobile(String mobileNumber) {
        return mobileNumber != null && patient.getMobileNumber().equals(mobileNumber.trim());
    }

    /**
     * Compares this appointment with another appointment in time order, so that
     * a collection of them can be sorted from earliest to latest.
     *
     * @param other the appointment to compare with
     * @return a negative number if this appointment is earlier, zero if both
     *         are at the same time, and a positive number if this one is later
     * @throws IllegalArgumentException if {@code other} is null
     */
    @Override
    public int compareTo(Appointment other) {
        if (other == null) {
            throw new IllegalArgumentException("An appointment cannot be compared with null.");
        }
        return timeSlot.compareTo(other.timeSlot);
    }

    /**
     * Checks whether this appointment and another object describe exactly the
     * same booking.
     *
     * @param other the object to compare with
     * @return true if the patient, professional, day and time all match
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Appointment)) {
            return false;
        }
        Appointment appointment = (Appointment) other;
        return patient.equals(appointment.patient)
                && healthProfessional.equals(appointment.healthProfessional)
                && day == appointment.day
                && timeSlot.equals(appointment.timeSlot);
    }

    /**
     * Returns a hash code that is consistent with {@link #equals(Object)}.
     *
     * @return the hash code of the four booking details
     */
    @Override
    public int hashCode() {
        return Objects.hash(patient, healthProfessional, day, timeSlot);
    }

    /**
     * Returns the details of the appointment in a readable form, including the
     * details of the professional that was booked.
     *
     * @return the appointment details, one item per line
     */
    @Override
    public String toString() {
        StringBuilder details = new StringBuilder();
        details.append("Appointment at ").append(timeSlot).append(" on ").append(day)
                .append(System.lineSeparator());
        details.append("    Patient            : ").append(patient).append(System.lineSeparator());
        details.append("    Health professional: ").append(healthProfessional.toSummaryLine())
                .append(System.lineSeparator());
        details.append("    Working days of the professional: ")
                .append(formatWorkingDays());
        return details.toString();
    }

    /**
     * Joins the working days of the booked professional into one readable line.
     *
     * @return the working days separated by commas
     */
    private String formatWorkingDays() {
        List<Weekday> days = healthProfessional.getWorkingDays();
        StringBuilder text = new StringBuilder();
        for (int index = 0; index < days.size(); index++) {
            if (index > 0) {
                text.append(", ");
            }
            text.append(days.get(index).getDisplayName());
        }
        return text.toString();
    }
}
