package seedu.address.logic.commands.member;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.Year;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditMemberCommand extends EditCommand {

    public static final String MESSAGE_EDIT_MEMBER_SUCCESS = "Edited Member: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This member already exists in the address book.";

    private final Index index;
    private final EditMemberDescriptor editMemberDescriptor;

    // ======================================= EditMemberDescriptor class =======================================
    /**
     * A descriptor class that stores details to edit a {@code Person}.
     * Each non-null field value replaces the corresponding field value of the target {@code Person}.
     */
    public static class EditMemberDescriptor extends EditCommand.EditDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Year year;
        private Role role;

        public EditMemberDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditMemberDescriptor(EditMemberDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setYear(toCopy.year);
            setRole(toCopy.role);
        }

        /**
         * Returns true if at least one field is edited.
         */
        @Override
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, year, role);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setYear(Year year) {
            this.year = year;
        }

        public Optional<Year> getYear() {
            return Optional.ofNullable(year);
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public Optional<Role> getRole() {
            return Optional.ofNullable(role);
        }

//        /**
//         * Sets {@code tags} to this object's {@code tags}.
//         * A defensive copy of {@code tags} is used internally.
//         */
//        @Override
//        public void setTags(Set<Tag> tags) {
//            this.tags = (tags != null) ? new HashSet<>(tags) : null;
//        }
//
//        /**
//         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
//         * if modification is attempted.
//         * Returns {@code Optional#empty()} if {@code tags} is null.
//         */
//        @Override
//        public Optional<Set<Tag>> getTags() {
//            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
//        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditMemberDescriptor)) {
                return false;
            }

            EditMemberDescriptor otherEditPersonDescriptor = (EditMemberDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(year, otherEditPersonDescriptor.year)
                    && Objects.equals(role, otherEditPersonDescriptor.role);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("year", year)
                    .add("role", role)
                    .toString();
        }
    }
    // ==================================================================================================

    /**
     * @param index of the member in the filtered member list to edit
     * @param editPersonDescriptor details to edit the member with
     */
    public EditMemberCommand(Index index, EditMemberDescriptor editPersonDescriptor) {
        super();
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editMemberDescriptor = new EditMemberDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editMemberDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return CommandResult.showMembers(
                String.format(MESSAGE_EDIT_MEMBER_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditMemberDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Year updatedYear = editPersonDescriptor.getYear().orElse(personToEdit.getYear());
        Role updatedRole = editPersonDescriptor.getRole().orElse(personToEdit.getRole());
//        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        int attendanceCount = personToEdit.getAttendanceCount(); // Preserve attendance count

        return new Person(updatedName, updatedPhone, updatedEmail, updatedYear, updatedRole, attendanceCount);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditMemberCommand)) {
            return false;
        }

        EditMemberCommand otherEditCommand = (EditMemberCommand) other;
        return index.equals(otherEditCommand.index)
                && editMemberDescriptor.equals(otherEditCommand.editMemberDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editMemberDescriptor", editMemberDescriptor)
                .toString();
    }


}
