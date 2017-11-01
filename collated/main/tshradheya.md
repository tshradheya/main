# tshradheya
###### \java\seedu\address\commons\events\model\DisplayPictureChangedEvent.java
``` java
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Event to trigger reading and storing of image
 */
public class DisplayPictureChangedEvent extends BaseEvent {

    public final String path;
    public final int newPath;
    private boolean isRead;

    public DisplayPictureChangedEvent(String path, int newPath) {
        this.path = path;
        this.newPath = newPath;
        isRead = true;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean b) {
        isRead = b;
    }

    @Override
    public String toString() {
        return newPath + " stored";
    }
}
```
###### \java\seedu\address\commons\events\model\PopularContactChangedEvent.java
``` java
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.Model;

/**
 * Event to handle change of popular contact list
 */
public class PopularContactChangedEvent extends BaseEvent {

    private Model model;

    public PopularContactChangedEvent(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    @Override
    public String toString() {
        return "Updated Popular Contact List";
    }
}
```
###### \java\seedu\address\commons\events\ui\PopularContactPanelSelectionChangedEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.PopularContactCard;

/**
 * Represents a selection change in the Popular Contact Panel
 */
public class PopularContactPanelSelectionChangedEvent extends BaseEvent {

    private final PopularContactCard newSelection;

    public PopularContactPanelSelectionChangedEvent(PopularContactCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PopularContactCard getNewSelection() {
        return newSelection;
    }
}
```
###### \java\seedu\address\commons\events\ui\SendingEmailEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;

/**
 * Event raised on 'email' command's successful execution
 */
public class SendingEmailEvent extends BaseEvent {

    public final String recipients;
    public final Subject subject;
    public final Body body;
    public final Service service;

    public SendingEmailEvent(String recipients, Subject subject, Body body, Service service) {
        this.recipients = recipients;
        this.subject = subject;
        this.body = body;
        this.service = service;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowLocationEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Event raised on 'location' command's successful execution
 */
public class ShowLocationEvent extends BaseEvent {

    public final ReadOnlyPerson person;

    public ShowLocationEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\DisplayPictureCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISPLAYPICTURE;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.DisplayPicture;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds a display picture to an existing person in address book
 */
public class DisplayPictureCommand extends Command {

    public static final String COMMAND_WORD = "displaypic";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds/Updates the profile picture of a person identified "
            + "by the index number used in the last person listing. "
            + "Existing Display picture will be updated by the image referenced in the input path. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DISPLAYPICTURE + "[PATH]\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + "C:\\Users\\Admin\\Desktop\\pic.jpg";

    public static final String MESSAGE_ADD_DISPLAYPICTURE_SUCCESS = "Added Display Picture to Person: %1$s";

    public static final String MESSAGE_DELETE_DISPLAYPICTURE_SUCCESS = "Removed Display Picture from Person: %1$s";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    public static final String MESSAGE_IMAGE_PATH_FAIL =
            "This specified path cannot be read. Please check it's validity and try again";


    private Index index;
    private DisplayPicture displayPicture;

    /**
     * @param index of the person in the filtered person list to edit the remark
     * @param displayPicture of the person
     */
    public DisplayPictureCommand(Index index, DisplayPicture displayPicture) {
        requireNonNull(index);
        requireNonNull(displayPicture);

        this.index = index;
        this.displayPicture = displayPicture;
    }

    @Override
    public CommandResult execute() throws CommandException, IOException, URISyntaxException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());

        if (displayPicture.getPath().equalsIgnoreCase("")) {
            displayPicture.setPath("");

            Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getBirthday(), personToEdit.getNickname(),
                    displayPicture, personToEdit.getPopularityCounter(), personToEdit.getTags());

            try {
                model.updatePerson(personToEdit, editedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
            model.updateFilteredListToShowAll();

            return new CommandResult(generateSuccessMessage(editedPerson));
        }


        boolean isExecutedProperly = model.addDisplayPicture(displayPicture.getPath(),
                    personToEdit.getEmail().hashCode());
        if (isExecutedProperly) {
            displayPicture.setPath(Integer.toString(personToEdit.getEmail().hashCode()));
        } else {
            displayPicture.setPath("");
            return new CommandResult(generateFailureMessage());
        }

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getBirthday(), personToEdit.getNickname(),
                displayPicture, personToEdit.getPopularityCounter(), personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredListToShowAll();

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates failure message
     * @return String
     */
    private String generateFailureMessage() {
        return MESSAGE_IMAGE_PATH_FAIL;
    }

    /**
     * Generates success message
     * @param personToEdit is checked
     * @return String
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!displayPicture.getPath().isEmpty()) {
            return String.format(MESSAGE_ADD_DISPLAYPICTURE_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_DISPLAYPICTURE_SUCCESS, personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DisplayPictureCommand)) {
            return false;
        }

        // state check
        DisplayPictureCommand e = (DisplayPictureCommand) other;
        return index.equals(e.index)
                && displayPicture.equals(e.displayPicture);
    }
}
```
###### \java\seedu\address\logic\commands\EmailCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_BODY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_SERVICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_TO;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;
import seedu.address.model.person.PersonContainsTagPredicate;

/**
 * Emails all people with a particular tag either using gmail/outlook
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String MESSAGE_EMAIL_SENT = "Email .";
    public static final String MESSAGE_NOT_SENT = "Please enter a valid name/tag with a valid Email ID.";
    public static final String EMAIL_SERVICE_GMAIL = "gmail";
    public static final String EMAIL_SERVICE_OUTLOOK = "outlook";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":  people in the Address Book.\n"
            + "The 'to' field is compulsory\n"
            + "The 'to' field can take tag and it only supports one parameter.\n"
            + "Parameters: "
            + PREFIX_EMAIL_SERVICE + "SERVICE "
            + PREFIX_EMAIL_TO + "TAGS "
            + PREFIX_EMAIL_SUBJECT + "SUBJECT "
            + PREFIX_EMAIL_BODY + "BODY \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EMAIL_SERVICE + "gmail"
            + PREFIX_EMAIL_TO + "cs2103"
            + PREFIX_EMAIL_SUBJECT + "Meeting "
            + PREFIX_EMAIL_BODY + "On Monday ";

    private PersonContainsTagPredicate predicate;
    private Subject subject;
    private Body body;
    private Service service;

    public EmailCommand(PersonContainsTagPredicate predicate, Subject subject, Body body, Service service) {
        this.predicate = predicate;
        this.subject = subject;
        this.body = body;
        this.service = service;

    }

    /**
     * Calls event for opening the url in browser panel
     * @param recipients String of all recipients
     * @throws ParseException when wrong recipients
     */
    public void processEmail(String recipients) throws ParseException {
        requireNonNull(recipients);

        if (recipients.equals("")) {
            throw new ParseException("Invalid recipients");
        }
        model.processEmailEvent(recipients, subject, body, service);
    }

