# tshradheya
###### \java\guitests\guihandles\PopularContactCardHandle.java
``` java
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Provides a handle to a person card in the Popular Contact list panel.
 */
public class PopularContactCardHandle extends NodeHandle<Node> {

    private static final String RANK_FEILD_ID = "#rank";
    private static final String NAME_FIELD_ID = "#popularContactName";
    private static final String DISPLAY_PICTURE_FIELD_ID = "#popularContactDisplayPicture";

    private final Label idLabel;
    private final Label nameLabel;
    private final ImageView displayPictureImageView;

    public PopularContactCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(RANK_FEILD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.displayPictureImageView = getChildNode(DISPLAY_PICTURE_FIELD_ID);

    }

    public String getRank() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public Image getDisplayPictureImageView() {
        return displayPictureImageView.getImage();
    }
}
```
###### \java\guitests\guihandles\PopularContactsPanelHandle.java
``` java
package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.PopularContactCard;

/**
 * Provides a handle for {@code PopularContactListPanel} containing the list of {@code PopularContactCard}
 */
public class PopularContactsPanelHandle extends NodeHandle<ListView<PopularContactCard>> {

    public static final String PERSON_LIST_VIEW_ID = "#popularContactListView";

    private Optional<PopularContactCard> lastRememberedSelectedPopularContactCard;

    public PopularContactsPanelHandle(ListView<PopularContactCard> personListPanelNode) {
        super(personListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PersonCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public PopularContactCardHandle getHandleToSelectedCard() {
        List<PopularContactCard> personList = getRootNode().getSelectionModel().getSelectedItems();

        if (personList.size() != 1) {
            throw new AssertionError("Person list size expected 1.");
        }

        return new PopularContactCardHandle(personList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<PopularContactCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the person.
     */
    public void navigateToCard(ReadOnlyPerson person) {
        List<PopularContactCard> cards = getRootNode().getItems();
        Optional<PopularContactCard> matchingCard = cards.stream().filter(card -> card.person.equals(person)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Person does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the person card handle of a person associated with the {@code index} in the list.
     */
    public PopularContactCardHandle getPopularContactCardHandle(int index) {
        return getPopularContactCardHandle(getRootNode().getItems().get(index).person);
    }

    /**
     * Returns the {@code PersonCardHandle} of the specified {@code person} in the list.
     */
    public PopularContactCardHandle getPopularContactCardHandle(ReadOnlyPerson person) {
        Optional<PopularContactCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.person.equals(person))
                .map(card -> new PopularContactCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Person does not exist."));
    }

    /**
     * Selects the {@code PersonCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code PersonCard} in the list.
     */
    public void rememberSelectedPersonCard() {
        List<PopularContactCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPopularContactCard = Optional.empty();
        } else {
            lastRememberedSelectedPopularContactCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PersonCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
     */
    public boolean isSelectedPersonCardChanged() {
        List<PopularContactCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPopularContactCard.isPresent();
        } else {
            return !lastRememberedSelectedPopularContactCard.isPresent()
                    || !lastRememberedSelectedPopularContactCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java

        @Override
        public boolean addDisplayPicture(String path, int newPath) throws IOException {
            fail("This method should not be called");
            return false;
        }
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java

        @Override
        public void updateFilteredPersonListForViewTag(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void increaseCounterByOneForATag(List<ReadOnlyPerson> filteredPersonList) {
            fail("This method should not be called");
        }

        @Override
        public ReadOnlyPerson increaseCounterByOne(ReadOnlyPerson person) {
            fail("This method should not be called");
            return person;
        }

        @Override
        public ObservableList<ReadOnlyPerson> getPopularContactList() {
            fail("This method should not be called");
            return new ImmutableObservableList<>();
        }

        @Override
        public void refreshWithPopulatingAddressBook() {
            fail("This method should not be called");
        }

        @Override
        public void updatePopularContactList() {
            fail("This method should not be called");
        }

        @Override
        public void getOnlyTopFiveMaximum() {
            fail("This method should not be called");
        }

        @Override
        public void updatePersonsPopularityCounterByOne(ReadOnlyPerson person) throws DuplicatePersonException,
                PersonNotFoundException {
            fail("This method should not be called");
        }
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java

        @Override
        public void showLocation(ReadOnlyPerson person) {
            fail("This method should not be called");
        }

        @Override
        public void processEmailEvent(String recipients, Subject subject, Body body, Service service) {
            fail("This method should not be called");
        }

        @Override
        public String createEmailRecipients(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called");
            return "";
        }
```
###### \java\seedu\address\logic\commands\DisplayPictureCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DISPLAY_PICTURE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DISPLAY_PICTURE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISPLAYPIC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalReminders.getUniqueTypicalReminders;

