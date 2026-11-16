/**
 * A dietitian: a health professional who plans what a patient eats in order to
 * treat a condition.
 *
 * <p>A dietitian is not a doctor and does not prescribe drugs. The shared
 * details and behaviour come from {@link HealthProfessional}; what a dietitian
 * adds is the area of nutrition they specialise in and the meal plans they
 * prepare.</p>
 *
 * @author  Zekai Wang (25259366)
 * @version 1.0
 */
public class Dietitian extends HealthProfessional {

    /** The area of nutrition the dietitian specialises in. */
    private final String dietaryFocus;

    /**
     * Creates a dietitian with an ID, a name, a dietary focus and the days
     * worked.
     *
     * @param id           the ID of the dietitian, digits only
     * @param name         the full name of the dietitian
     * @param dietaryFocus the area of nutrition the dietitian specialises in
     * @param workingDays  the days of the week the dietitian works
     * @throws IllegalArgumentException if any detail is missing or invalid
     */
    public Dietitian(String id, String name, String dietaryFocus, Weekday... workingDays) {
        super(id, name, workingDays);
        if (!hasText(dietaryFocus)) {
            throw new IllegalArgumentException("A dietitian must have a dietary focus.");
        }
        this.dietaryFocus = dietaryFocus.trim();
    }

    /**
     * Creates a dietitian with an ID, a name and a dietary focus, who works the
     * standard Monday to Friday week.
     *
     * @param id           the ID of the dietitian, digits only
     * @param name         the full name of the dietitian
     * @param dietaryFocus the area of nutrition the dietitian specialises in
     * @throws IllegalArgumentException if any detail is missing or invalid
     */
    public Dietitian(String id, String name, String dietaryFocus) {
        this(id, name, dietaryFocus,
                // Default roster: the standard Monday to Friday week.
                Weekday.MONDAY, Weekday.TUESDAY, Weekday.WEDNESDAY, Weekday.THURSDAY, Weekday.FRIDAY);
    }

    /**
     * Returns the area of nutrition the dietitian specialises in.
     *
     * @return the dietary focus
     */
    public String getDietaryFocus() {
        return dietaryFocus;
    }

    /**
     * Prepares a meal plan for the patient. This is the dietitian's own
     * procedure, which no other professional in this system performs.
     *
     * @param patient the patient the plan is written for
     * @return a description of the plan prepared
     */
    public String createMealPlan(Patient patient) {
        return getName() + " prepares a weekly meal plan for " + patient.getName()
                + " focused on " + dietaryFocus + ".";
    }

    /**
     * Returns the name of this type of health professional.
     *
     * @return "Dietitian"
     */
    @Override
    public String getProfessionalType() {
        return "Dietitian";
    }

    /**
     * Returns the details that are specific to a dietitian.
     *
     * @return the dietary focus, as one line
     */
    @Override
    public String getSpecialistDetails() {
        return "Dietary focus: " + dietaryFocus;
    }

    /**
     * Carries out the nutritional care the dietitian is responsible for.
     *
     * @param patient the patient being seen
     * @return a description of what the dietitian did
     */
    @Override
    public String performClinicalDuty(Patient patient) {
        return createMealPlan(patient) + " The plan is reviewed at the next appointment.";
    }
}