    /**
     * Checks if service is one of the offered service of "gmail" or "outlook"
     * @param service mentioned by the user
     * @throws ParseException if not an offered service
     */
    public void checkServiceValid(Service service) throws ParseException {
        if (!service.service.equalsIgnoreCase(EMAIL_SERVICE_GMAIL)
                && !service.service.equalsIgnoreCase(EMAIL_SERVICE_OUTLOOK)) {
            throw new ParseException("Invalid service");
        }
    }


    @Override
    public CommandResult execute() {


        try {
            checkServiceValid(service);
            String emailTo = model.createEmailRecipients(predicate);
            processEmail(emailTo);
        } catch (ParseException e) {
            e.printStackTrace();
            return new CommandResult(MESSAGE_NOT_SENT);
        }
        return new CommandResult(MESSAGE_EMAIL_SENT);
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setPredicate(PersonContainsTagPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.predicate.equals(((EmailCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\LocationCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Shows location in Google maps of the specified person
 */
public class LocationCommand extends Command {

    public static final String COMMAND_WORD = "location";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays  the location of specified person. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FIND_LOCATION_SUCCESS = "Location of %1$s: %2$s";

    private final Index index;

    public LocationCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personWhoseLocationIsToBeShown = lastShownList.get(index.getZeroBased());

        try {
            model.updatePersonsPopularityCounterByOne(lastShownList.get(index.getZeroBased()));
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        try {
            model.showLocation(personWhoseLocationIsToBeShown);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_FIND_LOCATION_SUCCESS,
                personWhoseLocationIsToBeShown.getName().fullName, personWhoseLocationIsToBeShown.getAddress()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocationCommand // instanceof handles nulls
                && this.index.equals(((LocationCommand) other).index)); // state check
    }


}
```
###### \java\seedu\address\logic\commands\ViewTagCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.PersonContainsTagPredicate;

/**
 * Finds and lists all persons in address book who are associated with the tag given in keywords
 * Keyword matching is case sensitive.
 */

public class ViewTagCommand extends Command {

    public static final String COMMAND_WORD = "viewtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who are associated with the tag in "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " cs2103";


    private final PersonContainsTagPredicate predicate;

    public ViewTagCommand(PersonContainsTagPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonListForViewTag(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewTagCommand // instanceof handles nulls
                && this.predicate.equals(((ViewTagCommand) other).predicate)); // state check
    }










}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public ObservableList<ReadOnlyPerson> getListOfPersonsForPopularContacts() {
        return model.getPopularContactList();
    }

```
###### \java\seedu\address\logic\parser\DisplayPictureCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DisplayPictureCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DisplayPicture;

/**
 * Parses input arguments and creates a new DisplayPictureCommand object
 */
public class DisplayPictureCommandParser implements Parser<DisplayPictureCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DisplayPictureCommand
     * and returns an DisplayPictureCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayPictureCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String regex = "[\\s]+";
        String[] splitArgs = args.trim().split(regex, 2);

        Index index;
        try {
            index = ParserUtil.parseIndex(splitArgs[0]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayPictureCommand.MESSAGE_USAGE));
        }

        String path;
        if (splitArgs.length > 1) {
            path = splitArgs[1];
        } else {
            path = "";
        }


        return new DisplayPictureCommand(index, new DisplayPicture(path));
    }
}
```
###### \java\seedu\address\logic\parser\EmailCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_BODY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_SERVICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_TO;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;
import seedu.address.model.person.PersonContainsTagPredicate;

/**
 * Parses the input command and creates a EmailCommand object
 */
public class EmailCommandParser implements Parser<EmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMAIL_SERVICE, PREFIX_EMAIL_TO,
                        PREFIX_EMAIL_SUBJECT, PREFIX_EMAIL_BODY);

        if (!arePrefixesPresent(argMultimap, PREFIX_EMAIL_SERVICE, PREFIX_EMAIL_TO)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }

        try {
            Service service = new Service(argMultimap.getValue(PREFIX_EMAIL_SERVICE).get());
            String tagToWhichEmailHasToBeSent =
                    ParserUtil.parseRecipientTag(argMultimap.getValue(PREFIX_EMAIL_TO).get());
            Subject subject = new Subject(String.join("",
                    argMultimap.getAllValues(PREFIX_EMAIL_SUBJECT)).replace(" ", "+"));
            Body body = new Body(String.join("",
                    argMultimap.getAllValues(PREFIX_EMAIL_BODY)).replace(" ", "+"));
            return new EmailCommand(new PersonContainsTagPredicate(tagToWhichEmailHasToBeSent), subject, body, service);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
```
###### \java\seedu\address\logic\parser\exceptions\ImageException.java
``` java
package seedu.address.logic.parser.exceptions;

import java.io.IOException;

/**
 * Represents a image reading error encountered by a parser.
 */
public class ImageException extends IOException {

    public ImageException(String message) {
        super(message);
    }
}
```
###### \java\seedu\address\logic\parser\LocationCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.LocationCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the `location' command
 */
public class LocationCommandParser implements Parser<LocationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LocationCommand
     * and returns an LocationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public LocationCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        Index index;
        try {
            index = ParserUtil.parseIndex(userInput);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocationCommand.MESSAGE_USAGE));
        }

