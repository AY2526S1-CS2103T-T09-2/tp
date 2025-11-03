package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Year;

/**
 * Tests for {@link JsonAdaptedPerson}.
 */
public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "abc12345";
    private static final String INVALID_PHONE_WITH_SPACES = "9435 1253";
    private static final String INVALID_PHONE_LENGTH = "9435125";
    private static final String INVALID_PHONE_START = "74351253";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_EMAIL_TOO_LONG = "averyveryveryverylonglocalpart123456@u.nus.edu";
    private static final String INVALID_YEAR = "5";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ROLE = BENSON.getRole().toString();
    private static final String VALID_YEAR = BENSON.getYear().value;
    private static final Integer VALID_ATTENDANCE = BENSON.getAttendanceCount();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_YEAR, VALID_ROLE, VALID_ATTENDANCE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                null, VALID_PHONE, VALID_EMAIL, VALID_YEAR, VALID_ROLE, VALID_ATTENDANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_YEAR, VALID_ROLE, VALID_ATTENDANCE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS_NUMBER;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_phoneWithWhitespace_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, INVALID_PHONE_WITH_SPACES, VALID_EMAIL, VALID_YEAR, VALID_ROLE, VALID_ATTENDANCE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS_SPACES;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_phoneWrongLength_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, INVALID_PHONE_LENGTH, VALID_EMAIL, VALID_YEAR, VALID_ROLE, VALID_ATTENDANCE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS_LENGTH;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_phoneInvalidStart_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, INVALID_PHONE_START, VALID_EMAIL, VALID_YEAR, VALID_ROLE, VALID_ATTENDANCE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS_START;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, null, VALID_EMAIL, VALID_YEAR, VALID_ROLE, VALID_ATTENDANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_YEAR, VALID_ROLE, VALID_ATTENDANCE);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_emailTooLong_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, INVALID_EMAIL_TOO_LONG, VALID_YEAR, VALID_ROLE, VALID_ATTENDANCE);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, null, VALID_YEAR, VALID_ROLE, VALID_ATTENDANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidYear_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_YEAR, VALID_ROLE, VALID_ATTENDANCE);
        String expectedMessage = Year.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullYear_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_ROLE, VALID_ATTENDANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Year.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_YEAR, "Role.", VALID_ATTENDANCE);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
