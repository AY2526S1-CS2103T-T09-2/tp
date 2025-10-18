package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TYPE;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.event.FindEventLocationCommand;
import seedu.address.logic.commands.event.FindEventNameCommand;
import seedu.address.logic.commands.member.FindMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.model.event.LocationContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        // Split the current args input into at most 2 parts: [type, index]
        String[] argsParts = args.trim().split("\\s+", 2);

        if (argsParts.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String type = argsParts[0];
        String keywords = argsParts[1];
        boolean isInvalidType =
                !type.equalsIgnoreCase("member") && !type.equalsIgnoreCase("event");

        if (isInvalidType) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_TYPE, FindCommand.MESSAGE_USAGE));
        }

        if (type.equalsIgnoreCase("member")) {
            return checkFindMemberType(keywords);
        } else { // event
            return checkFindEventType(keywords);
        }
    }

    /**
     * needs to change implementation of this method (sujith)
     */
    private FindCommand checkFindMemberType(String remainingArgs) throws ParseException {
        String[] keywords = remainingArgs.trim().split("\\s+");
        if (keywords.length == 0) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return new FindMemberCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
    }

    /**
     * Parses and returns either FindEventNameCommand or FindEventLocationCommand.
     * Expects a prefix n/ or l/.
     */
    private FindCommand checkFindEventType(String remainingArgs) throws ParseException {
        remainingArgs = remainingArgs.trim();
        if (remainingArgs.startsWith("n/")) {
            return getFindEventNameCommand(remainingArgs);
        } else if (remainingArgs.startsWith("l/")) {
            return getFindEventLocationCommand(remainingArgs);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_TYPE, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Creates a {@code FindEventLocationCommand} by parsing keywords after the "l/" prefix.
     *
     * @param remainingArgs user input after "find event"
     * @return command to find events by location
     * @throws ParseException if no keywords are provided or format is invalid
     */
    private static FindEventLocationCommand getFindEventLocationCommand(String remainingArgs) throws ParseException {
        String keywordsPart = remainingArgs.substring(2).trim(); // remove l/
        if (keywordsPart.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        String[] locationKeywords = keywordsPart.split("\\s+");
        return new FindEventLocationCommand(
                new LocationContainsKeywordsPredicate(Arrays.asList(locationKeywords)));
    }

    /**
     * Creates a {@code FindEventNameCommand} by parsing keywords after the "n/" prefix.
     *
     * @param remainingArgs user input after "find event"
     * @return command to find events by name
     * @throws ParseException if no keywords are provided or format is invalid
     */
    private static FindEventNameCommand getFindEventNameCommand(String remainingArgs) throws ParseException {
        String keywordsPart = remainingArgs.substring(2).trim(); // remove n/
        if (keywordsPart.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        String[] nameKeywords = keywordsPart.split("\\s+");
        return new FindEventNameCommand(
                new EventNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
