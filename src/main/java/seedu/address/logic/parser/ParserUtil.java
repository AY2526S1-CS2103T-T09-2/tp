package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.alias.Alias;
import seedu.address.model.event.Date;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Venue;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;
import seedu.address.model.person.Year;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Checks if the string is equal to member and it is not case-sensitive
     *
     * @param type Input string
     * @return True if it is equal to "member", otherwise false
     */
    public static boolean isMember(String type) {
        return type.equalsIgnoreCase("member");
    }

    /**
     * Checks if the string is equal to event and it is not case-sensitive
     *
     * @param type Input string
     * @return True if it is equal to "event", otherwise false
     */
    public static boolean isEvent(String type) {
        return type.equalsIgnoreCase("event");
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String normalizedName = Name.normalizeWhitespace(name);
        if (!Name.isValidName(normalizedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(normalizedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();

        // 1) No internal whitespace
        if (Phone.hasInternalWhitespace(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS_SPACES);
        }
        // Internal invariant after validation
        assert !Phone.hasInternalWhitespace(trimmedPhone) : "Invariant: phone must not contain internal whitespace";
        // 2) Only digits
        if (!Phone.isDigitsOnly(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS_NUMBER);
        }
        // Internal invariant after validation
        assert Phone.isDigitsOnly(trimmedPhone) : "Invariant: phone must be digits-only";

        // 3) Exactly 8 digits
        if (!Phone.isValidLength(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS_LENGTH);
        }
        // Internal invariant after validation
        assert Phone.isValidLength(trimmedPhone) : "Invariant: phone must be exactly 8 digits";

        // 4) Starts with 8 or 9
        if (!Phone.isValidStart(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS_START);
        }
        // Internal invariant after validation
        assert Phone.isValidStart(trimmedPhone) : "Invariant: phone must start with 8 or 9";
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String year} into a {@code Year}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code year} is invalid.
     */
    public static Year parseYear(String year) throws ParseException {
        requireNonNull(year);
        String trimmedYear = year.trim();
        if (!Year.isValidYear(trimmedYear)) {
            throw new ParseException(Year.MESSAGE_CONSTRAINTS);
        }
        return new Year(trimmedYear);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Role parseRole(String role) throws ParseException {
        requireNonNull(role);
        String normalizedRoleName = Role.normalizeWhitespace(role);
        if (!Role.isValidRoleName(normalizedRoleName)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Role(role);
    }

    /**
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String eventName} into a {@code Event}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code event} is invalid.
     */
    public static EventName parseEventName(String eventName) throws ParseException {
        requireNonNull(eventName);
        String normalizedEventName = EventName.normalizeWhitespace(eventName);
        if (!EventName.isValidEventName(normalizedEventName)) {
            throw new ParseException(EventName.MESSAGE_CONSTRAINTS);
        }
        return new EventName(normalizedEventName);
    }

    /**
     * Parses a {@code String venue} into a {@code Venue}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code venue} is invalid.
     */
    public static Venue parseVenue(String venue) throws ParseException {
        requireNonNull(venue);
        String trimmedVenue = venue.trim();
        if (!Venue.isValidVenue(trimmedVenue)) {
            throw new ParseException(Venue.MESSAGE_CONSTRAINTS);
        }
        return new Venue(trimmedVenue);
    }

//    /**
//     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
//     */
//    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
//        requireNonNull(tags);
//        final Set<Tag> tagSet = new HashSet<>();
//        for (String tagName : tags) {
//            tagSet.add(parseTag(tagName));
//        }
//        return tagSet;
//    }

    /**
     * Parses a {@code String commandWord} and {@code String aliasWord} into a {@code Alias}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Alias} is invalid.
     */
    public static Alias parseAlias(String commandWord, String aliasWord) throws ParseException {
        requireNonNull(commandWord, aliasWord);
        String trimmedCommandWord = commandWord.trim();
        String trimmedAliasWord = aliasWord.trim();

        if (!Alias.isValidCommandWord(trimmedCommandWord)) {
            throw new ParseException(Alias.MESSAGE_CONSTRAINS_COMMAND_WORD);
        }

        if (!Alias.isValidAliasWord(trimmedAliasWord)) {
            throw new ParseException(Alias.MESSAGE_CONSTRAINS_ALIAS_WORD);
        }

        return new Alias(commandWord, aliasWord);
    }
}
