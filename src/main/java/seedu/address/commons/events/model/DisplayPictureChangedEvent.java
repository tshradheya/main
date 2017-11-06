//@@author tshradheya
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
