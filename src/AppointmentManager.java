import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the whole collection of appointments for the clinic.
 *
 * <p>The appointments are kept in an {@link ArrayList}. An
 * {@code ArrayList} is the right data structure here because the clinic needs
 * to walk through every appointment, filter some of them out and print the
 * result in time order - all of which are position based operations that an
 * ordered, index addressed collection handles well, and the number of
 * appointments in one day is small enough that searching through it is not a
 * performance concern. Sorting is not the job of the collection itself: each
 * {@link Appointment} already knows how it compares with another one, so the
 * standard sort can be applied to the whole collection or to any filtered
 * subset.</p>
 *
 * <p>Every operation that can fail returns a message instead of throwing an
 * exception at the user. A caller therefore always has something to show: a
 * "SUCCESS" line when the booking was made or cancelled, or a "WARNING" line
 * explaining exactly why it was not.</p>
 *
 * @author  Zekai Wang (25259366)
 * @version 1.0
 */
public class AppointmentManager {

    /** Every appointment currently held by the clinic. */
    private final List<Appointment> appointments;

    /** The timetable that decides which times may be booked. */
    private final ClinicSchedule schedule;

    /**
     * Creates a manager for the standard clinic timetable.
     */
    public AppointmentManager() {
        this(new ClinicSchedule());
    }

    /**
     * Creates a manager for the timetable supplied.
     *
     * @param schedule the timetable appointments must fit into
     * @throws IllegalArgumentException if no timetable is supplied
     */
    public AppointmentManager(ClinicSchedule schedule) {
        if (schedule == null) {
            throw new IllegalArgumentException("An appointment manager needs a clinic schedule.");
        }
        this.schedule = schedule;
        this.appointments = new ArrayList<>();
    }

    /**
     * Books a new appointment if every rule allows it.
     *
     * <p>An appointment is only added when all four details are supplied, the
     * time is one of the clinic's slots, the professional works that day and
     * the professional is not already booked at that time. The last check is
     * what stops a professional from being double booked: two appointments
     * match when they are at the same time for the same professional, and the
     * professional is recognised through {@link HealthProfessional#equals}
     * (that is, by ID), so this works for every type of professional.</p>
     *
     * @param patient            the patient booking the appointment
     * @param healthProfessional the professional the patient wants to see
     * @param day                the day of the appointment
     * @param timeSlot           the time of the appointment
     * @return a message saying whether the appointment was booked, or why not
     */
    public String addAppointment(Patient patient, HealthProfessional healthProfessional,
            Weekday day, TimeSlot timeSlot) {
        if (patient == null || healthProfessional == null || day == null || timeSlot == null) {
            return "WARNING: an appointment needs a patient, a health professional, a day and a time slot.";
        }
        if (!schedule.hasSlot(timeSlot)) {
            return "WARNING: " + timeSlot + " is not one of the clinic's time slots. "
                    + "The clinic runs " + schedule.describe() + ".";
        }
        if (!healthProfessional.worksOn(day)) {
            return "WARNING: " + healthProfessional.getName() + " (ID " + healthProfessional.getId()
                    + ") does not work on " + day + ". Working days: "
                    + healthProfessional.getWorkingDaysText() + ".";
        }
        if (isBooked(healthProfessional, day, timeSlot)) {
            return "WARNING: " + healthProfessional.getName() + " (ID " + healthProfessional.getId()
                    + ") is already booked at " + timeSlot + " on " + day + ".";
        }

        Appointment appointment = new Appointment(patient, healthProfessional, day, timeSlot);
        appointments.add(appointment);
        return "SUCCESS: booked " + patient.getName() + " with " + healthProfessional.getName()
                + " (ID " + healthProfessional.getId() + ") at " + timeSlot + " on " + day + ".";
    }