        return new LocationCommand(index);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> displayPicture} into an {@code Optional<DisplayPicture>}
     * if {@code path} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<DisplayPicture> parseDisplayPicture(Optional<String> displayPicture)
            throws IllegalValueException {
        requireNonNull(displayPicture);
        return displayPicture.isPresent() ? Optional.of(new DisplayPicture(displayPicture.get())) : Optional.empty();
    }

    /**
     * Parses popularity counter for dummy purpose
     * @param popularityCounter
     * @return
     * @throws IllegalValueException
     */
    public static Optional<PopularityCounter> parsePopularityCounter(Optional<String> popularityCounter)
            throws IllegalValueException {
        requireNonNull(popularityCounter);
        return popularityCounter.isPresent()
                ? Optional.of(new PopularityCounter(Integer.parseInt(popularityCounter.get()))) : Optional.empty();
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java

    /**
     * Parses the given keyword tag into trimmed string
     * @param tag keyword given by user
     * @return trimmedTag which is trimmed for comparison purposes
     * @throws IllegalValueException
     */
    public static String parseRecipientTag(String tag) throws IllegalValueException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();

        return trimmedTag;
    }
```
###### \java\seedu\address\logic\parser\ViewTagCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsTagPredicate;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewTagCommandParser implements Parser<ViewTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTagCommand.MESSAGE_USAGE));
        }

        return new ViewTagCommand(new PersonContainsTagPredicate(trimmedArgs));
    }



}
```
###### \java\seedu\address\model\email\Body.java
``` java
package seedu.address.model.email;

import static java.util.Objects.requireNonNull;

/**
 * Represent's the body of an Email
 */
public class Body {

    public final String body;

    public Body(String body) {
        requireNonNull(body);
        String trimmedBody = body.trim();

        this.body = trimmedBody;
    }

    @Override
    public String toString() {
        return body;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Body // instanceof handles nulls
                && this.body.equals(((Body) other).body)); // state check
    }

    @Override
    public int hashCode() {
        return body.hashCode();
    }
}
```
###### \java\seedu\address\model\email\Service.java
``` java
package seedu.address.model.email;

import static java.util.Objects.requireNonNull;

/**
 * Specifies email service to use for processing email command
 */
public class Service {
    public final String service;

    public Service(String service) {
        requireNonNull(service);
        String trimmedService = service.trim();

        this.service = trimmedService;
    }

    @Override
    public String toString() {
        return service;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Service // instanceof handles nulls
                && this.service.equals(((Service) other).service)); // state check
    }

    @Override
    public int hashCode() {
        return service.hashCode();
    }
}
```
###### \java\seedu\address\model\email\Subject.java
``` java
package seedu.address.model.email;

import static java.util.Objects.requireNonNull;

/**
 * Represent's subject of the Email being sent
 */
public class Subject {

    public final String subject;

    public Subject(String subject) {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();

        this.subject = trimmedSubject;
    }

    @Override
    public String toString() {
        return subject;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Subject // instanceof handles nulls
                && this.subject.equals(((Subject) other).subject)); // state check
    }

    @Override
    public int hashCode() {
        return subject.hashCode();
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java

    /** Raises an event to indicate model has changed and favourite contacts might be updated */
    private void indicatePopularContactsChangedPossibility() {
        raise(new PopularContactChangedEvent(this));

    }

    /** Raises an event to indicate the picture has changed */
    private boolean indicateDisplayPictureChanged(String path, int newPath) throws IOException {
        DisplayPictureChangedEvent displayPictureChangedEvent = new DisplayPictureChangedEvent(path, newPath);
        raise(displayPictureChangedEvent);
        return displayPictureChangedEvent.isRead();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
        indicatePopularContactsChangedPossibility();
        updatePopularContactList();
    }

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
        indicatePopularContactsChangedPossibility();
        updatePopularContactList();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java

    @Override
    public boolean addDisplayPicture(String path, int newPath) throws IOException {
        return indicateDisplayPictureChanged(path, newPath);
    }

    /**
     * Shows location of the given person
     */
    @Override
    public void showLocation(ReadOnlyPerson person) throws PersonNotFoundException {
        raise(new ShowLocationEvent(person));
    }

    /**
     * Raises event for processing Email
     */
    @Override
    public void processEmailEvent(String recipients, Subject subject, Body body, Service service) {
        raise(new SendingEmailEvent(recipients, subject, body, service));
    }

    /**
     * Makes a string of all intended recipient through the given @predicate
     */
    @Override
    public String createEmailRecipients(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);

        filteredPersonsForEmail.setPredicate(predicate);
        increaseCounterByOneForATag(filteredPersonsForEmail);

        List<String> validEmails = new ArrayList<>();
        for (ReadOnlyPerson person : filteredPersonsForEmail) {
            if (Email.isValidEmail(person.getEmail().value)) {
                validEmails.add(person.getEmail().value);
            }
        }
        return String.join(",", validEmails);
    }

    @Override
    public void increaseCounterByOneForATag(List<ReadOnlyPerson> filteredPersonsForEmail) {

        for (ReadOnlyPerson person : filteredPersonsForEmail) {
            try {
                this.updatePersonsPopularityCounterByOne(person);
            } catch (DuplicatePersonException dpe) {
                assert false : "Duplicate";
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
        }
    }

    //=========== Update Popularity counter for the required commands =======================================

    /**
     * Increases the counter by 1 increasing popularity
     * @param person whose popularity counter increased
     */
    @Override
    public void updatePersonsPopularityCounterByOne(ReadOnlyPerson person) throws DuplicatePersonException,
            PersonNotFoundException {
        ReadOnlyPerson editedPerson = increaseCounterByOne(person);

        addressBook.updatePerson(person, editedPerson);
        indicateAddressBookChanged();
        indicatePopularContactsChangedPossibility();
    }

    @Override
    public ReadOnlyPerson increaseCounterByOne(ReadOnlyPerson person) {
        person.getPopularityCounter().increasePopularityCounter();
        return new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                person.getBirthday(), person.getNickname(), person.getDisplayPicture(), person.getPopularityCounter(),
                person.getTags());
    }

    //=========== Filtered Popular Contact List Accessors and Mutators =======================================

    /**
     * Updates the popular contact list whenever address book is changed
     */
    @Override
    public void updatePopularContactList() {
        refreshWithPopulatingAddressBook();
        listOfPersonsForPopularContacts.sort((o1, o2) ->
                o2.getPopularityCounter().getCounter() - o1.getPopularityCounter().getCounter());

        getOnlyTopFiveMaximum();
    }

    @Override
    public void getOnlyTopFiveMaximum() {
        while (listOfPersonsForPopularContacts.size() > 5) {
            listOfPersonsForPopularContacts.remove(listOfPersonsForPopularContacts.size() - 1);
        }
    }

    @Override
    public void refreshWithPopulatingAddressBook() {
        listOfPersonsForPopularContacts = new ArrayList<>(this.addressBook.getPersonList());
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPopularContactList() {
        updatePopularContactList();
        return FXCollections.observableList(listOfPersonsForPopularContacts);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java

    @Override
    public void updateFilteredPersonListForViewTag(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);

        increaseCounterByOneForATag(filteredPersons);
    }
```
###### \java\seedu\address\model\person\DisplayPicture.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represent's a person's display picture in the address book
 * Guarantees : immutable; is always valid
 */
