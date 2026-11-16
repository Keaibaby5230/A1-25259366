/**
 * A nurse: a health professional who provides day to day care on a ward and
 * carries out routine procedures such as taking blood samples.
 *
 * <p>A nurse is not a doctor, so a nurse does not carry a medical licence and
 * does not inherit the ability to prescribe. Only the data and behaviour that
 * every health professional shares is inherited from
 * {@link HealthProfessional}; what a nurse adds is the ward they work on and
 * the samples they take.</p>
 *
 * @author  Zekai Wang (25259366)
 * @version 1.0
 */
public class Nurse extends HealthProfessional {

    /** The ward the nurse works on. */
    private final String ward;

    /**
     * Creates a nurse with an ID, a name, a ward and the days worked.
     *
     * @param id          the ID of the nurse, digits only
     * @param name        the full name of the nurse
     * @param ward        the ward the nurse works on
     * @param workingDays the days of the week the nurse works
     * @throws IllegalArgumentException if any detail is missing or invalid
     */
    public Nurse(String id, String name, String ward, Weekday... workingDays) {
        super(id, name, workingDays);
        if (!hasText(ward)) {
            throw new IllegalArgumentException("A nurse must work on a ward.");
        }
        this.ward = ward.trim();
    }

    /**
     * Creates a nurse with an ID, a name and a ward, who works the standard
     * Monday to Friday week.
     *
     * @param id   the ID of the nurse, digits only
     * @param name the full name of the nurse
     * @param ward the ward the nurse works on
     * @throws IllegalArgumentException if any detail is missing or invalid
     */
    public Nurse(String id, String name, String ward) {
        this(id, name, ward, DEFAULT_WORKING_DAYS);
    }

    /**
     * Returns the ward the nurse works on.
     *
     * @return the ward
     */
    public String getWard() {
        return ward;
    }

    /**
     * Takes a blood sample from the patient. This is the nurse's own procedure,
     * which no other professional in this system performs.
     *
     * @param patient the patient the sample is taken from
     * @return a description of the sample taken
     */
    public String takeBloodSample(Patient patient) {
        return getName() + " takes a blood sample from " + patient.getName()
                + " and sends it to the " + ward + " laboratory.";
    }

    /**
     * Returns the name of this type of health professional.
     *
     * @return "Nurse"
     */
    @Override
    public String getProfessionalType() {
        return "Nurse";
    }

    /**
     * Returns the details that are specific to a nurse.
     *
     * @return the ward, as one line
     */
    @Override
    public String getSpecialistDetails() {
        return "Ward: " + ward;
    }

    /**
     * Carries out the routine care the nurse is responsible for.
     *
     * @param patient the patient being cared for
     * @return a description of what the nurse did
     */
    @Override
    public String performClinicalDuty(Patient patient) {
        return takeBloodSample(patient) + " The result is recorded in the " + ward + " notes.";
    }
}
