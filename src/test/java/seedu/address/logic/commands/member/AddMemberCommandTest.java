package seedu.address.logic.commands.member;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.AliasBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Unit tests for {@link AddMemberCommand}.
 */
public class AddMemberCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddMemberCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        AddMemberCommand command = new AddMemberCommand(validPerson);
        CommandResult result = command.execute(modelStub);

        assertEquals(CommandResult.showMembers(String.format(
                AddMemberCommand.MESSAGE_SUCCESS, Messages.format(validPerson))), result);
        assertEquals(List.of(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        ModelStub modelStub = new ModelStubWithPerson(validPerson);
        AddMemberCommand command = new AddMemberCommand(validPerson);

        String expectedMessage = "Duplicate member: another member already uses the same "
                + "phone number and email address.";
        assertThrows(CommandException.class, expectedMessage, () -> command.execute(modelStub));
    }

    @Test
    public void execute_duplicatePersonSamePhone_throwsCommandException() {
        Person existing = new PersonBuilder().withName("Existing Member")
                .withPhone("91234567").withEmail("existing@u.nus.edu").build();
        Person toAdd = new PersonBuilder().withName("Different Name")
                .withPhone("91234567").withEmail("different@u.nus.edu").build();

        ModelStub modelStub = new ModelStubWithPerson(existing);
        AddMemberCommand command = new AddMemberCommand(toAdd);

        String expectedMessage = "Duplicate member: another member already uses the same phone number.";
        assertThrows(CommandException.class, expectedMessage, () -> command.execute(modelStub));
    }

    @Test
    public void execute_duplicatePersonSameEmailDifferentCase_throwsCommandException() {
        Person existing = new PersonBuilder().withName("Existing Member")
                .withPhone("91234567").withEmail("existing@u.nus.edu").build();
        Person toAdd = new PersonBuilder().withName("Different Name")
                .withPhone("98765432").withEmail("EXISTING@u.nus.edu").build();

        ModelStub modelStub = new ModelStubWithPerson(existing);
        AddMemberCommand command = new AddMemberCommand(toAdd);

        String expectedMessage = "Duplicate member: another member already uses the same email address.";
        assertThrows(CommandException.class, expectedMessage, () -> command.execute(modelStub));
    }

    @Test
    public void execute_sameNameDifferentContact_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person existing = new PersonBuilder().withName("Alex Yeoh")
                .withPhone("91230000").withEmail("alex@u.nus.edu").build();
        modelStub.addPerson(existing);

        Person newPerson = new PersonBuilder().withName("Alex Yeoh")
                .withPhone("93334444").withEmail("alex.new@u.nus.edu").build();

        AddMemberCommand command = new AddMemberCommand(newPerson);
        CommandResult result = command.execute(modelStub);

        assertEquals(CommandResult.showMembers(String.format(
                AddMemberCommand.MESSAGE_SUCCESS, Messages.format(newPerson))), result);
        assertEquals(List.of(existing, newPerson), modelStub.personsAdded);
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddMemberCommand addAliceCommand = new AddMemberCommand(alice);
        AddMemberCommand addBobCommand = new AddMemberCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddMemberCommand addAliceCommandCopy = new AddMemberCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        Person alice = new PersonBuilder().withName("Alice Pauline").build();
        AddMemberCommand addAliceCommand = new AddMemberCommand(alice);
        String expected = AddMemberCommand.class.getCanonicalName() + "{toAdd=" + alice + "}";
        assertEquals(expected, addAliceCommand.toString());
    }

    /**
     * A default model stub that has all methods failing.
     */
    private static class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetMembers() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetEvents() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEvent(Event target, Event editedEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAlias(Alias alias) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAlias(Alias alias) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCommand(String commandWord) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCommand(Alias alias) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isAliasBookEmpty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeAlias(String commandWord) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeAlias(Alias alias) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void replaceAlias(Alias alias) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public AliasBook getAliasBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Alias> getAliasList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearAllAliases() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A model stub containing a single person.
     */
    private static class ModelStubWithPerson extends ModelStub {
        private final List<Person> persons = new ArrayList<>();

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.persons.add(person);
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return persons.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            AddressBook addressBook = new AddressBook();
            for (Person p : persons) {
                addressBook.addPerson(p);
            }
            return addressBook;
        }
    }

    /**
     * A model stub that always accepts the person being added.
     */
    private static class ModelStubAcceptingPersonAdded extends ModelStub {
        final List<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            AddressBook addressBook = new AddressBook();
            for (Person person : personsAdded) {
                addressBook.addPerson(person);
            }
            return addressBook;
        }
    }
}