import java.io.File;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.DisplayPicture;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DisplayPictureCommandTest {

    public static final String INDEX_FIRST_PERSON_EMAIL = "alice@example.com";

    public static final  String DISPLAY_PICTURE_ALICE_PATH =
            new File("./src/test/resources/pictures/" + VALID_DISPLAYPIC_ALICE)
                    .getAbsolutePath();

    private static final String INVALID_PATH = " . /?no/2 t hing";
    private Model model = new ModelManager(getTypicalAddressBook(), getUniqueTypicalReminders(), new UserPrefs());

    @Test
    public void execute_setDisplayPicture_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withDisplayPicture(Integer.toString(INDEX_FIRST_PERSON_EMAIL.hashCode())).build();

        DisplayPictureCommand displayPictureCommand = prepareCommand(INDEX_FIRST_PERSON,
                DISPLAY_PICTURE_ALICE_PATH);

        String expectedMessage = String.format(DisplayPictureCommand.MESSAGE_ADD_DISPLAYPICTURE_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                getUniqueTypicalReminders(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(displayPictureCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_setDisplayPicture_successWithNoPath() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withDisplayPicture("").build();

        DisplayPictureCommand displayPictureCommand = prepareCommand(INDEX_FIRST_PERSON,
                "");

        String expectedMessage = String.format(DisplayPictureCommand.MESSAGE_DELETE_DISPLAYPICTURE_SUCCESS,
                editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                getUniqueTypicalReminders(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(displayPictureCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DisplayPictureCommand displayPictureCommand = prepareCommand(outOfBoundIndex, DISPLAY_PICTURE_ALICE_PATH);

        assertCommandFailure(displayPictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still within bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DisplayPictureCommand displayPictureCommand = prepareCommand(outOfBoundIndex, DISPLAY_PICTURE_ALICE_PATH);

        assertCommandFailure(displayPictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        final DisplayPictureCommand standardCommand =
                new DisplayPictureCommand(INDEX_FIRST_PERSON, DISPLAY_PICTURE_AMY);
        final DisplayPictureCommand commandWithSameValues =
                new DisplayPictureCommand(INDEX_FIRST_PERSON, DISPLAY_PICTURE_AMY);
        final DisplayPictureCommand commandWithDifferentIndex =
                new DisplayPictureCommand(INDEX_SECOND_PERSON, DISPLAY_PICTURE_AMY);
        final DisplayPictureCommand commandWithDifferentDisplayPicture =
                new DisplayPictureCommand(INDEX_FIRST_PERSON, DISPLAY_PICTURE_BOB);
        final DisplayPictureCommand commandWithDifferentValues =
                new DisplayPictureCommand(INDEX_SECOND_PERSON, DISPLAY_PICTURE_BOB);

        // same object -> Returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different object, same type with same values -> return true
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same type but different index -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

        // same type but different nickname -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentDisplayPicture));

        // same type but different index and nickname -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentValues));
    }

    /**
     * Returns an {@code DisplayPictureCommand} with parameters {@code index} and {@code displaypic}
     */
    private DisplayPictureCommand prepareCommand(Index index, String displaypic) {
        DisplayPictureCommand displayPictureCommand = new DisplayPictureCommand(index, new DisplayPicture(displaypic));
        displayPictureCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return displayPictureCommand;
    }
}
```
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java
package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;
import seedu.address.model.person.PersonContainsTagPredicate;


public class EmailCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void processEmailTestFail() throws ParseException {
        thrown.expect(ParseException.class);
        EmailCommand command = new EmailCommand(new PersonContainsTagPredicate("friends"), new Subject("hello"),
                new Body("hi"), new Service("gmail"));
        command.processEmail("");
    }

    @Test
    public void checkServiceValid_throwsException() throws ParseException {
        thrown.expect(ParseException.class);
        EmailCommand command = new EmailCommand(new PersonContainsTagPredicate("friends"), new Subject("hello"),
                new Body("hi"), new Service("gmail"));
        command.checkServiceValid(new Service("notPresent"));
    }

    @Test
    public void executeParseExceptionThrown() {
        EmailCommand command = new EmailCommand(new PersonContainsTagPredicate("friend"), new Subject("hello"),
                new Body("hi"), new Service("non existing"));
        CommandResult result = command.execute();
        assertEquals(result.feedbackToUser, EmailCommand.MESSAGE_NOT_SENT);
    }

    @Test
    public void equals() {
        EmailCommand command = new EmailCommand(new PersonContainsTagPredicate("friends"), new Subject("hello"),
                new Body("hi"), new Service("gmail"));
        EmailCommand sameCommand = new EmailCommand(new PersonContainsTagPredicate("friends"), new Subject("hello"),
                new Body("hi"), new Service("gmail"));
        EmailCommand differentCommand = new EmailCommand(new PersonContainsTagPredicate("hmm"), new Subject("hi"),
                new Body("yo"), new Service("outlook"));

        assertTrue(command.equals(sameCommand));

        assertTrue(command.equals(command));

        assertFalse(command == null);

        assertFalse(sameCommand.equals(differentCommand));
    }


}
```
###### \java\seedu\address\logic\commands\LocationCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalReminders.getUniqueTypicalReminders;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowLocationEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class LocationCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), getUniqueTypicalReminders(), new UserPrefs());

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LocationCommand locationCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(locationCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still within bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        LocationCommand locationCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(locationCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final LocationCommand standardCommand = new LocationCommand(INDEX_FIRST_PERSON);
        final LocationCommand commandWithSameIndex = new LocationCommand(INDEX_FIRST_PERSON);
        final LocationCommand commandWithDifferentIndex = new LocationCommand(INDEX_SECOND_PERSON);


        // same object -> Returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different object, same type with same values -> return true
        assertTrue(standardCommand.equals(commandWithSameIndex));

        // same type but different index -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void checkIfEventCollected() throws CommandException {
        LocationCommand locationCommand = prepareCommand(INDEX_FIRST_PERSON);
        locationCommand.execute();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowLocationEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 3);
    }

    /**
     * Executes a {@code LocationCommand} with the given {@code index}, and checks that {@code ShowLocationEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        LocationCommand locationCommand = prepareCommand(index);

        try {
            CommandResult commandResult = locationCommand.execute();
            assertEquals(String.format(LocationCommand.MESSAGE_FIND_LOCATION_SUCCESS,
                    model.getFilteredPersonList().get(index.getZeroBased()).getName().fullName,
                    model.getFilteredPersonList().get(index.getZeroBased()).getAddress().value),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ShowLocationEvent lastEvent = (ShowLocationEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(model.getFilteredPersonList().get(index.getZeroBased()), lastEvent.person);
    }

    /**
     * Returns an {@code LocationCommand} with parameters {@code index}}
     */
    private LocationCommand prepareCommand(Index index) {
        LocationCommand locationCommand = new LocationCommand(index);
        locationCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return locationCommand;
    }
}
```
###### \java\seedu\address\logic\commands\ViewTagCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalReminders.getUniqueTypicalReminders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonContainsTagPredicate;
import seedu.address.model.person.ReadOnlyPerson;

public class ViewTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getUniqueTypicalReminders(), new UserPrefs());

    @Test
    public void equals() {
        PersonContainsTagPredicate firstPredicate =
                new PersonContainsTagPredicate("foo");
        PersonContainsTagPredicate secondPredicate =
                new PersonContainsTagPredicate("bar");

        ViewTagCommand viewTagFirstCommand = new ViewTagCommand(firstPredicate);
        ViewTagCommand viewTagSecondCommand = new ViewTagCommand(secondPredicate);

        // same object -> returns true
        assertTrue(viewTagFirstCommand.equals(viewTagFirstCommand));

        // same values -> returns true
        ViewTagCommand viewTagFirstCommandCopy = new ViewTagCommand(firstPredicate);
        assertTrue(viewTagFirstCommand.equals(viewTagFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewTagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewTagFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewTagFirstCommand.equals(viewTagSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        ViewTagCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_oneKeyword_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        ViewTagCommand command = prepareCommand("friend");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }

    @Test
    public void execute_oneKeyword_singlePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        ViewTagCommand command = prepareCommand("enemy");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(ELLE));
    }

    /**
     * Parses {@code keyword} into a {@code ViewTagCommand}.
     */
    private ViewTagCommand prepareCommand(String keyword) {
        ViewTagCommand command =
                new ViewTagCommand(new PersonContainsTagPredicate(keyword));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ViewTagCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

}
```
###### \java\seedu\address\model\email\BodyTest.java
``` java
package seedu.address.model.email;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BodyTest {

    private Body body = new Body("gmail");
    private Body sameBody = new Body("gmail");
    private Body anotherBody = new Body("outlook");

    @Test
    public void service_equals() {
        assertTrue(body.equals(sameBody));

        assertFalse(body == null);

        assertTrue(body.equals(body));

        assertFalse(body.equals(anotherBody));
    }
}
```
###### \java\seedu\address\model\email\ServiceTest.java
``` java
package seedu.address.model.email;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ServiceTest {
    private Service service = new Service("gmail");
    private Service sameService = new Service("gmail");
    private Service anotherService = new Service("outlook");

    @Test
    public void service_equals() {
        assertTrue(service.equals(sameService));

        assertFalse(service == null);

        assertTrue(service.equals(service));

        assertFalse(service.equals(anotherService));
    }
}
```
###### \java\seedu\address\model\email\SubjectTest.java
``` java
package seedu.address.model.email;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SubjectTest {

    private Subject subject = new Subject("message");
    private Subject sameSubject = new Subject("message");
    private Subject anotherSubject = new Subject("different message");

    @Test
    public void service_equals() {
        assertTrue(subject.equals(sameSubject));

        assertFalse(subject == null);

        assertTrue(subject.equals(subject));

        assertFalse(subject.equals(anotherSubject));
    }
}
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java

    @Test
    public void addDisplayPicture_eventRaised() throws IOException {
        ModelManager modelManager = new ModelManager();
        modelManager.addDisplayPicture(TEST_DATA_FOLDER + "1137944384.png", 1137944384);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DisplayPictureChangedEvent);
    }
```
###### \java\seedu\address\model\person\DisplayPictureTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISPLAYPIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISPLAYPIC_BOB;

import org.junit.Test;

public class DisplayPictureTest {
    @Test
    public void equals() {
        DisplayPicture standardDisplayPicture = new DisplayPicture(VALID_DISPLAYPIC_AMY);
        DisplayPicture sameDisplayPicture = new DisplayPicture(VALID_DISPLAYPIC_AMY);
        DisplayPicture differentDisplayPicture = new DisplayPicture(VALID_DISPLAYPIC_BOB);

        // same object -> returns true
        assertTrue(standardDisplayPicture.equals(standardDisplayPicture));

        // null -> returns false
        assertFalse(standardDisplayPicture == null);

        // different type -> returns false
        assertFalse(standardDisplayPicture.equals("String"));

        // different nickname -> returns false
        assertFalse(sameDisplayPicture.equals(differentDisplayPicture));

        // same nickname -> returns true
        assertTrue(standardDisplayPicture.equals(sameDisplayPicture));
    }
}
```
###### \java\seedu\address\model\person\EmailTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EmailTest {

    @Test
    public void isValidEmail() {
        // blank email
        assertFalse(Email.isValidEmail("")); // empty string
        assertFalse(Email.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Email.isValidEmail("@example.com")); // missing local part
        assertFalse(Email.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(Email.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Email.isValidEmail("-@example.com")); // invalid local part
        assertFalse(Email.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Email.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(Email.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Email.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(Email.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Email.isValidEmail("peterjack@example@com")); // '@' symbol in domain name

        // valid email
        assertTrue(Email.isValidEmail("PeterJack_1190@example.com"));
        assertTrue(Email.isValidEmail("a@b"));  // minimal
        assertTrue(Email.isValidEmail("test@localhost"));   // alphabets only
        assertTrue(Email.isValidEmail("123@145"));  // numeric local part and domain name
        assertTrue(Email.isValidEmail("a1@example1.com"));  // mixture of alphanumeric and dot characters
        assertTrue(Email.isValidEmail("_user_@_e_x_a_m_p_l_e_.com_"));    // underscores
        assertTrue(Email.isValidEmail("peter_jack@very_very_very_long_example.com"));   // long domain name
        assertTrue(Email.isValidEmail("if.you.dream.it_you.can.do.it@example.com"));    // long local part
    }
}
```
###### \java\seedu\address\model\person\PersonContainsTagPredicateTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsTagPredicateTest {

    @Test
    public void equals() {

        String firstPredicateKeyword = "foo";
        String secondPredicateKeyword = "bar";

        PersonContainsTagPredicate firstPredicate = new PersonContainsTagPredicate(firstPredicateKeyword);
        PersonContainsTagPredicate secondPredicate = new PersonContainsTagPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsTagPredicate firstPredicateCopy = new PersonContainsTagPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personContainsTag_returnsTrue() {

        // multiple people having 'friend' tag
        PersonContainsTagPredicate predicate = new PersonContainsTagPredicate("friend");
        assertTrue(predicate.test(new PersonBuilder().withTags("friend").build()));

        // one person having the tag
        predicate = new PersonContainsTagPredicate("enemy");
        assertTrue(predicate.test(new PersonBuilder().withTags("enemy").build()));

        // mixed-case tag
        predicate = new PersonContainsTagPredicate("coLLeagUe");
        assertTrue(predicate.test(new PersonBuilder().withTags("colleague").build()));

        // no one with the tag
        predicate = new PersonContainsTagPredicate("cs2103");
        assertTrue(predicate.test(new PersonBuilder().withTags("cs2103").build()));

    }

    @Test
    public void test_personDoesNotContainTag_returnsFalse() {

        // no keywords
        PersonContainsTagPredicate predicate = new PersonContainsTagPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withTags("enemy").build()));

        // not matching
        predicate = new PersonContainsTagPredicate("friend");
        assertFalse(predicate.test(new PersonBuilder().withTags("enemy").build()));

        // people associated with empty tag
        predicate = new PersonContainsTagPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withTags("friend").build()));

        // keyword matches name but not tag
        predicate = new PersonContainsTagPredicate("Alice");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

    }
}
```
###### \java\seedu\address\model\person\PopularityCounterTest.java
``` java
package seedu.address.model.person;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;

import org.junit.Test;

public class PopularityCounterTest {

    @Test
    public void popularityCounterEquals() {

        PopularityCounter popularityCounter = new PopularityCounter();
        PopularityCounter samePopularityCounter = new PopularityCounter();

        assertEquals(popularityCounter, samePopularityCounter);

        samePopularityCounter.increasePopularityCounter();

        assertNotSame(popularityCounter, samePopularityCounter);
    }
}
```
###### \java\seedu\address\storage\AddressBookPictureStorageTest.java
``` java
package seedu.address.storage;

import static junit.framework.TestCase.assertEquals;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AddressBookPictureStorageTest {

    private static final String PATH = "/pictures/default.png";
    private static final String INVALID_PATH = ".2? 2./";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createTest() {
        AddressBookPictureStorage addressBookPictureStorage = new AddressBookPictureStorage(PATH);
        assertEquals(addressBookPictureStorage.getAddressBookPicturePath(), PATH);
    }

    /*@Test
    public void createPicturesPath_throwsIoException() throws IOException {
        thrown.expect(IOException.class);
        AddressBookPictureStorage addressBookPictureStorage = new AddressBookPictureStorage(INVALID_PATH);
        addressBookPictureStorage.createPictureStorageFolder();
    }
    */

    @Test
    public void saveAddressBookPicturePath_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        AddressBookPictureStorage addressBookPictureStorage = new AddressBookPictureStorage(null);
        addressBookPictureStorage.createPictureStorageFolder();
    }
}
```
###### \java\seedu\address\storage\ImageDisplayPictureStorageTest.java
``` java
package seedu.address.storage;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.parser.exceptions.ImageException;

public class ImageDisplayPictureStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/ImageDisplayPicture/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void read_notValidPath_exceptionThrown() throws Exception {

        thrown.expect(ImageException.class);
        DisplayPictureStorage displayPictureStorage = new ImageDisplayPictureStorage();
        displayPictureStorage.readImageFromDevice("34?/32 2dsd k", 232);
        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    /**
     * To ensure no exception is thrown and command happens successfully
     * @throws IOException not expected
     */
    @Test
    public void read_validPath_success() throws IOException {
        DisplayPictureStorage displayPictureStorage = new ImageDisplayPictureStorage();
        displayPictureStorage.readImageFromDevice(TEST_DATA_FOLDER + "1137944384.png", 1137944384);
    }

}
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java

    @Test
    public void handleDisplayPictureChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorage("dummy"),
                new XmlRemindersStorageExceptionThrowingStub("dummy"),
                new JsonUserPrefsStorage("dummy"), new ImageDisplayPictureStorage());
        storage.handleDisplayPictureChangedEvent(new DisplayPictureChangedEvent("dummy", 123));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }
```
###### \java\seedu\address\testutil\EmailBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.logic.commands.EmailCommand;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;
import seedu.address.model.person.PersonContainsTagPredicate;

/**
 * A utility class to help with building Email content
 */
public class EmailBuilder {

    public static final String DEFAULT_SERVICE = "gmail";
    public static final String DEFAULT_TAG = "friends";
    public static final String DEFAULT_SUBJECT = "hello";
    public static final String DEFAULT_BODY = "how are you?";

    private EmailCommand emailCommand;

    public EmailBuilder() {
        this.emailCommand = new EmailCommand(new PersonContainsTagPredicate(DEFAULT_TAG),
                new Subject(DEFAULT_SUBJECT), new Body(DEFAULT_BODY), new Service(DEFAULT_SERVICE));
    }

    public EmailBuilder(EmailCommand toCopy) {
        emailCommand = toCopy;
    }

    /** Assigns a service to email command */
    public EmailBuilder withService(Service service) {
        emailCommand.setService(service);
        return this;
    }

    /** Assigns a subject to email command */
    public EmailBuilder withSubject(Subject subject) {
        emailCommand.setSubject(subject);
        return this;
    }

    /** Assigns a body to email command */
    public EmailBuilder withBody(Body body) {
        emailCommand.setBody(body);
        return this;
    }

    /** Assigns a predicate to email command to represent recipients */
    public EmailBuilder withPredicate(PersonContainsTagPredicate personContainsTagPredicate) {
        emailCommand.setPredicate(personContainsTagPredicate);
        return this;
    }
    public EmailCommand build() {
        return this.emailCommand;
    }
}
```
###### \java\seedu\address\ui\PersonCardTest.java
``` java

    @Test
    public void displayImage() {
        Person personWithDisplayPicture = new PersonBuilder().withDisplayPicture("1137944384").build();
        PersonCard personCard = new PersonCard(personWithDisplayPicture, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithDisplayPicture, 1);

        // changes made to Person reflects on card
        guiRobot.interact(() -> {
            personWithDisplayPicture.setName(ALICE.getName());
            personWithDisplayPicture.setAddress(ALICE.getAddress());
            personWithDisplayPicture.setEmail(ALICE.getEmail());
            personWithDisplayPicture.setPhone(ALICE.getPhone());
            personWithDisplayPicture.setNickname(ALICE.getNickname());
            personWithDisplayPicture.setDisplayPicture(new DisplayPicture("1137944384"));
            personWithDisplayPicture.setTags(ALICE.getTags());
        });
    }
```
###### \java\seedu\address\ui\PopularContactCardTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPopularPerson;

