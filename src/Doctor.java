/**
 * A health professional who is a registered medical doctor.
 *
 * <p>This class sits between {@link HealthProfessional} and the individual
 * doctor specialties. Everything a doctor shares - the medical licence number
 * and the ability to prescribe drugs - lives here, so the general practitioner,
 * the cardiologist and the surgeon do not each have to declare it. It is
 * still abstract: only a specific kind of doctor can be created.</p>
 *
 * @author  Zekai Wang (25259366)
 * @version 1.0
 */
public abstract class Doctor extends HealthProfessional {

    /** The medical licence number of the doctor, digits only. */
    private final String licenceNumber;

    /**
     * Creates a doctor who works the standard Monday to Friday week.
     *
     * @param id            the ID of the doctor, digits only
     * @param name          the full name of the doctor
     * @param licenceNumber the medical licence number, digits only
     * @throws IllegalArgumentException if the ID or the licence number is not
     *         digits only, or if the name is empty
     */
    protected Doctor(String id, String name, String licenceNumber) {
        super(id, name);
        this.licenceNumber = validateLicenceNumber(licenceNumber);
    }

    /**
     * Creates a doctor with an ID, a name, a licence number and the days worked.
     *
     * @param id            the ID of the doctor, digits only
     * @param name          the full name of the doctor
     * @param licenceNumber the medical licence number, digits only
     * @param workingDays   the days of the week the doctor works
     * @throws IllegalArgumentException if the ID or the licence number is not
     *         digits only, if the name is empty or if no working day is supplied
     */
    protected Doctor(String id, String name, String licenceNumber, Weekday... workingDays) {
        super(id, name, workingDays);
        this.licenceNumber = validateLicenceNumber(licenceNumber);
    }

    /**
     * Returns the medical licence number of the doctor.
     *
     * @return the licence number, digits only
     */
    public String getLicenceNumber() {
        return licenceNumber;
    }

    /**
     * Records a prescription for the patient. Only doctors can do this, which
     * is why the behaviour lives in this class rather than in the facility
     * class every professional inherits from.
     *
     * @param patient    the patient the drug is prescribed for
     * @param medication the drug and the directions, for example
     *                   "a five-day course of amoxicillin"
     * @return a description of the prescription
     */
    public String prescribe(Patient patient, String medication) {
        return getName() + " prescribes " + medication + " for " + patient.getName() + ".";
    }

    /**
     * Returns the licence number plus whatever further detail the specific
     * kind of doctor records, so the shared doctor data is never repeated by
     * the subclasses.
     *
     * @return the doctor details, one per line
     */
    @Override
    public String getSpecialistDetails() {
        return "Medical licence number: " + licenceNumber
                + System.lineSeparator() + getDoctorDetails();
    }

    /**
     * Returns the details that only this kind of doctor records. Every doctor
     * specialty must provide its own answer.
     *
     * @return the specialty specific details, one per line
     */
    protected abstract String getDoctorDetails();

    /**
     * Checks that the licence number is present and is digits only.
     *
     * @param licenceNumber the licence number supplied by the caller
     * @return the cleaned licence number
     * @throws IllegalArgumentException if the licence number is missing or is
     *         not digits only
     */
    private static String validateLicenceNumber(String licenceNumber) {
        if (!isNumeric(licenceNumber)) {
            throw new IllegalArgumentException(
                    "A medical licence number must be digits only: " + licenceNumber);
        }
        return licenceNumber.trim();
    }
}