public class DisplayPicture {


    private String path;

    public DisplayPicture(String path) {
        requireNonNull(path);
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayPicture // instanceof handles nulls
                && this.path.equals(((DisplayPicture) other).path)); // state check
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
```
###### \java\seedu\address\model\person\PersonContainsTagPredicate.java
``` java
package seedu.address.model.person;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tags} matches the keyword given.
 */

public class PersonContainsTagPredicate implements Predicate<ReadOnlyPerson> {

    private final String keyword;

    public PersonContainsTagPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        Set<Tag> tagsOfPerson = person.getTags();

        return tagsOfPerson.stream()
                .anyMatch(tagMatches -> tagMatches.getTagName().equalsIgnoreCase(keyword));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsTagPredicate // instanceof handles nulls
                && this.keyword.equals(((PersonContainsTagPredicate) other).keyword)); // state check
    }

}
```
###### \java\seedu\address\model\person\PopularityCounter.java
``` java
package seedu.address.model.person;

/**
 * Represents a Person's frequency of visits in address book.
 * Initially the value is 0 for all
 */
public class PopularityCounter {

    private int counter;

    public PopularityCounter() {
        counter = 0;
    }

    public PopularityCounter(int counter) {
        this.counter = counter;
    }

    /**
     * Increases popularity by 1 when searched or viewed by selecting
     */
    public void increasePopularityCounter() {
        counter++;
    }

