package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class ReminderCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String REMINDER_FIELD_ID = "#reminder";
    private static final String DUEDATE_FIELD_ID = "#dueDate";

    private final Label idLabel;
    private final Label reminderLabel;
    private final Label dueDateLabel;

    public ReminderCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.reminderLabel = getChildNode(REMINDER_FIELD_ID);
        this.dueDateLabel = getChildNode(DUEDATE_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getReminder() {
        return reminderLabel.getText();
    }

    public String getDueDate() {
        return dueDateLabel.getText();
    }

}
