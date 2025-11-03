package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.EditEventCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.member.EditMemberCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EditMemberDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "91234567";
    public static final String VALID_PHONE_BOB = "81234567";
    public static final String VALID_EMAIL_AMY = "amy@u.nus.edu";
    public static final String VALID_EMAIL_BOB = "bob@u.nus.edu";
    public static final String VALID_YEAR_AMY = "1";
    public static final String VALID_YEAR_BOB = "2";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String YEAR_DESC_AMY = " " + PREFIX_YEAR + VALID_YEAR_AMY;
    public static final String YEAR_DESC_BOB = " " + PREFIX_YEAR + VALID_YEAR_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_ROLE + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_ROLE + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob@gmail.com"; // non-NUS domain
    public static final String INVALID_YEAR_DESC = " " + PREFIX_YEAR + "5"; // only 1-4 allowed for year
    public static final String INVALID_TAG_DESC = " " + PREFIX_ROLE + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditMemberCommand.EditMemberDescriptor DESC_AMY;
    public static final EditMemberCommand.EditMemberDescriptor DESC_BOB;
    public static final EditEventCommand.EditEventDescriptor DESC_WORKSHOP;
    public static final EditEventCommand.EditEventDescriptor DESC_WELCOME_TEA;

    static {
        DESC_AMY = new EditMemberDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withYear(VALID_YEAR_AMY)
                .withRole(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditMemberDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withYear(VALID_YEAR_BOB)
                .withRole(VALID_TAG_HUSBAND).build();
    }

    public static final String VALID_EVENT_NAME_WELCOME_TEA = "Welcome Tea";
    public static final String VALID_EVENT_NAME_WORKSHOP = "CS Workshop";
    public static final String VALID_EVENT_DATE_WELCOME_TEA = "2025-09-01T18:00";
    public static final String VALID_EVENT_DATE_WORKSHOP = "2025-12-30T14:30";
    public static final String VALID_EVENT_VENUE_WELCOME_TEA = "COM1-01-02";
    public static final String VALID_EVENT_VENUE_WORKSHOP = "NUS COM2";

    public static final String NAME_DESC_WELCOME_TEA = " " + PREFIX_NAME + VALID_EVENT_NAME_WELCOME_TEA;
    public static final String NAME_DESC_WORKSHOP = " " + PREFIX_NAME + VALID_EVENT_NAME_WORKSHOP;
    public static final String DATE_DESC_WELCOME_TEA = " " + PREFIX_DATE + VALID_EVENT_DATE_WELCOME_TEA;
    public static final String DATE_DESC_WORKSHOP = " " + PREFIX_DATE + VALID_EVENT_DATE_WORKSHOP;
    public static final String VENUE_DESC_WELCOME_TEA = " " + PREFIX_LOCATION + VALID_EVENT_VENUE_WELCOME_TEA;
    public static final String VENUE_DESC_WORKSHOP = " " + PREFIX_LOCATION + VALID_EVENT_VENUE_WORKSHOP;

    public static final String INVALID_EVENT_NAME_DESC = " " + PREFIX_NAME + "CS@Workshop"; // '@' not allowed
    public static final String INVALID_EVENT_DATE_DESC = " " + PREFIX_DATE + "2025/09/01 18:00"; // not ISO format
    public static final String INVALID_EVENT_VENUE_DESC = " " + PREFIX_LOCATION + "A".repeat(80); // exceeds 75 chars

    static {
        DESC_WORKSHOP = new EditEventDescriptorBuilder().withName(VALID_EVENT_NAME_WORKSHOP)
                .withDate(VALID_EVENT_DATE_WORKSHOP).withVenue(VALID_EVENT_VENUE_WORKSHOP).build();
        DESC_WELCOME_TEA = new EditEventDescriptorBuilder().withName(VALID_EVENT_NAME_WELCOME_TEA)
                .withDate(VALID_EVENT_DATE_WELCOME_TEA).withVenue(VALID_EVENT_VENUE_WELCOME_TEA).build();
    }



    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the {@code command} and checks that:
     * <ul>
     *   <li>The {@link CommandResult} equals {@code expectedCommandResult}</li>
     *   <li>The feedback message matches {@code expectedMessage}</li>
     *   <li>The {@code actualModel} matches {@code expectedModel}</li>
     * </ul>
     *
     * Used for commands that may trigger UI state changes (e.g., showing events or members).
     */
    public static void assertCommandSuccess(Command command, Model actualModel,
                                            String expectedMessage, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedMessage, result.getFeedbackToUser());
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered event list to show only the event at the given {@code targetIndex}.
     */
    public static void showEventAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEventList().size());

        Event event = model.getFilteredEventList().get(targetIndex.getZeroBased());
        final String[] splitName = event.getName().fullName.split("\\s+");
        model.updateFilteredEventList(new EventNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredEventList().size());
    }

}