    public void resetPopularityCounter() {
        counter = 0;
    }

    /**
     * Gets popularity counter to form the favourite list of contacts
     * @return popularity counter
     */
    public int getCounter() {
        return counter;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PopularityCounter // instanceof handles nulls
                && this.getCounter() == ((PopularityCounter) other).getCounter()); // state check
    }
}
```
###### \java\seedu\address\storage\AddressBookPictureStorage.java
``` java
package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.util.FileUtil;

/**
 * To create a display picture resource folder
 */
public class AddressBookPictureStorage {

    private String filePath;

    public AddressBookPictureStorage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns the file path of the pictures folder.
     */
    public String getAddressBookPicturePath() {
        return filePath;
    }

    /**
     * Creates a new folder for pictures storage
     */
    public void createPictureStorageFolder() throws IOException {
        requireNonNull(filePath);

        File file  = new File(filePath);
        FileUtil.createIfMissing(file);

    }
}
```
###### \java\seedu\address\storage\DisplayPictureStorage.java
``` java
package seedu.address.storage;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents a storage for Display Picture.
 */
public interface DisplayPictureStorage {

    void readImageFromDevice(String imagePath, int newPath) throws IOException;

    void saveImageInDirectory(BufferedImage image, String uniquePath) throws IOException;

}
```
###### \java\seedu\address\storage\ImageDisplayPictureStorage.java
``` java
package seedu.address.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_IMAGE;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import seedu.address.logic.commands.DisplayPictureCommand;
import seedu.address.logic.parser.exceptions.ImageException;