    /**
     * Checks whether the professional already has an appointment at the time
     * supplied.
     *
     * @param healthProfessional the professional to check
     * @param day                the day to check
     * @param timeSlot           the time to check
     * @return true if that professional is already booked then
     */
    public boolean isBooked(HealthProfessional healthProfessional, Weekday day, TimeSlot timeSlot) {
        for (Appointment appointment : appointments) {
            if (appointment.isForProfessional(healthProfessional)
                    && appointment.getDay() == day
                    && appointment.isAt(timeSlot)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns every appointment, sorted from earliest to latest.
     *
     * <p>A sorted copy is returned so that printing the clinic's day does not
     * re-order the collection that is actually stored. Two appointments at the
     * same time may appear in either order, which is acceptable to the clinic.</p>
     *
     * @return a sorted copy of all appointments
     */
    public List<Appointment> getAllAppointments() {
        List<Appointment> copy = new ArrayList<>(appointments);
        Collections.sort(copy);
        return copy;
    }

    /**
     * Returns the appointments booked with the professional whose ID is
     * supplied.
     *
     * <p>The appointments are returned in the order they were booked. The
     * caller decides whether the subset needs to be sorted, which is what the
     * {@link #sortByTime(List)} method is for.</p>
     *
     * @param professionalId the ID of the professional, digits only
     * @return the matching appointments, in booking order
     */
    public List<Appointment> getAppointmentsByProfessionalId(String professionalId) {
        List<Appointment> matches = new ArrayList<>();
        if (professionalId == null) {
            return matches;
        }
        for (Appointment appointment : appointments) {
            if (appointment.isForProfessionalWithId(professionalId)) {
                matches.add(appointment);
            }
        }
        return matches;
    }

    /**
     * Returns the appointments booked by the patient whose mobile number is
     * supplied.
     *
     * @param mobileNumber the mobile number of the patient
     * @return the matching appointments, in booking order
     */
    public List<Appointment> getAppointmentsByPatientMobile(String mobileNumber) {
        List<Appointment> matches = new ArrayList<>();
        if (mobileNumber == null) {
            return matches;
        }
        for (Appointment appointment : appointments) {
            if (appointment.isForPatientWithMobile(mobileNumber)) {
                matches.add(appointment);
            }
        }
        return matches;
    }

    /**
     * Sorts the appointments supplied into time order. This can be applied to
     * the whole day or to any filtered subset.
     *
     * @param list the appointments to sort
     */
    public void sortByTime(List<Appointment> list) {
        if (list != null) {
            Collections.sort(list);
        }
    }

    /**
     * Cancels the appointment booked with the professional and time supplied.
     *
     * @param professionalId the ID of the professional, digits only
     * @param timeSlot       the time of the appointment to cancel
     * @return a message saying whether the appointment was cancelled, or why
     *         it could not be
     */
    public String cancelAppointment(String professionalId, TimeSlot timeSlot) {
        if (professionalId == null || timeSlot == null) {
            return "WARNING: cancelling an appointment needs a health professional ID and a time.";
        }
        for (int index = 0; index < appointments.size(); index++) {
            Appointment appointment = appointments.get(index);
            if (appointment.isForProfessionalWithId(professionalId) && appointment.isAt(timeSlot)) {
                appointments.remove(index);
                return "SUCCESS: cancelled the " + timeSlot + " appointment of "
                        + appointment.getPatient().getName() + " with "
                        + appointment.getHealthProfessional().getName()
                        + " (ID " + professionalId + ").";
            }
        }
        return "WARNING: no appointment was found for health professional ID " + professionalId
                + " at " + timeSlot + ", so nothing was cancelled.";
    }

    /**
     * Cancels the earliest appointment booked by the patient whose mobile
     * number is supplied.
     *
     * @param mobileNumber the mobile number of the patient
     * @return a message saying whether the appointment was cancelled, or why
     *         it could not be
     */
    public String cancelAppointmentByPatientMobile(String mobileNumber) {
        if (mobileNumber == null) {
            return "WARNING: cancelling an appointment needs a patient mobile number.";
        }
        Appointment earliest = null;
        for (Appointment appointment : appointments) {
            if (!appointment.isForPatientWithMobile(mobileNumber)) {
                continue;
            }
            if (earliest == null || appointment.compareTo(earliest) < 0) {
                earliest = appointment;
            }
        }
        if (earliest == null) {
            return "WARNING: no appointment was found for patient mobile number "
                    + mobileNumber + ", so nothing was cancelled.";
        }
        appointments.remove(earliest);
        return "SUCCESS: cancelled the earliest appointment of " + earliest.getPatient().getName()
                + ", which was at " + earliest.getTimeSlot() + " with "
                + earliest.getHealthProfessional().getName() + ".";
    }

    /**
     * Returns the number of appointments currently held.
     *
     * @return the number of appointments
     */
    public int getAppointmentCount() {
        return appointments.size();
    }

    /**
     * Returns the timetable the manager books into.
     *
     * @return the clinic schedule
     */
    public ClinicSchedule getSchedule() {
        return schedule;
    }
}
