/**
 * A surgeon: a specialist doctor who performs operations.
 *
 * <p>A surgeon is a doctor, so the licence number and the ability to prescribe
 * are inherited. The data that is new here is the operating theatre the
 * surgeon works in.</p>
 *
 * @author  Zekai Wang (25259366)
 * @version 1.0
 */
public class Surgeon extends Doctor {

    /** The operating theatre the surgeon works in. */
    private final String operatingTheatre;

    /**
     * Creates a surgeon with an ID, a name, a licence number, an operating
     * theatre and the days worked.
     *
     * @param id               the ID of the surgeon, digits only
     * @param name             the full name of the surgeon
     * @param licenceNumber    the medical licence number, digits only
     * @param operatingTheatre the theatre the surgeon operates in
     * @param workingDays      the days of the week the surgeon works
     * @throws IllegalArgumentException if any detail is missing or invalid
     */
    public Surgeon(String id, String name, String licenceNumber,
            String operatingTheatre, Weekday... workingDays) {
        super(id, name, licenceNumber, workingDays);
        if (!hasText(operatingTheatre)) {
            throw new IllegalArgumentException("A surgeon must have an operating theatre.");
        }
        this.operatingTheatre = operatingTheatre.trim();
    }

    /**
     * Creates a surgeon with an ID, a name, a licence number and an operating
     * theatre, who works the standard Monday to Friday week.
     *
     * @param id               the ID of the surgeon, digits only
     * @param name             the full name of the surgeon
     * @param licenceNumber    the medical licence number, digits only
     * @param operatingTheatre the theatre the surgeon operates in
     * @throws IllegalArgumentException if any detail is missing or invalid
     */
    public Surgeon(String id, String name, String licenceNumber, String operatingTheatre) {
        this(id, name, licenceNumber, operatingTheatre,
                // Default roster: the standard Monday to Friday week.
                Weekday.MONDAY, Weekday.TUESDAY, Weekday.WEDNESDAY, Weekday.THURSDAY, Weekday.FRIDAY);
    }

    /**
     * Returns the operating theatre the surgeon works in.
     *
     * @return the operating theatre
     */
    public String getOperatingTheatre() {
        return operatingTheatre;
    }

    /**
     * Returns the name of this type of health professional.
     *
     * @return "Surgeon"
     */
    @Override
    public String getProfessionalType() {
        return "Surgeon";
    }

    /**
     * Returns the details that are specific to a surgeon.
     *
     * @return the operating theatre, as one line
     */
    @Override
    protected String getDoctorDetails() {
        return "Operating theatre: " + operatingTheatre;
    }

    /**
     * Treats the patient by prescribing pain relief after an operation and
     * booking a theatre session.
     *
     * @param patient the patient being seen
     * @return a description of what the surgeon did
     */
    @Override
    public String performClinicalDuty(Patient patient) {
        return prescribe(patient, "post-operative analgesia")
                + " The patient is booked into " + operatingTheatre + ".";
    }
}