/**
 * To read and store image
 */
public class ImageDisplayPictureStorage implements DisplayPictureStorage {

    public ImageDisplayPictureStorage() {
    }

    /**
     * Reads image from local device
     * @throws IOException to display imagePath is wrong
     */
    public void readImageFromDevice(String imagePath, int newPath) throws IOException {
        File fileToRead = null;
        BufferedImage image = null;
        String uniquePath = null;

        try {
            fileToRead = new File(imagePath);
            image = new BufferedImage(963, 640, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(fileToRead);

            uniquePath = Integer.toString(newPath);

            saveImageInDirectory(image, uniquePath);
        } catch (IOException ioe) {
            throw new ImageException(String.format(MESSAGE_INVALID_IMAGE,
                    DisplayPictureCommand.MESSAGE_IMAGE_PATH_FAIL));

        }
    }

    /**
     * To store image in directory
     * @throws IOException to display error message
     */
    public void saveImageInDirectory(BufferedImage image, String uniquePath) throws IOException {
        File fileToWrite = null;
        try {
            fileToWrite = new File("pictures/" + uniquePath + ".png");
            ImageIO.write(image, "png", fileToWrite);
        } catch (IOException ioe) {
            throw  new ImageException(String.format(MESSAGE_INVALID_IMAGE,
                    DisplayPictureCommand.MESSAGE_IMAGE_PATH_FAIL));
        }
    }

}
```
###### \java\seedu\address\storage\StorageManager.java
``` java

    @Override
    public void readImageFromDevice(String path, int newPath) throws IOException {
        logger.fine("Attempting to read from file: " + path);
        displayPictureStorage.readImageFromDevice(path, newPath);
    }

    @Override
    public void saveImageInDirectory(BufferedImage image, String uniquePath) throws IOException {
        logger.fine("Attempting to write to file: " + uniquePath);
        displayPictureStorage.saveImageInDirectory(image, uniquePath);
    }

