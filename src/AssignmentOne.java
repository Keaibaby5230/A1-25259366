import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Runs the scripted simulation that stands in for the people who would use the
 * clinic system, and prints the results to the console.
 *
 * <p>There is no user interface and no menu on purpose: the simulation creates
 * the objects, calls their behaviour and prints what happens, which is how the
 * design decisions hidden in the other classes are shown to work.</p>
 *
 * <p>Part 3 builds the clinic's health professionals and prints them through
 * the single general type. Part 4 models and prints one appointment, and shows
 * that appointments can be sorted. Part 6 runs the appointment system end to
 * end, covering the successful operations and the ones that must be refused.</p>
 *
 * @author  Zekai Wang (25259366)
 * @version 1.0
 */
public class AssignmentOne {

    /** The day the simulated clinic is running on. All bookings are for today. */
    private static final Weekday TODAY = Weekday.MONDAY;

    /**
     * Runs the demonstration.
     *
     * @param args command line arguments, which are not used
     */
    public static void main(String[] args) {
        // ------------------------------------------------------------------
        // Part 3 - the health professionals of the clinic
        // ------------------------------------------------------------------
        // Ten professionals of five different types. Some are created with a
        // full roster of working days, others rely on the default Monday to
        // Friday week or on a default consultation room, which shows the extra
        // constructors in use.
        GeneralPractitioner drNguyen = new GeneralPractitioner("1001", "Dr Alice Nguyen", "55120",
                "General Clinic, Room 1", Weekday.MONDAY, Weekday.WEDNESDAY, Weekday.FRIDAY);
        GeneralPractitioner drOkafor = new GeneralPractitioner("1002", "Dr Ben Okafor", "55488");
        GeneralPractitioner drSilva = new GeneralPractitioner("1003", "Dr Carla Silva", "55901",
                "General Clinic, Room 3", Weekday.MONDAY, Weekday.TUESDAY, Weekday.THURSDAY);

        Cardiologist drPetrov = new Cardiologist("2001", "Dr Dmitri Petrov", "60214",
                "Interventional cardiology", Weekday.MONDAY, Weekday.THURSDAY);
        Cardiologist drChan = new Cardiologist("2002", "Dr Wei Chan", "60377",
                "Heart failure clinic");

        Surgeon drMoreau = new Surgeon("3001", "Dr Elise Moreau", "70125",
                "Operating Theatre 2", Weekday.MONDAY, Weekday.WEDNESDAY);

        Nurse nurseAdeyemi = new Nurse("4001", "Nurse Fola Adeyemi", "Ward 3B",
                Weekday.MONDAY, Weekday.TUESDAY, Weekday.WEDNESDAY, Weekday.THURSDAY, Weekday.FRIDAY);
        Nurse nurseKaur = new Nurse("4002", "Nurse Gita Kaur", "Ward 5A");

        Dietitian dietitianHughes = new Dietitian("5001", "Dietitian Hana Hughes",
                "Type 2 diabetes and weight management", Weekday.MONDAY, Weekday.FRIDAY);
        Dietitian dietitianIbrahim = new Dietitian("5002", "Dietitian Imran Ibrahim",
                "Paediatric nutrition", Weekday.TUESDAY, Weekday.THURSDAY);

        // Storing all five types in one collection of the general type is what
        // lets the clinic process them together: nothing below needs to know,
        // or care, which concrete type it is looking at.
        List<HealthProfessional> professionals = new ArrayList<>();
        professionals.add(drNguyen);
        professionals.add(drOkafor);
        professionals.add(drSilva);
        professionals.add(drPetrov);
        professionals.add(drChan);
        professionals.add(drMoreau);
        professionals.add(nurseAdeyemi);
        professionals.add(nurseKaur);
        professionals.add(dietitianHughes);
        professionals.add(dietitianIbrahim);

        printHeader("PART 3 - THE HEALTH PROFESSIONALS OF THE CLINIC");
        printProfessionals(professionals);

        // ------------------------------------------------------------------
        // Part 4 - modelling a single appointment
        // ------------------------------------------------------------------
        Patient alice = new Patient("Alice Nguyen", "0412345678");
        Patient ben = new Patient("Ben Carter", "0422334455");
        Patient cathy = new Patient("Cathy Wu", "0433556677");
        Patient daniel = new Patient("Daniel Osei", "0445667788");
        Patient erin = new Patient("Erin Blake", "0456778899");
        Patient frank = new Patient("Frank Doyle", "0467889900");

        printHeader("PART 4 - MODELLING ONE APPOINTMENT");
        Appointment singleAppointment = new Appointment(alice, drNguyen, TODAY, new TimeSlot(11, 0));
        System.out.println("An appointment holds the patient, the professional, the day and the time:");
        System.out.println(singleAppointment);
        System.out.println();

        printSubHeader("Three appointments in the order they happen to be created");
        List<Appointment> trialAppointments = new ArrayList<>();
        trialAppointments.add(new Appointment(alice, drNguyen, TODAY, new TimeSlot(11, 0)));
        trialAppointments.add(new Appointment(ben, drPetrov, TODAY, new TimeSlot(9, 0)));
        trialAppointments.add(new Appointment(cathy, nurseAdeyemi, TODAY, new TimeSlot(10, 0)));
        printAppointments(trialAppointments);

        printSubHeader("The same three appointments after Collections.sort(...)");
        Collections.sort(trialAppointments);
        printAppointments(trialAppointments);

        // ------------------------------------------------------------------
        // Part 6 - the appointment system end to end
        // ------------------------------------------------------------------
        printHeader("PART 6 - THE APPOINTMENT SYSTEM END TO END");
        ClinicSchedule schedule = new ClinicSchedule();
        AppointmentManager manager = new AppointmentManager(schedule);
        System.out.println("Clinic timetable: " + schedule.describe());
        System.out.println("All bookings below are for " + TODAY + ", the current day.");

        // 6.1 - the operations that should succeed.
        printSubHeader("6.1 Adding appointments");
        System.out.println(manager.addAppointment(alice, drNguyen, TODAY, new TimeSlot(11, 0)));
        System.out.println(manager.addAppointment(ben, drPetrov, TODAY, new TimeSlot(9, 0)));
        System.out.println(manager.addAppointment(cathy, nurseAdeyemi, TODAY, new TimeSlot(10, 0)));
        System.out.println(manager.addAppointment(daniel, dietitianHughes, TODAY, new TimeSlot(11, 30)));
        System.out.println(manager.addAppointment(alice, drNguyen, TODAY, new TimeSlot(9, 0)));
        System.out.println(manager.addAppointment(erin, drMoreau, TODAY, new TimeSlot(14, 0)));
        System.out.println(manager.addAppointment(ben, drChan, TODAY, new TimeSlot(15, 30)));
        System.out.println("Appointments held: " + manager.getAppointmentCount());

        // 6.2 - the whole day, in time order.
        printSubHeader("6.2 All appointments, sorted from earliest to latest");
        printAppointments(manager.getAllAppointments());

        // 6.3 - filtering, then sorting the filtered subset.
        printSubHeader("6.3 Appointments for health professional 1001");
        List<Appointment> gpAppointments = manager.getAppointmentsByProfessionalId("1001");
        System.out.println("Found " + gpAppointments.size()
                + " appointment(s) for ID 1001, in the order they were booked:");
        printAppointments(gpAppointments);
        manager.sortByTime(gpAppointments);
        System.out.println("The same subset after sorting by appointment time:");
        printAppointments(gpAppointments);

        // 6.4 - filtering by patient mobile number.
        printSubHeader("6.4 Appointments for patient mobile number 0412345678");
        printAppointments(manager.getAppointmentsByPatientMobile("0412345678"));

        // 6.5 - the operations that must be refused.
        printSubHeader("6.5 Operations that cannot be completed");
        System.out.println(manager.addAppointment(alice, drNguyen, TODAY, new TimeSlot(9, 0)));
        System.out.println(manager.addAppointment(ben, nurseKaur, TODAY, new TimeSlot(17, 0)));
        System.out.println(manager.addAppointment(frank, dietitianIbrahim, TODAY, new TimeSlot(12, 0)));
        System.out.println(manager.cancelAppointment("9999", new TimeSlot(12, 0)));
        System.out.println(manager.cancelAppointmentByPatientMobile("0499999999"));
        System.out.println("Appointments held: " + manager.getAppointmentCount()
                + " (unchanged, nothing invalid was added or removed)");

        // 6.6 - cancelling by the two supported routes.
        printSubHeader("6.6 Cancelling appointments");
        System.out.println(manager.cancelAppointment("1001", new TimeSlot(11, 0)));
        System.out.println(manager.cancelAppointmentByPatientMobile("0433556677"));
        System.out.println("Appointments held: " + manager.getAppointmentCount());

        // 6.7 - the collection after the cancellations.
        printSubHeader("6.7 All appointments after the cancellations");
        printAppointments(manager.getAllAppointments());

        printSubHeader("6.8 Appointments for health professional 1001 after the cancellation");
        printAppointments(manager.getAppointmentsByProfessionalId("1001"));

        System.out.println();
        System.out.println("End of the demonstration.");
    }

