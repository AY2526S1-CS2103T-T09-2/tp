package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.event.EditEventCommand;
import seedu.address.logic.commands.member.EditMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] argsParts = args.trim().split("\\s+", 2);
        if (argsParts.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        String type = argsParts[0];
        String keywords = argsParts[1];
        if (ParserUtil.isMember(type)) {
            return createEditMemberCommand(keywords);
        } else if (ParserUtil.isEvent(type)) {
            return createEditEventCommand(keywords);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses input for an {@link EditMemberCommand}.
     *
     * @param keywords The user input string containing parameters for editing a member.
     * @return An {@link EditMemberCommand} with parsed values.
     * @throws ParseException If the input format is invalid or missing required parameters.
     */
    private EditMemberCommand createEditMemberCommand(String keywords) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(keywords,
                        PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_YEAR, PREFIX_ROLE);
        Index index;
        return getEditMemberCommand(argMultimap);
    }

    /**
     * Builds an {@link EditMemberCommand} using parsed arguments from the provided {@link ArgumentMultimap}.
     *
     * @param argMultimap A map of argument prefixes to their respective values.
     * @return A fully constructed {@link EditMemberCommand}.
     * @throws ParseException If any field is invalid or no editable fields are specified.
     */
    private EditMemberCommand getEditMemberCommand(ArgumentMultimap argMultimap) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_YEAR, PREFIX_ROLE);
        EditMemberCommand.EditMemberDescriptor editPersonDescriptor = new EditMemberCommand.EditMemberDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_YEAR).isPresent()) {
            editPersonDescriptor.setYear(ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get()));
        }

        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
           editPersonDescriptor.setRole(ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get()));
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditMemberCommand.MESSAGE_NOT_EDITED);
        }
        return new EditMemberCommand(index, editPersonDescriptor);
    }

    /**
     * Parses input for an {@link EditEventCommand}.
     *
     * @param keywords The user input string containing parameters for editing an event.
     * @return An {@link EditEventCommand} with parsed values.
     * @throws ParseException If the input format is invalid or missing required parameters.
     */
    private EditEventCommand createEditEventCommand(String keywords) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(keywords,
                        PREFIX_NAME, PREFIX_DATE, PREFIX_LOCATION);
        Index index;

        return getEditEventCommand(argMultimap);
    }

    /**
     * Builds an {@link EditEventCommand} using parsed arguments from the provided {@link ArgumentMultimap}.
     *
     * @param argMultimap A map of argument prefixes to their respective values.
     * @return A fully constructed {@link EditEventCommand}.
     * @throws ParseException If any field is invalid or no editable fields are specified.
     */
    private static EditEventCommand getEditEventCommand(ArgumentMultimap argMultimap) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DATE, PREFIX_LOCATION);

        EditEventCommand.EditEventDescriptor editEventDescriptor = new EditEventCommand.EditEventDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editEventDescriptor.setEventName(ParserUtil.parseEventName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editEventDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            editEventDescriptor.setVenue(ParserUtil.parseVenue(argMultimap.getValue(PREFIX_LOCATION).get()));
        }
        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEventCommand.MESSAGE_NOT_EDITED);
        }
        return new EditEventCommand(index, editEventDescriptor);
    }

//    /**
//     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
//     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
//     * {@code Set<Tag>} containing zero tags.
//     */
//    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
//        assert tags != null;
//
//        if (tags.isEmpty()) {
//            return Optional.empty();
//        }
//        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
//        return Optional.of(ParserUtil.parseTags(tagSet));
//    }

}
