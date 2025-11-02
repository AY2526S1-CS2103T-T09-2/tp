package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
// import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.Year;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    public static final String MESSAGE_INVALID_ATTENDANCE_COUNT =
            "Person's attendance count cannot be negative.";

    private final String name;
    private final String phone;
    private final String email;
    private final String year;
    private final String role;
//    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final Integer attendanceCount;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("year") String year,
            @JsonProperty("role") String role, @JsonProperty("attendanceCount") Integer attendanceCount) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.year = year;
        this.role = role;
        this.attendanceCount = attendanceCount;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        year = source.getYear().value;
        role = source.getRole().roleName;
        attendanceCount = source.getAttendanceCount();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
//        final List<Tag> personTags = new ArrayList<>();
//        for (JsonAdaptedTag tag : tags) {
//            personTags.add(tag.toModelType());
//        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        // 1) No internal whitespace
        if (Phone.hasInternalWhitespace(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS_SPACES);
        }
        // Internal invariant after validation
        assert !Phone.hasInternalWhitespace(phone) : "Invariant: JSON phone must not contain internal whitespace here";
        // 2) Only digits
        if (!Phone.isDigitsOnly(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS_NUMBER);
        }
        // Internal invariant after validation
        assert Phone.isDigitsOnly(phone) : "Invariant: JSON phone must be digits-only here";
        // 3) Exactly 8 digits
        if (!Phone.isValidLength(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS_LENGTH);
        }
        // Internal invariant after validation
        assert Phone.isValidLength(phone) : "Invariant: JSON phone must be exactly 8 digits here";
        // 4) Starts with 8 or 9
        if (!Phone.isValidStart(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS_START);
        }
        // Internal invariant after validation
        assert Phone.isValidStart(phone) : "Invariant: JSON phone must start with 8 or 9 here";
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (year == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Year.class.getSimpleName()));
        }
        if (!Year.isValidYear(year)) {
            throw new IllegalValueException(Year.MESSAGE_CONSTRAINTS);
        }
        final Year modelYear = new Year(year);

        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName()));
        }
        if (!Role.isValidRoleName(role)) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }
        final Role modelRole = new Role(role);

//        final Set<Tag> modelTags = new HashSet<>(personTags);

        if (attendanceCount != null && attendanceCount < 0) {
            throw new IllegalValueException(MESSAGE_INVALID_ATTENDANCE_COUNT);
        }

        int modelAttendanceCount = (attendanceCount != null) ? attendanceCount : 0;

        return new Person(modelName, modelPhone, modelEmail, modelYear, modelRole, modelAttendanceCount);
    }

}
