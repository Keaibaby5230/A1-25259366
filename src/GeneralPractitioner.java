/**
 * A general practitioner: the doctor a patient usually sees first.
 *
 * <p>A general practitioner is a doctor, so it inherits the licence number and
 * the ability to prescribe from {@link Doctor}, which in turn inherits the ID,
 * the name and the working days from {@link HealthProfessional}. The only data
 * that is new here is the consultation room the general practitioner works
 * from.</p>
 *
 * @author  Zekai Wang (25259366)
 * @version 1.0
 */
public class GeneralPractitioner extends Doctor {

    /** The room the general practitioner consults in. */
    private final String clinicRoom;

    /**
     * Creates a general practitioner with an ID, a name, a licence number,
     * a consultation room and the days worked.
     *
     * @param id            the ID of the general practitioner, digits only
     * @param name          the full name of the general practitioner
     * @param licenceNumber the medical licence number, digits only
     * @param clinicRoom    the room the general practitioner consults in
     * @param workingDays   the days of the week the general practitioner works
     * @throws IllegalArgumentException if any detail is missing or invalid
     */
    public GeneralPractitioner(String id, String name, String licenceNumber,
            String clinicRoom, Weekday... workingDays) {
        super(id, name, licenceNumber, workingDays);
        if (!hasText(clinicRoom)) {
            throw new IllegalArgumentException("A general practitioner must have a consultation room.");
        }
        this.clinicRoom = clinicRoom.trim();
    }

    /**
     * Creates a general practitioner with an ID, a name, a licence number and
     * a consultation room, who works the standard Monday to Friday week.
     *
     * @param id            the ID of the general practitioner, digits only
     * @param name          the full name of the general practitioner
     * @param licenceNumber the medical licence number, digits only
     * @param clinicRoom    the room the general practitioner consults in
     * @throws IllegalArgumentException if any detail is missing or invalid
     */
    public GeneralPractitioner(String id, String name, String licenceNumber, String clinicRoom) {
        this(id, name, licenceNumber, clinicRoom, DEFAULT_WORKING_DAYS);
    }

    /**
     * Creates a general practitioner with an ID, a name and a licence number,
     * who works the standard Monday to Friday week in the main clinic room.
     *
     * @param id            the ID of the general practitioner, digits only
     * @param name          the full name of the general practitioner
     * @param licenceNumber the medical licence number, digits only
     * @throws IllegalArgumentException if any detail is missing or invalid
     */
    public GeneralPractitioner(String id, String name, String licenceNumber) {
        this(id, name, licenceNumber, "General Clinic, Room 1", DEFAULT_WORKING_DAYS);
    }

    /**
     * Returns the room the general practitioner consults in.
     *
     * @return the consultation room
     */
    public String getClinicRoom() {
        return clinicRoom;
    }

    /**
     * Returns the name of this type of health professional.
     *
     * @return "General Practitioner"
     */
    @Override
    public String getProfessionalType() {
        return "General Practitioner";
    }

    /**
     * Returns the details that are specific to a general practitioner.
     *
     * @return the consultation room, as one line
     */
    @Override
    protected String getDoctorDetails() {
        return "Consultation room: " + clinicRoom;
    }

    /**
     * Treats the patient with a general consultation. The general practitioner
     * prescribes a common antibiotic and files the visit under the room it was
     * carried out in.
     *
     * @param patient the patient being seen
     * @return a description of what the general practitioner did
     */
    @Override
    public String performClinicalDuty(Patient patient) {
        return prescribe(patient, "a five-day course of amoxicillin")
                + " The visit is filed under " + clinicRoom + ".";
    }
}
