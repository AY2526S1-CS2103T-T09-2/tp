package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.HACKATHON;
import static seedu.address.testutil.TypicalEvents.MEETING;
import static seedu.address.testutil.TypicalEvents.WORKSHOP;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.AliasBook;
import seedu.address.model.event.DateContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindEventDateCommand}.
 */
public class FindEventDateCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());

    @Test
    public void equals() {
        DateContainsKeywordsPredicate firstPredicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("Zoom"));
        DateContainsKeywordsPredicate secondPredicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("Auditorium"));

        FindEventDateCommand findFirstCommand = new FindEventDateCommand(firstPredicate);
        FindEventDateCommand findSecondCommand = new FindEventDateCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindEventDateCommand findFirstCommandCopy = new FindEventDateCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        DateContainsKeywordsPredicate predicate = new DateContainsKeywordsPredicate(Collections.emptyList());
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_singleKeyword_singleEventFound() {
        // Only MEETING has "Zoom" as its location
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        DateContainsKeywordsPredicate predicate = preparePredicate("Zoom");
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(MEETING), model.getFilteredEventList());
    }

    @Test
    public void execute_keywordWithWhitespace_singleEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        DateContainsKeywordsPredicate predicate = preparePredicate("  Zoom  "); // extra spaces
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(MEETING), model.getFilteredEventList());
    }

    @Test
    public void execute_keywordDifferentCase_singleEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        DateContainsKeywordsPredicate predicate = preparePredicate("zOOM"); // uppercase/lowercase mix
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(MEETING), model.getFilteredEventList());
    }

    @Test
    public void execute_duplicateKeywords_singleEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        DateContainsKeywordsPredicate predicate = preparePredicate("Zoom Zoom Zoom");
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(MEETING), model.getFilteredEventList());
    }

    @Test
    public void execute_mixedKeywords_someEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        DateContainsKeywordsPredicate predicate = preparePredicate("Zoom InvalidLocation");
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(MEETING), model.getFilteredEventList());
    }

    @Test
    public void execute_multipleKeywords_multipleEventsFound() {
        // "UTown" matches HACKATHON, "Auditorium" matches WORKSHOP
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 2);
        DateContainsKeywordsPredicate predicate = preparePredicate("UTown Auditorium");
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(WORKSHOP, HACKATHON), model.getFilteredEventList());
    }

    @Test
    public void execute_keywordMatchingAllEvents_allEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 3);
        DateContainsKeywordsPredicate predicate = preparePredicate("Zoom UTown Auditorium");
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(MEETING, WORKSHOP, HACKATHON), model.getFilteredEventList());
    }

    @Test
    public void execute_keywordNotMatchingCase_noEventFound() {
        // Case-sensitive check (depending on predicate implementation)
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        DateContainsKeywordsPredicate predicate = preparePredicate("zoomz");
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_keywordWithSpecialCharacters_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        DateContainsKeywordsPredicate predicate = preparePredicate("@Zoom!");
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_partialKeyword_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        DateContainsKeywordsPredicate predicate = preparePredicate("Zoo"); // partial match for "Zoom"
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void toStringMethod() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Arrays.asList("Zoom"));
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        String expected = FindEventDateCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * Parses {@code userInput} into a {@code DateContainsKeywordsPredicate}.
     */
    private DateContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DateContainsKeywordsPredicate(Arrays.asList(userInput.trim().split("\\s+")));
    }
}
