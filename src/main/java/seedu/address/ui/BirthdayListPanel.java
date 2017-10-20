package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel containing the list of persons with birthday in the current month.
 */
public class BirthdayListPanel extends UiPart<Region> {
    private static final String FXML = "BirthdayListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<BirthdayReminderCard> birthdayListView;

    public BirthdayListPanel(ObservableList<ReadOnlyPerson> birthdayList) {
        super(FXML);
        setConnections(birthdayList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyPerson> birthdayList) {
        ObservableList<BirthdayReminderCard> mappedList = EasyBind.map(
                birthdayList, (birthdayPerson) -> new BirthdayReminderCard(birthdayPerson,
                        birthdayList.indexOf(birthdayPerson) + 1));
        birthdayListView.setItems(mappedList);
        birthdayListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<BirthdayReminderCard> {

        @Override
        protected void updateItem(BirthdayReminderCard person, boolean empty) {
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
