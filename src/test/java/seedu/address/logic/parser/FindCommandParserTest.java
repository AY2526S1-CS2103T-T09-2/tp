package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.event.FindEventDateCommand;
import seedu.address.logic.commands.event.FindEventNameCommand;
import seedu.address.logic.commands.member.FindMemberNameCommand;
import seedu.address.logic.commands.member.FindMemberRoleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.DateContainsKeywordsPredicate;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.RoleContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new FindCommandParser();
    }

    @Test
    public void parse_validMemberName_returnsFindMemberNameCommand() throws Exception {
        FindCommand expectedCommand =
                new FindMemberNameCommand(
                        new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertEquals(expectedCommand, parser.parse("member n/Alice Bob"));
    }

    @Test
    public void parse_validMemberYear_returnsFindMemberYearCommand() throws Exception {
        FindCommand expectedCommand =
                new FindMemberRoleCommand(
                        new RoleContainsKeywordsPredicate(Arrays.asList("1", "2")));
        assertEquals(expectedCommand, parser.parse("member y/1 2"));
    }

    @Test
    public void parse_validEventName_returnsFindEventNameCommand() throws Exception {
        FindCommand expectedCommand =
                new FindEventNameCommand(
                        new EventNameContainsKeywordsPredicate(Arrays.asList("Ideate", "Hackathon")));
        assertEquals(expectedCommand, parser.parse("event n/Ideate Hackathon"));
    }

    @Test
    public void parse_validEventLocation_returnsFindEventLocationCommand() throws Exception {
        FindCommand expectedCommand =
                new FindEventDateCommand(
                        new DateContainsKeywordsPredicate(Arrays.asList("UTown", "NUS")));
        assertEquals(expectedCommand, parser.parse("event v/UTown NUS"));
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
        assertThrows(ParseException.class, () -> parser.parse("member"));
        assertThrows(ParseException.class, () -> parser.parse("event"));
    }

    @Test
    public void parse_invalidType_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("random n/test"));
    }

    @Test
    public void parse_missingPrefixAfterMember_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("member Alice"));
    }

    @Test
    public void parse_missingPrefixAfterEvent_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("event Ideate"));
    }

    @Test
    public void parse_emptyKeywordAfterPrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("member n/   "));
        assertThrows(ParseException.class, () -> parser.parse("event n/  "));
    }

    @Test
    public void parse_multiplePrefixesForMember_throwsParseException() {
        // n/ followed by y/ somewhere in the same string
        assertThrows(ParseException.class, () -> parser.parse("member n/Alice y/1"));
    }

    @Test
    public void parse_multiplePrefixesForEvent_throwsParseException() {
        // n/ followed by v/ somewhere in the same string
        assertThrows(ParseException.class, () -> parser.parse("event n/Ideate v/UTown"));
    }

    @Test
    public void parse_memberWithEventPrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("member l/UTown"));
    }

    @Test
    public void parse_eventWithMemberPrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("event y/2"));
    }
}
