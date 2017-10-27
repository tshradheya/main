package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.PopularContactChangedEvent;
import seedu.address.commons.events.model.RemindersChangedEvent;
import seedu.address.commons.events.ui.SendingEmailEvent;
import seedu.address.commons.events.ui.ShowLocationEvent;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;
import seedu.address.model.person.BirthdayInCurrentMonthPredicate;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.UniqueReminderList;
import seedu.address.model.reminders.exceptions.DuplicateReminderException;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private final FilteredList<ReadOnlyPerson> filteredPersonsForBirthdayListPanel;
    private final FilteredList<ReadOnlyPerson> filteredPersonsForEmail;
    private final UniqueReminderList reminderList;
    private List<ReadOnlyPerson> listOfPersonsForPopularContacts;

    private SortedList<ReadOnlyPerson> sortedfilteredPersons;
    private SortedList<ReadOnlyPerson> sortedFilteredPersonsForBirthdayListPanel;
    private SortedList<Reminder> sortedReminderList;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UniqueReminderList reminders, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, reminders, userPrefs);

        logger.fine("Initializing with address book: " + addressBook +  " and reminders " + reminders
                + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.reminderList = reminders;
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredPersonsForBirthdayListPanel = new FilteredList<>(this.addressBook.getPersonList());
        filteredPersonsForBirthdayListPanel.setPredicate(new BirthdayInCurrentMonthPredicate());
        filteredPersonsForEmail = new FilteredList<>(this.addressBook.getPersonList());
        listOfPersonsForPopularContacts = new ArrayList<>(this.addressBook.getPersonList());
        sortedfilteredPersons = new SortedList<>(filteredPersons);
        sortedFilteredPersonsForBirthdayListPanel = new SortedList<>(filteredPersonsForBirthdayListPanel,
                Comparator.comparingInt(birthday -> birthday.getBirthday().getDayOfBirthday()));
        sortedReminderList = new SortedList<>(reminderList.asObservableList(),
                Comparator.comparing(reminder -> reminder.getDueDate().getLocalDateTime()));

    }

    public ModelManager() {
        this(new AddressBook(), new UniqueReminderList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public void resetReminders(UniqueReminderList newReminders) {
        reminderList.setReminders(newReminders);
        indicateRemindersChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    /** Raises an event to indicate model has changed and favourite contacts might be updated */
    private void indicatePopularContactsChangedPossibility() {
        raise(new PopularContactChangedEvent(this));

    }
    /** Raises an event to indicate the reminders have changed */
    private void indicateRemindersChanged() {
        raise(new RemindersChangedEvent(reminderList));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteTag(Tag tagToRemove) throws DuplicatePersonException, PersonNotFoundException {
        for (int i = 0; i < addressBook.getPersonList().size(); i++) {
            ReadOnlyPerson originalPerson = addressBook.getPersonList().get(i);

            Person personWithTagRemoved = new Person(originalPerson);
            Set<Tag> updatedTags = personWithTagRemoved.getTags();
            updatedTags.remove(tagToRemove);

            addressBook.updatePerson(originalPerson, personWithTagRemoved);
        }


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
        increaseCounterByOneForEmailRecipients(filteredPersonsForEmail);

        List<String> validEmails = new ArrayList<>();
        for (ReadOnlyPerson person : filteredPersonsForEmail) {
            if (Email.isValidEmail(person.getEmail().value)) {
                validEmails.add(person.getEmail().value);
            }
        }
        return String.join(",", validEmails);
    }

    @Override
    public void increaseCounterByOneForEmailRecipients(List<ReadOnlyPerson> filteredPersonsForEmail) {

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
        if (listOfPersonsForPopularContacts.size() > 5) {
            for (int i = 5; i < listOfPersonsForPopularContacts.size(); i++) {
                listOfPersonsForPopularContacts.remove(i);
            }
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

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(sortedfilteredPersons);
    }

    @Override
    public void sortFilteredPersonList() {

        Comparator<ReadOnlyPerson> sortByName = (o1, o2) -> o1.getName().fullName.compareTo(o2.getName().fullName);
        sortedfilteredPersons.setComparator(sortByName);
        indicateAddressBookChanged();
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredPersons.setPredicate(null);
    }

    @Override
    public ObservableList<ReadOnlyPerson> getBirthdayPanelFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(sortedFilteredPersonsForBirthdayListPanel);
    }

    //=========== UniqueReminderList Accessors =================================================================

    @Override
    public ObservableList<Reminder> getSortedReminderList() {
        return sortedReminderList;
    }

    @Override
    public UniqueReminderList getUniqueReminderList() {
        return reminderList;
    }

    @Override
    public void addReminder(Reminder toAdd) throws DuplicateReminderException {
        reminderList.add(toAdd);
        indicateRemindersChanged();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredPersons.equals(other.filteredPersons)
                && reminderList.equals(other.reminderList);
    }

}