    @Override
    @Subscribe
    public void handleDisplayPictureChangedEvent(DisplayPictureChangedEvent event) throws IOException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, " Image changed, saving to file"));
        try {
            readImageFromDevice(event.path, event.newPath);
            event.setRead(true);
        } catch (IOException e) {
            event.setRead(false);
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
```
###### \java\seedu\address\ui\BrowserAndRemindersPanel.java
``` java

    private void bringBrowserToFront() {
        browser.toFront();
        currentlyInFront = Node.BROWSER;
    }

    /**
     * Set's up the UI to bring browser to front and show location
     */
    private void setUpToShowLocation() {
        if (currentlyInFront == Node.REMINDERS) {
            browser.toFront();
            currentlyInFront = Node.BROWSER;
            raise(new TurnLabelsOffEvent());
        }
    }

    /**
     * Creates url from given address
     * @param address of the specified person
     */
    public String loadPersonLocation(String address) {

        String[] splitAddressByWords = address.split("\\s");

        String keywordsOfUrl = "";

        for (String word: splitAddressByWords) {
            keywordsOfUrl += word;
            keywordsOfUrl += "+";
        }

        loadPage(GOOGLE_MAPS_URL + keywordsOfUrl);
        return GOOGLE_MAPS_URL + keywordsOfUrl;
    }

    /**
     * Sets up email Url for processing Email in Browser panel
     * @param service mentioned email service
     * @param recipients formed recipients string
     * @param subject
     * @param body
     */
    private void setUpEmailUrl(String service, String recipients, String subject, String body) {
        if (service.equalsIgnoreCase(EMAIL_SERVICE_GMAIL)) {
            loadEmailUrlGmail(recipients, subject, body);
        } else if (service.equalsIgnoreCase(EMAIL_SERVICE_OUTLOOK)) {
            loadEmailUrlOutlook(recipients, subject, body);
        }
    }

    /**
     * Loads page to send email through gmail
     * @param recipients
     * @param subject
     * @param body
     */
    public void loadEmailUrlGmail(String recipients, String subject, String body) {
        try {
            Desktop.getDesktop().browse(new URI(String.format(GMAIL_EMAIL_URL, recipients, subject, body)));
        } catch (URISyntaxException urise) {
            urise.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Loads page to send email through outlook
     * @param recipients
     * @param subject
     * @param body
     */
    private void loadEmailUrlOutlook(String recipients, String subject, String body) {
        try {
            Desktop.getDesktop().browse(new URI(String.format(OUTLOOK_EMAIL_URL, recipients, subject, body)));
        } catch (URISyntaxException urise) {
            urise.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
        bringBrowserToFront();
        raise(new TurnLabelsOffEvent());
    }

    @Subscribe
    private void handlePopularContactPanelSelectionChangedEvent(PopularContactPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
        bringBrowserToFront();
        raise(new TurnLabelsOffEvent());
    }


    @Subscribe
    private void handleBrowserPanelToggleEvent(BrowserAndRemindersPanelToggleEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        toggleBrowserPanel();
    }

    @Subscribe
    private void handleShowLocationEvent(ShowLocationEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Processing Location of " + event.person.getName().fullName));
        setUpToShowLocation();
        String url = loadPersonLocation(event.person.getAddress().value);
    }

    @Subscribe
    private void handleSendingEmailEvent(SendingEmailEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Processing email through service of " + event.service.service));
        setUpEmailUrl(event.service.service, event.recipients, event.subject.subject, event.body.body);
    }

```
###### \java\seedu\address\ui\PersonCard.java
``` java

    /**
     * Assigns URL to the image depending on the path
     */
    private void assignImage(ReadOnlyPerson person) {

        if (!person.getDisplayPicture().getPath().equals("")) {

            Image image = new Image("file:" + "pictures/" + person.getDisplayPicture().getPath() + ".png",
                    IMAGE_WIDTH, IMAGE_HEIGHT, false, false);

            centerImage();
            displayPicture.setImage(image);

        }
    }

```
###### \java\seedu\address\ui\PopularContactCard.java
``` java
package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a Popular Contact
 */
public class PopularContactCard extends UiPart<Region> {

    private static final String FXML = "PopularContactCard.fxml";
    private static final Integer IMAGE_WIDTH = 70;
    private static final Integer IMAGE_HEIGHT = 70;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyPerson person;

    @FXML
    private VBox popularContactPane;
    @FXML
    private Label popularContactName;
    @FXML
    private ImageView popularContactDisplayPicture;
    @FXML
    private Label rank;

    public PopularContactCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        rank.setText("#" + displayedIndex + " ");
        bindListeners(person);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        popularContactName.textProperty().bind(Bindings.convert(person.nameProperty()));
        assignImage(person);
    }

    /**
     * Assigns URL to the image depending on the path
     */
    private void assignImage(ReadOnlyPerson person) {

        if (!person.getDisplayPicture().getPath().equals("")) {

            Image image = new Image("file:" + "pictures/" + person.getDisplayPicture().getPath() + ".png",
                    IMAGE_WIDTH, IMAGE_HEIGHT, false, false);

            centerImage();
            popularContactDisplayPicture.setImage(image);

        }
    }

    /**
     * Centre the image in ImageView
     */
    public void centerImage() {
        Image image = popularContactDisplayPicture.getImage();
        if (image != null) {
            double width;
            double height;

            double ratioX = popularContactDisplayPicture.getFitWidth() / image.getWidth();
            double ratioY = popularContactDisplayPicture.getFitHeight() / image.getHeight();

            double reducCoeff;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            width = image.getWidth() * reducCoeff;
            height = image.getHeight() * reducCoeff;

            popularContactDisplayPicture.setX((popularContactDisplayPicture.getFitWidth() - width) / 2);
            popularContactDisplayPicture.setY((popularContactDisplayPicture.getFitHeight() - height) / 2);

        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PopularContactCard card = (PopularContactCard) other;
        return rank.getText().equals(card.rank.getText())
                && person.equals(card.person);
    }
}
```
###### \java\seedu\address\ui\PopularContactPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.PopularContactChangedEvent;
import seedu.address.commons.events.ui.PopularContactPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel to display top 5 popular most frequently accessed contacts.
 */
public class PopularContactPanel extends UiPart<Region> {

    private static final String FXML = "PopularContactPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<PopularContactCard> popularContactListView;

    public PopularContactPanel(ObservableList<ReadOnlyPerson> popularContactList) {
        super(FXML);
        setConnections(popularContactList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyPerson> popularContactList) {
        ObservableList<PopularContactCard> mappedList = EasyBind.map(
                popularContactList, (person) -> new PopularContactCard(person, popularContactList.indexOf(person) + 1));
        popularContactListView.setItems(mappedList);
        popularContactListView.setCellFactory(listView -> new PopularContactPanel.PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        popularContactListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new PopularContactPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PopularContactCard}.
     */
    class PersonListViewCell extends ListCell<PopularContactCard> {

        @Override
        protected void updateItem(PopularContactCard person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(person.getRoot());
            }
        }
    }

    @Subscribe
    public void handlePopularContactChangedEvent(PopularContactChangedEvent ppce) {
        setConnections(ppce.getModel().getPopularContactList());
    }
}

```
###### \resources\view\MainWindow.fxml
``` fxml

  <StackPane fx:id="popularContactsPanelPlaceHolder" maxHeight="120.0" minHeight="120" prefHeight="120.0" styleClass="pane-with-border" VBox.vgrow="NEVER">
    <padding>
      <Insets bottom="5" left="10" right="10" top="5" />
    </padding>
   </StackPane>
```
###### \resources\view\PopularContactCard.fxml
``` fxml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>

<VBox id="popularContactPane" fx:id="popularContactPane" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="94.0" prefWidth="200.0" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <ImageView fx:id="popularContactDisplayPicture" fitHeight="76.0" fitWidth="70.0" pickOnBounds="true" preserveRatio="true">
         <VBox.margin>
            <Insets left="70.0" />
         </VBox.margin>
         <image>
            <Image url="@../images/defaulddp.png" />
         </image>
      </ImageView>
      <HBox prefHeight="100.0" prefWidth="200.0">
         <children>
            <Label fx:id="rank" prefHeight="15.0" prefWidth="25.0"  styleClass="cell_small_label" />
            <Label fx:id="popularContactName" styleClass="cell_small_label" text="\$name" textAlignment="CENTER">
               <padding>
                  <Insets left="10.0" />
               </padding>
            </Label>
         </children>
         <VBox.margin>
            <Insets />
         </VBox.margin>
         <padding>
            <Insets left="50.0" />
         </padding>
      </HBox>
   </children>
</VBox>
```
###### \resources\view\PopularContactPanel.fxml
``` fxml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="popularContactListView" orientation="HORIZONTAL">
      <VBox.margin>
         <Insets left="130.0" right="130.0" />
      </VBox.margin></ListView>
</VBox>
```
