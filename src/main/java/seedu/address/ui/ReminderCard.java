package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.reminders.Reminder;

/**
 * An UI component that displays the content, date and time of a Reminder.
 */
public class ReminderCard extends UiPart<Region> {
    private static final String FXML = "ReminderListCard.fxml";
    public final Reminder source;

    @FXML
    private HBox cardPane;
    @FXML
    private Label reminder;
    @FXML
    private Label id;
    @FXML
    private Label duedate;

    public ReminderCard(Reminder reminder, int displayedIndex) {
        super(FXML);
        source = reminder;
        id.setText(displayedIndex + ". ");
        bindListeners(reminder);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Reminder} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(Reminder source) {
        reminder.textProperty().bind(Bindings.convert(source.reminderProperty()));
        duedate.textProperty().bind(Bindings.convert(source.dueDateProperty()));
    }
}
