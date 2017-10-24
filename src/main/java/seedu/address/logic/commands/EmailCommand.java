package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_BODY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_TO;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.email.Body;
import seedu.address.model.email.Subject;
import seedu.address.model.person.PersonContainsTagPredicate;

/**
 * Emails all people with a particular tag either using gmail/outlook
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String MESSAGE_EMAIL_SENT = "Email .";
    public static final String MESSAGE_NOT_SENT = "Please enter a valid name/tag with a valid Email ID.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":  people in the Address Book.\n"
            + "The 'to' field is compulsory\n"
            + "The 'to' field can take tags and it also supports more than one parameter.\n"
            + "Parameters: "
            + PREFIX_EMAIL_TO + "TAGS "
            + PREFIX_EMAIL_SUBJECT + "SUBJECT "
            + PREFIX_EMAIL_BODY + "BODY \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EMAIL_TO + "cs2103 cs2101"
            + PREFIX_EMAIL_SUBJECT + "Meeting "
            + PREFIX_EMAIL_BODY + "On Monday ";

    private final PersonContainsTagPredicate predicate;
    private final Subject subject;
    private final Body body;

    public EmailCommand(PersonContainsTagPredicate predicate, Subject subject, Body body) {
        this.predicate = predicate;
        this.subject = subject;
        this.body = body;

    }

    /**
     * Calls event for opening the url in browser panel
     * @param recipients String of all recipeints
     * @throws ParseException when wrong recipients
     */
    public void processEmail(String recipients) throws ParseException {
        requireNonNull(recipients);

        if (recipients.equals("")) {
            throw new ParseException("Invalid recipients");
        }
        model.processEmailEvent(recipients, subject, body);
    }


    @Override
    public CommandResult execute() {
        String emailTo = model.createEmailRecipients(predicate);
        try {
            processEmail(emailTo);
        } catch (ParseException e) {
            e.printStackTrace();
            return new CommandResult(MESSAGE_NOT_SENT);
        }
        return new CommandResult(MESSAGE_EMAIL_SENT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.predicate.equals(((EmailCommand) other).predicate)); // state check
    }
}
