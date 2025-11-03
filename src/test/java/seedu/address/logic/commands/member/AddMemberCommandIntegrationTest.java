package seedu.address.logic.commands.member;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.AliasBook;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@link AddMemberCommand}.
 */
public class AddMemberCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();
        Model expectedModel = new ModelManager(model.getAddressBook(), new AliasBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        CommandResult expectedCommandResult = CommandResult.showMembers(String.format(
                AddMemberCommand.MESSAGE_SUCCESS, Messages.format(validPerson)));

        assertCommandSuccess(new AddMemberCommand(validPerson), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        String expectedMessage = "Duplicate member: another member already uses the same "
                + "phone number and email address.";
        assertCommandFailure(new AddMemberCommand(personInList), model, expectedMessage);
    }

    @Test
    public void execute_sameNameDifferentContact_success() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        Person newPerson = new PersonBuilder().withName(personInList.getName().fullName)
                .withPhone("93334444").withEmail("uniquealex@u.nus.edu").withYear("1").build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new AliasBook(), new UserPrefs());
        expectedModel.addPerson(newPerson);

        CommandResult expectedCommandResult = CommandResult.showMembers(String.format(
                AddMemberCommand.MESSAGE_SUCCESS, Messages.format(newPerson)));

        assertCommandSuccess(new AddMemberCommand(newPerson), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_samePhoneDifferentName_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        Person duplicatePhonePerson = new PersonBuilder().withName("Different Name")
                .withPhone(personInList.getPhone().value)
                .withEmail("differentphone@u.nus.edu").withYear("2").build();

        String expectedMessage = "Duplicate member: another member already uses the same phone number.";
        assertCommandFailure(new AddMemberCommand(duplicatePhonePerson), model,
                expectedMessage);
    }

    @Test
    public void execute_sameEmailDifferentName_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        Person duplicateEmailPerson = new PersonBuilder().withName("Another Name")
                .withPhone("93445566").withEmail(personInList.getEmail().value.toUpperCase()).withYear("3").build();

        String expectedMessage = "Duplicate member: another member already uses the same email address.";
        assertCommandFailure(new AddMemberCommand(duplicateEmailPerson), model,
                expectedMessage);
    }
}

