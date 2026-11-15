# A1-25259366 - Health Service Appointment System (PROG2004)

A Java console simulation of the backbone of a health service appointment
system. There is no user interface: `AssignmentOne` creates the objects, calls
their behaviour and prints the results, standing in for the staff and patients
who would use the real system.

## How to run

```
javac -d out src/*.java
java -cp out AssignmentOne
```

Java 11 or later. The project is a plain `javac` project with no build tool and
no external libraries.

## Classes

| Class | Role |
|---|---|
| `HealthProfessional` | Abstract base class: ID, name, working days, shared display behaviour |
| `Doctor` | Abstract intermediate class: medical licence number and prescribing |
| `GeneralPractitioner` | Concrete doctor type with a consultation room |
| `Cardiologist` | Concrete doctor type with a cardiology sub-specialty |
| `Surgeon` | Concrete doctor type with an operating theatre |
| `Nurse` | Concrete type with a ward and the ability to take blood samples |
| `Dietitian` | Concrete type with a dietary focus and the ability to write meal plans |
| `Patient` | Patient name and validated mobile number |
| `TimeSlot` | Immutable time value that can be compared and sorted |
| `ClinicSchedule` | The fixed daily timetable (08:00-16:00 in 30-minute slots) |
| `Appointment` | One patient, one professional, one day, one slot; `Comparable` by time |
| `AppointmentManager` | Holds the appointments: add, filter, sort and cancel |
| `AssignmentOne` | Main class: the scripted demonstration for Parts 3, 4 and 6 |
| `Weekday` | Enumeration of the days of the week |

## Main design decisions

* **Inheritance over repetition.** The shared data of every professional lives
  once in `HealthProfessional`. `Doctor` adds what all doctors share, and the
  three doctor specialties add only what is unique to them; `Nurse` and
  `Dietitian` inherit from `HealthProfessional` directly because they are not
  doctors and must not be able to prescribe.
* **Abstract base class.** `HealthProfessional` cannot be instantiated, so no
  "plain professional with no specialty" can exist. Its abstract methods
  (`getProfessionalType()`, `getSpecialistDetails()`, `performClinicalDuty()`)
  force every new type to supply its own answer, and let a collection of mixed
  professionals be printed and used through one loop.
* **Encapsulation and immutability.** All fields are `private final`, there are
  no setters, collections are copied on the way in and out, and every validity
  rule is enforced once in a constructor. An object can therefore never reach
  an invalid state.
* **Equality by ID.** `HealthProfessional.equals`/`hashCode` use the ID, which
  is what lets the booking code recognise that two references describe the same
  professional and refuse a double booking.
* **Time as numbers, not text.** `TimeSlot` stores an hour and a minute and
  exposes `compareTo`, so `Appointment` can implement `Comparable` and be
  sorted. A `String` such as "09:00" would not sort correctly.
* **Configurable timetable.** `ClinicSchedule` builds its slots from a start
  hour, an end hour and a slot length. Changing the clinic hours changes one
  constructor argument and nothing else.
* **Operations report, they do not throw.** `AppointmentManager` returns a
  `SUCCESS` or `WARNING` message from every operation, so a failed booking
  explains itself to the caller instead of crashing the program.

## Demonstration coverage

`AssignmentOne` prints ten professionals of five types (Part 3), one appointment
and a sorted group of three (Part 4), then the appointment system end to end
(Part 6): seven bookings, the whole day sorted, a filter by professional ID
followed by a sort of that subset, a filter by patient mobile number, five
refused operations (double booking, a time outside the timetable, a day the
professional does not work, and two cancellations that match nothing), two
successful cancellations, and the collection printed again afterwards.
