package seedu.address.logic.commands.member;

import java.util.List;
import java.util.Optional;

import seedu.address.model.person.Person;

/**
 * Utility functions for constructing duplicate member messages.
 */
final class MemberDuplicateMessageUtil {

    private MemberDuplicateMessageUtil() {
        // Utility class
    }

    /**
     * Builds a duplicate member message, if any, when {@code candidate} conflicts with existing persons.
     *
     * @param persons   the list of existing persons to check against
     * @param candidate the person being added or edited
     * @param toIgnore  a person to ignore during the check (e.g. the person being edited), may be {@code null}
     * @return an {@link Optional} containing the duplicate message if a clash exists, otherwise
     *         {@link Optional#empty()}
     */
    static Optional<String> buildDuplicateIdentityMessage(List<Person> persons, Person candidate, Person toIgnore) {
        boolean phoneClash = persons.stream()
                .filter(existing -> toIgnore == null || !existing.equals(toIgnore))
                .anyMatch(existing -> existing.getPhone().equals(candidate.getPhone()));
        boolean emailClash = persons.stream()
                .filter(existing -> toIgnore == null || !existing.equals(toIgnore))
                .anyMatch(existing -> existing.getEmail().value.equalsIgnoreCase(candidate.getEmail().value));

        if (!phoneClash && !emailClash) {
            return Optional.empty();
        }

        String phoneReason = "phone number";
        String emailReason = "email address";
        String reason;
        if (phoneClash && emailClash) {
            reason = phoneReason + " and " + emailReason;
        } else if (phoneClash) {
            reason = phoneReason;
        } else {
            reason = emailReason;
        }
        return Optional.of("Duplicate member: another member already uses the same " + reason + ".");
    }
}
