//package seedu.address.logic.parser;
//
//import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
//import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
//import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.Messages;
//import seedu.address.logic.commands.member.EditMemberCommand;
//import seedu.address.logic.commands.member.EditMemberCommand.EditMemberDescriptor;
//import seedu.address.model.person.Email;
//import seedu.address.model.person.Name;
//import seedu.address.model.person.Phone;
//import seedu.address.model.tag.Tag;
//import seedu.address.model.person.Year;
//import seedu.address.testutil.EditMemberDescriptorBuilder;
//
///**
// * Contains unit tests for {@code EditCommandParser}.
// */
//public class EditCommandParserTest {
//
//    private static final String MESSAGE_INVALID_FORMAT =
//            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMemberCommand.MESSAGE_USAGE);
//
//    private final EditCommandParser parser = new EditCommandParser();
//
//    @Test
//    public void parse_missingParts_failure() {
//        // no index specified
//        assertParseFailure(parser, "member n/Amy", MESSAGE_INVALID_FORMAT);
//
//        // no field specified
//        assertParseFailure(parser, "member 1", EditMemberCommand.MESSAGE_NOT_EDITED);
//
//        // no index and no field specified
//        assertParseFailure(parser, "member", MESSAGE_INVALID_FORMAT);
//    }
//
//    @Test
//    public void parse_invalidPreamble_failure() {
//        // negative index
//        assertParseFailure(parser, "member -5 n/Amy", MESSAGE_INVALID_FORMAT);
//
//        // zero index
//        assertParseFailure(parser, "member 0 n/Amy", MESSAGE_INVALID_FORMAT);
//
//        // invalid arguments being parsed as preamble
//        assertParseFailure(parser, "member 1 some random string", MESSAGE_INVALID_FORMAT);
//
//        // invalid prefix being parsed as preamble
//        assertParseFailure(parser, "member 1 i/ string", MESSAGE_INVALID_FORMAT);
//    }
//
//    @Test
//    public void parse_invalidValue_failure() {
//        // invalid name
//        assertParseFailure(parser, "member 1 n/!@#", Name.MESSAGE_CONSTRAINTS);
//
//        // invalid phone
//        assertParseFailure(parser, "member 1 p/abcd", Phone.MESSAGE_CONSTRAINTS_NUMBER);
//
//        // invalid email
//        assertParseFailure(parser, "member 1 e/amy@", Email.MESSAGE_CONSTRAINTS);
//
//        // invalid year
//        assertParseFailure(parser, "member 1 y/notayear", Year.MESSAGE_CONSTRAINTS);
//
//        // invalid tag
//        assertParseFailure(parser, "member 1 r/@", Tag.MESSAGE_CONSTRAINTS);
//
//        // invalid phone followed by valid email
//        assertParseFailure(parser, "member 1 p/abcd e/amy@example.com", Phone.MESSAGE_CONSTRAINTS_NUMBER);
//
//        // empty tag
//        assertParseFailure(parser, "member 1 r/",
//                "Tag cannot be empty. Key in a role after 'r/'.");
//
//        // multiple invalid values, only first invalid captured
//        assertParseFailure(parser, "member 1 n/!@# e/invalid@", Name.MESSAGE_CONSTRAINTS);
//    }
//
//    @Test
//    public void parse_allFieldsSpecified_success() {
//        Index targetIndex = INDEX_SECOND_PERSON;
//        String userInput = "member " + targetIndex.getOneBased()
//                + " n/Amy Bee p/91234567 e/amy@u.nus.edu y/2 r/friend r/classmate";
//
//        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder()
//                .withName("Amy Bee")
//                .withPhone("91234567")
//                .withEmail("amy@u.nus.edu")
//                .withYear("2")
//                .withTags("friend", "classmate")
//                .build();
//
//        EditMemberCommand expectedCommand = new EditMemberCommand(targetIndex, descriptor);
//        assertParseSuccess(parser, userInput, expectedCommand);
//    }
//
//    @Test
//    public void parse_someFieldsSpecified_success() {
//        Index targetIndex = INDEX_FIRST_PERSON;
//        String userInput = "member " + targetIndex.getOneBased() + " p/99999999 e/amy@u.nus.edu";
//
//        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder()
//                .withPhone("99999999")
//                .withEmail("amy@u.nus.edu")
//                .build();
//
//        EditMemberCommand expectedCommand = new EditMemberCommand(targetIndex, descriptor);
//        assertParseSuccess(parser, userInput, expectedCommand);
//    }
//
//    @Test
//    public void parse_oneFieldSpecified_success() {
//        Index targetIndex = INDEX_THIRD_PERSON;
//
//        // name
//        String userInput = "member " + targetIndex.getOneBased() + " n/Amy Bee";
//        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder().withName("Amy Bee").build();
//        assertParseSuccess(parser, userInput, new EditMemberCommand(targetIndex, descriptor));
//
//        // phone
//        userInput = "member " + targetIndex.getOneBased() + " p/91234567";
//        descriptor = new EditMemberDescriptorBuilder().withPhone("91234567").build();
//        assertParseSuccess(parser, userInput, new EditMemberCommand(targetIndex, descriptor));
//
//        // email
//        userInput = "member " + targetIndex.getOneBased() + " e/amy@u.nus.edu";
//        descriptor = new EditMemberDescriptorBuilder().withEmail("amy@u.nus.edu").build();
//        assertParseSuccess(parser, userInput, new EditMemberCommand(targetIndex, descriptor));
//
//        // year
//        userInput = "member " + targetIndex.getOneBased() + " y/3";
//        descriptor = new EditMemberDescriptorBuilder().withYear("3").build();
//        assertParseSuccess(parser, userInput, new EditMemberCommand(targetIndex, descriptor));
//
//        // tags
//        userInput = "member " + targetIndex.getOneBased() + " r/friend";
//        descriptor = new EditMemberDescriptorBuilder().withTags("friend").build();
//        assertParseSuccess(parser, userInput, new EditMemberCommand(targetIndex, descriptor));
//    }
//
//    @Test
//    public void parse_multipleRepeatedFields_failure() {
//        Index targetIndex = INDEX_FIRST_PERSON;
//        String userInput = "member " + targetIndex.getOneBased()
//                + " p/91234567 p/99999999";
//
//        assertParseFailure(parser, userInput,
//                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_PHONE));
//
//        userInput = "member " + targetIndex.getOneBased()
//                + " e/amy@u.nus.edu e/amy2@u.nus.edu";
//
//        assertParseFailure(parser, userInput,
//                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_EMAIL));
//    }
//
//    @Test
//    public void parse_resetTags_throwsParseException() {
//        Index targetIndex = INDEX_THIRD_PERSON;
//        String userInput = "member " + targetIndex.getOneBased() + " r/";
//
//        String expectedMessage = "Tag cannot be empty. Key in a role after 'r/'.";
//        assertParseFailure(parser, userInput, expectedMessage);
//    }
//
//}