import org.junit.Test;

import guitests.guihandles.PopularContactCardHandle;
import seedu.address.model.person.DisplayPicture;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class PopularContactCardTest extends GuiUnitTest {

    @Test
    public void displayImage() {
        Person personWithDisplayPicture = new PersonBuilder().withDisplayPicture("1137944384").build();
        PopularContactCard popularContactCard = new PopularContactCard(personWithDisplayPicture, 1);
        uiPartRule.setUiPart(popularContactCard);
        assertCardDisplay(popularContactCard, personWithDisplayPicture, 1);

        // changes made to Person reflects on card
        guiRobot.interact(() -> {
            personWithDisplayPicture.setName(ALICE.getName());
            personWithDisplayPicture.setAddress(ALICE.getAddress());
            personWithDisplayPicture.setEmail(ALICE.getEmail());
            personWithDisplayPicture.setPhone(ALICE.getPhone());
            personWithDisplayPicture.setNickname(ALICE.getNickname());
            personWithDisplayPicture.setDisplayPicture(new DisplayPicture("1137944384"));
            personWithDisplayPicture.setTags(ALICE.getTags());
        });
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        PopularContactCard popularContactCard = new PopularContactCard(person, 0);

        // same object -> returns true
        assertTrue(popularContactCard.equals(popularContactCard));

        // null -> returns false
        assertFalse(popularContactCard.equals(null));

        // different types -> returns false
        assertFalse(popularContactCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(popularContactCard.equals(new PopularContactCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(popularContactCard.equals(new PopularContactCard(person, 1)));
    }

    /**
     * Asserts that {@code popularContactCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedRank}.
     */
    private void assertCardDisplay(PopularContactCard popularContactCard, ReadOnlyPerson expectedPerson,
                                   int expectedRank) {
        guiRobot.pauseForHuman();

        PopularContactCardHandle popularContactCardHandle = new PopularContactCardHandle(popularContactCard.getRoot());

        // verify id is displayed correctly
        assertEquals("#" + Integer.toString(expectedRank) + " ", popularContactCardHandle.getRank());

        // verify person details are displayed correctly
        assertCardDisplaysPopularPerson(expectedPerson, popularContactCardHandle);
    }
}
```
###### \java\seedu\address\ui\PopularContactListPanelTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getPopularPersons;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPopularPerson;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PopularContactCardHandle;
import guitests.guihandles.PopularContactsPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;

public class PopularContactListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getPopularPersons());

    private PopularContactsPanelHandle popularContactsPanelHandle;

    @Before
    public void setUp() {
        PopularContactPanel popularContactPanel = new PopularContactPanel(TYPICAL_PERSONS);
        uiPartRule.setUiPart(popularContactPanel);

        popularContactsPanelHandle = new PopularContactsPanelHandle(getChildNode(popularContactPanel.getRoot(),
                popularContactsPanelHandle.PERSON_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_PERSONS.size(); i++) {
            popularContactsPanelHandle.navigateToCard(TYPICAL_PERSONS.get(i));
            ReadOnlyPerson expectedPerson = TYPICAL_PERSONS.get(i);
            PopularContactCardHandle actualCard = popularContactsPanelHandle.getPopularContactCardHandle(i);

            assertCardDisplaysPopularPerson(expectedPerson, actualCard);
            assertEquals("#" + Integer.toString(i + 1) + " ", actualCard.getRank());
        }
    }

}
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertPopularCardEquals(PopularContactCardHandle expectedCard,
                                               PopularContactCardHandle actualCard) {
        assertEquals(expectedCard.getRank(), actualCard.getRank());
        assertEquals(expectedCard.getName(), actualCard.getName());
    }
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPopularPerson(ReadOnlyPerson expectedPerson,
                                                       PopularContactCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());

    }
```
###### \java\systemtests\EmailCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.EmailBuilder.DEFAULT_BODY;
import static seedu.address.testutil.EmailBuilder.DEFAULT_SERVICE;
import static seedu.address.testutil.EmailBuilder.DEFAULT_SUBJECT;
import static seedu.address.testutil.EmailBuilder.DEFAULT_TAG;

import org.junit.Test;

import seedu.address.logic.commands.EmailCommand;
import seedu.address.model.Model;

public class EmailCommandSystemTest extends AddressBookSystemTest {


    @Test
    public void sendingEmail() throws Exception {
        /** With all prefixes */
        String command  = EmailCommand.COMMAND_WORD + " s/" + DEFAULT_SERVICE + " to/" + DEFAULT_TAG + " sub/"
                + DEFAULT_SUBJECT + " body/" + DEFAULT_BODY;
        Model model = getModel();
        assertCommandSuccess(command, model, "");

        /** With optional prefixes missing*/
        command  = EmailCommand.COMMAND_WORD + " s/" + DEFAULT_SERVICE + " to/" + DEFAULT_TAG + " sub/"
                + DEFAULT_SUBJECT;
        model = getModel();
        assertCommandSuccess(command, model, "");

        /** With compulsory prefix missing*/
        command  = EmailCommand.COMMAND_WORD + " to/" + DEFAULT_TAG + " sub/"
                + DEFAULT_SUBJECT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }

    /**
     * To assert successful command
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();
    }

    /**
     * To assert failed command
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}

```
###### \java\systemtests\PopularContactsSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.LocationCommand.MESSAGE_FIND_LOCATION_SUCCESS;
import static seedu.address.logic.commands.SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.KEYWORD_TAG_ENEMY;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LocationCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ViewTagCommand;
import seedu.address.model.Model;

public class PopularContactsSystemTest extends AddressBookSystemTest {

    @Test
    public void favouriteContactsTest() {

        /* Selecting first one to increase popularity counter by one */
        String command = "   " + SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredListPopularContacts(expectedModel, ALICE, BENSON, CARL, DANIEL, ELLE);
        assertCommandSuccessForSelect(command, INDEX_FIRST_PERSON);
        assertSelectedCardChanged(INDEX_FIRST_PERSON);


        /* Executing viewtag to increase popularity counter by one for everyone in the tag */
        command = "    " + ViewTagCommand.COMMAND_WORD + " " + KEYWORD_TAG_ENEMY + "    ";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ELLE);
        ModelHelper.setFilteredListPopularContacts(expectedModel, ALICE, ELLE, BENSON, CARL, DANIEL);
        assertCommandSuccessForViewTag(command, expectedModel);

        /* Executing location to increase popularity counter by one for everyone in the tag */
        command = "    " + LocationCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + "    ";
        expectedModel = getModel();
        ModelHelper.setFilteredListPopularContacts(expectedModel, ELLE, ALICE, BENSON, CARL, DANIEL);
        assertCommandSuccessForLocation(command, INDEX_FIRST_PERSON, expectedModel);

        /* Listing all contacts */
        command = "    " + ListCommand.COMMAND_WORD;
        executeCommand(command);

        /* Executing viewtag to increase popularity counter by one for everyone in the tag */
        command = "    " + ViewTagCommand.COMMAND_WORD + " " + "owesmoney";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON);
        ModelHelper.setFilteredListPopularContacts(expectedModel, ELLE, ALICE, BENSON, CARL, DANIEL);
        assertCommandSuccessForViewTag(command, expectedModel);

    }

    /**
     * Command success for Select
     * @param command
     * @param expectedSelectedCardIndex
     */
    private void assertCommandSuccessForSelect(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
                MESSAGE_SELECT_PERSON_SUCCESS, expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getPersonListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedCardUnchanged();
        } else {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccessForViewTag(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedModel.getFilteredPersonList().size() != 0) {
            assertStatusBarUnchangedExceptSyncStatus();
        } else {
            assertStatusBarUnchanged();
        }

    }

    /**
     * Executes command and checks the state and equality of model
     * @param command
     * @param expectedSelectedCardIndex
     * @param expectedModel
     */
    private void assertCommandSuccessForLocation(String command, Index expectedSelectedCardIndex, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_FIND_LOCATION_SUCCESS,
                expectedModel.getFilteredPersonList().get(expectedSelectedCardIndex.getZeroBased()).getName(),
                expectedModel.getFilteredPersonList().get(expectedSelectedCardIndex.getZeroBased()).getAddress());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

}


