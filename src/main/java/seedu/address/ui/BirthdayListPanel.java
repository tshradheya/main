package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel containing the list of persons.
 */
public class BirthdayListPanel extends UiPart<Region> {
    private static final String FXML = "BirthdayListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<BirthdayCard> birthdayListView;

    public BirthdayListPanel(ObservableList<ReadOnlyPerson> birthdayList) {
        super(FXML);
        setConnections(birthdayList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyPerson> birthdayList) {
        ObservableList<BirthdayCard> mappedList = EasyBind.map(
                birthdayList, (birthdayPerson) -> new BirthdayCard(birthdayPerson, birthdayList.indexOf(birthdayPerson) + 1));
        birthdayListView.setItems(mappedList);
        birthdayListView.setCellFactory(listView -> new PersonListViewCell());
        //setEventHandlerForSelectionChangeEvent();
    }

    /*private void setEventHandlerForSelectionChangeEvent() {
        birthdayListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                    }
                });
    }*/

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    /*private void scrollTo(int index) {
        Platform.runLater(() -> {
            birthdayListView.scrollTo(index);
            birthdayListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }*/

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<BirthdayCard> {

        @Override
        protected void updateItem(BirthdayCard person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(person.getRoot());
            }
        }
    }

}
