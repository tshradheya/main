package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BrowserAndRemindersPanelToggleEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ShowLocationEvent;
import seedu.address.commons.events.ui.TurnLabelsOffEvent;
import seedu.address.commons.events.ui.TurnLabelsOnEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserAndRemindersPanel extends UiPart<Region> {

    /**
     * An Enumeration to differentiate between the child nodes and to keep track of which is
     * in front.
     */
    private enum Node {
        BROWSER, REMINDERS
    }

    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";
    public static final String GOOGLE_MAPS_URL = "https://www.google.com.sg/maps/place/";

    private static final String FXML = "BrowserAndRemindersPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private BirthdayListPanel birthdayListPanel;
    private Node currentlyInFront = Node.REMINDERS;

    @FXML
    private WebView browser;

    @FXML
    private StackPane birthdayList;

    public BrowserAndRemindersPanel(ObservableList<ReadOnlyPerson> birthdayPanelFilteredPersonList) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();

        birthdayListPanel = new BirthdayListPanel(birthdayPanelFilteredPersonList);

        //birthdayListPanel should be displayed first so no need to shift it to the back.
        birthdayList.getChildren().add(birthdayListPanel.getRoot());
        registerAsAnEventHandler(this);
    }

    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_SEARCH_URL_PREFIX + person.getName().fullName.replaceAll(" ", "+")
                + GOOGLE_SEARCH_URL_SUFFIX);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    /**
     * Check which child is currently at the front, and do the appropriate toggling between the children nodes.
     */
    private void toggleBrowserPanel() {
        switch(currentlyInFront) {
        case BROWSER:
            birthdayList.toFront();
            currentlyInFront = Node.REMINDERS;
            raise(new TurnLabelsOnEvent());
            break;
        case REMINDERS:
            browser.toFront();
            currentlyInFront = Node.BROWSER;
            raise(new TurnLabelsOffEvent());
            break;
        default:
            throw new AssertionError("It should not be possible to land here");
        }
    }

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

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
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
}