    /**
     * Prints every professional in the collection with all of its details and
     * the clinical duty it performs for a sample patient.
     *
     * <p>One loop handles all five types. The details and the duty are both
     * produced by the object itself, so each professional answers in its own
     * way through the same two calls.</p>
     *
     * @param professionals the professionals to print
     */
    private static void printProfessionals(List<HealthProfessional> professionals) {
        Patient samplePatient = new Patient("Sam Taylor", "0498765432");
        for (int index = 0; index < professionals.size(); index++) {
            HealthProfessional professional = professionals.get(index);
            System.out.println((index + 1) + ". " + professional);
            System.out.println("    Clinical duty: " + professional.performClinicalDuty(samplePatient));
            System.out.println();
        }
        System.out.println("Health professionals in the clinic: " + professionals.size());
    }

    /**
     * Prints an appointment collection in the order it is supplied.
     *
     * @param appointments the appointments to print
     */
    private static void printAppointments(List<Appointment> appointments) {
        if (appointments.isEmpty()) {
            System.out.println("(no appointments)");
            return;
        }
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
            System.out.println();
        }
        System.out.println("Appointments shown: " + appointments.size());
    }

    /**
     * Prints a section heading.
     *
     * @param title the heading to print
     */
    private static void printHeader(String title) {
        System.out.println();
        System.out.println("============================================================");
        System.out.println(title);
        System.out.println("============================================================");
    }

    /**
     * Prints a sub heading inside a section.
     *
     * @param title the sub heading to print
     */
    private static void printSubHeader(String title) {
        System.out.println();
        System.out.println("-- " + title + " --");
    }
}
