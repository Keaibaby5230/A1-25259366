/**
 * A cardiologist: a specialist doctor who treats the heart and the blood
 * vessels.
 *
 * <p>A cardiologist is a doctor, so it inherits everything from
 * {@link Doctor} and {@link HealthProfessional}. The data that is new here is
 * the sub-specialty the cardiologist practises, for example interventional
 * cardiology or a heart failure clinic.</p>
 *
 * @author  Zekai Wang (25259366)
 * @version 1.0
 */
public class Cardiologist extends Doctor {

    /** The sub-specialty the cardiologist practises. */
    private final String subSpecialty;

    /**
     * Creates a cardiologist with an ID, a name, a licence number, a
     * sub-specialty and the days worked.
     *
     * @param id            the ID of the cardiologist, digits only
     * @param name          the full name of the cardiologist
     * @param licenceNumber the medical licence number, digits only
     * @param subSpecialty  the cardiology sub-specialty practised
     * @param workingDays   the days of the week the cardiologist works
     * @throws IllegalArgumentException if any detail is missing or invalid
     */
    public Cardiologist(String id, String name, String licenceNumber,
            String subSpecialty, Weekday... workingDays) {
        super(id, name, licenceNumber, workingDays);
        if (!hasText(subSpecialty)) {
            throw new IllegalArgumentException("A cardiologist must have a sub-specialty.");
        }
        this.subSpecialty = subSpecialty.trim();
    }

    /**
     * Creates a cardiologist with an ID, a name, a licence number and a
     * sub-specialty, who works the standard Monday to Friday week.
     *
     * @param id            the ID of the cardiologist, digits only
     * @param name          the full name of the cardiologist
     * @param licenceNumber the medical licence number, digits only
     * @param subSpecialty  the cardiology sub-specialty practised
     * @throws IllegalArgumentException if any detail is missing or invalid
     */
    public Cardiologist(String id, String name, String licenceNumber, String subSpecialty) {
        this(id, name, licenceNumber, subSpecialty,
                // Default roster: the standard Monday to Friday week.
                Weekday.MONDAY, Weekday.TUESDAY, Weekday.WEDNESDAY, Weekday.THURSDAY, Weekday.FRIDAY);
    }

    /**
     * Returns the cardiology sub-specialty practised.
     *
     * @return the sub-specialty of the cardiologist
     */
    public String getSubSpecialty() {
        return subSpecialty;
    }

    /**
     * Returns the name of this type of health professional.
     *
     * @return "Cardiologist (specialist)"
     */
    @Override
    public String getProfessionalType() {
        return "Cardiologist (specialist)";
    }

    /**
     * Returns the details that are specific to a cardiologist.
     *
     * @return the sub-specialty, as one line
     */
    @Override
    protected String getDoctorDetails() {
        return "Cardiology sub-specialty: " + subSpecialty;
    }

    /**
     * Treats the patient by prescribing heart medication and requesting a
     * diagnostic test in the cardiology clinic.
     *
     * @param patient the patient being seen
     * @return a description of what the cardiologist did
     */
    @Override
    public String performClinicalDuty(Patient patient) {
        return prescribe(patient, "a daily beta blocker and orders an echocardiogram")
                + " The echocardiogram is followed up in the " + subSpecialty + " service.";
    }
}
