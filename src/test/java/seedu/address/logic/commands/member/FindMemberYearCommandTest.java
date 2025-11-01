package seedu.address.logic.commands.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAliases.getTypicalAliasBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.YearContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for FindMemberYearCommand.
 */
public class FindMemberYearCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalAliasBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalAliasBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        YearContainsKeywordsPredicate firstPredicate =
                new YearContainsKeywordsPredicate(Collections.singletonList("1"));
        YearContainsKeywordsPredicate secondPredicate =
                new YearContainsKeywordsPredicate(Collections.singletonList("2"));

        FindMemberYearCommand firstCommand = new FindMemberYearCommand(firstPredicate);
        FindMemberYearCommand secondCommand = new FindMemberYearCommand(secondPredicate);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // different object with same predicate -> returns true
        FindMemberYearCommand copy = new FindMemberYearCommand(firstPredicate);
        assertTrue(firstCommand.equals(copy));

        // different predicate -> returns false
        assertFalse(firstCommand.equals(secondCommand));

        // different type -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));
    }

    @Test
    public void execute_noKeywords_noPersonFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        YearContainsKeywordsPredicate predicate = new YearContainsKeywordsPredicate(Collections.emptyList());
        FindMemberYearCommand command = new FindMemberYearCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        // Execute and check
        assertCommandSuccess(command, model, CommandResult.showMembers(expectedMessage), expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        // Example: Suppose ALICE, BENSON, DANIEL have "Y1", "Y2", "Y3" respectively in their year
        YearContainsKeywordsPredicate predicate = new YearContainsKeywordsPredicate(Arrays.asList("1", "3"));
        FindMemberYearCommand command = new FindMemberYearCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(command, model, CommandResult.showMembers(expectedMessage), expectedModel);
    }

    @Test
    public void toStringMethod() {
        YearContainsKeywordsPredicate predicate = new YearContainsKeywordsPredicate(Collections.singletonList("1"));
        FindMemberYearCommand command = new FindMemberYearCommand(predicate);
        String expected = "seedu.address.logic.commands.member.FindMemberYearCommand"
                + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }
}

