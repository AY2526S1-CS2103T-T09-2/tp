package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_PRESIDENT;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_TREASURER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_TREASURER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.member.AddMemberCommand;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Year;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {

    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // Single tag
        Person expectedPerson = new PersonBuilder(BOB).withRole(VALID_ROLE_TREASURER).build();
        assertParseSuccess(parser, "member " + PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + YEAR_DESC_BOB + ROLE_DESC_TREASURER,
                new AddMemberCommand(expectedPerson));

        // Multiple tags
        Person expectedPersonMultipleTags = new PersonBuilder(BOB)
                .withRole(VALID_ROLE_TREASURER).build();

        assertParseFailure(parser, "member " + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + YEAR_DESC_BOB + ROLE_DESC_TREASURER + ROLE_DESC_PRESIDENT,
                "Multiple values specified for the following single-valued field(s): r/");
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // No tags
        Person expectedPerson = new PersonBuilder(AMY).withRole(VALID_ROLE_TREASURER).build();
        assertParseSuccess(parser, "member " + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + YEAR_DESC_AMY + ROLE_DESC_TREASURER, new AddMemberCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE_MEMBER);

        // Missing name prefix
        assertParseFailure(parser, "member " + VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + YEAR_DESC_BOB, expectedMessage);

        // Missing phone prefix
        assertParseFailure(parser, "member " + NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB
                        + YEAR_DESC_BOB, expectedMessage);

        // Missing email prefix
        assertParseFailure(parser, "member " + NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB
                        + YEAR_DESC_BOB, expectedMessage);

        // Missing year prefix
        assertParseFailure(parser, "member " + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + VALID_YEAR_BOB, expectedMessage);
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + YEAR_DESC_BOB
                + ROLE_DESC_TREASURER;

        // multiple names
        assertParseFailure(parser, "member " + NAME_DESC_AMY + validPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, "member " + PHONE_DESC_AMY + validPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, "member " + EMAIL_DESC_AMY + validPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple years
        assertParseFailure(parser, "member " + YEAR_DESC_AMY + validPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_YEAR));
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid name
        assertParseFailure(parser, "member " + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + YEAR_DESC_BOB + ROLE_DESC_TREASURER, Name.MESSAGE_CONSTRAINTS);

        // Invalid phone
        assertParseFailure(parser, "member " + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                        + YEAR_DESC_BOB + ROLE_DESC_TREASURER, Phone.MESSAGE_CONSTRAINTS_NUMBER);

        // Invalid email
        assertParseFailure(parser, "member " + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                        + YEAR_DESC_BOB + ROLE_DESC_TREASURER, Email.MESSAGE_CONSTRAINTS);

        // Invalid year
        assertParseFailure(parser, "member " + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_YEAR_DESC + ROLE_DESC_TREASURER, Year.MESSAGE_CONSTRAINTS);

        // Invalid tag
        assertParseFailure(parser, "member " + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + YEAR_DESC_BOB + INVALID_ROLE_DESC, Tag.MESSAGE_CONSTRAINTS);
    }
}