```
###### \java\systemtests\ViewTagCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.KEYWORD_TAG_FRIEND;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewTagCommand;
import seedu.address.model.Model;

public class ViewTagCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void viewtag() {

        /* Case: Multiple people having one Tag, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "    " + ViewTagCommand.COMMAND_WORD + " " + KEYWORD_TAG_FRIEND + "    ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON); // Alice and Benson have tags as "friend"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: Repeat previous command with no extra spaces -> 2 persons found */
        command = ViewTagCommand.COMMAND_WORD + " " + KEYWORD_TAG_FRIEND;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: Only person having the given tag -> 1 person found */
        command = ViewTagCommand.COMMAND_WORD + " enemy";
        ModelHelper.setFilteredList(expectedModel, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: When multiple people have a tag with other tags too -> 2 persons found */
        command = ViewTagCommand.COMMAND_WORD + " relative";
        ModelHelper.setFilteredList(expectedModel, CARL, DANIEL); // Carl and Daniel have 2 tags and one is "family"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons in address book with tag after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getModel().getAddressBook().getPersonList().contains(CARL);
        command = ViewTagCommand.COMMAND_WORD + " relative";
        expectedModel = getModel();

        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is same as name but of different case -> 1 person found */
        command = ViewTagCommand.COMMAND_WORD + " rElATivE";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is substring of name -> 0 persons found */
        command = ViewTagCommand.COMMAND_WORD + " relat";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, name is substring of keyword -> 0 persons found */
        command = ViewTagCommand.COMMAND_WORD + " myrelative";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find persons for a tag which doesn't exist-> 0 persons found */
        command = ViewTagCommand.COMMAND_WORD + " school";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = ViewTagCommand.COMMAND_WORD + " " + KEYWORD_TAG_FRIEND;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "viEwTaG owesMONey";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);


    }



    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedModel.getFilteredPersonList().size() != 0) {
            assertStatusBarUnchangedExceptSyncStatus();
        } else {
            assertStatusBarUnchanged();
        }

    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
