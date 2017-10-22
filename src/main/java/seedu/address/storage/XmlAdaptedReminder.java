package seedu.address.storage;

import java.time.format.DateTimeParseException;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.reminders.DueDate;
import seedu.address.model.reminders.Reminder;

/**
 * JAXB-friendly version of the Reminder.
 */
public class XmlAdaptedReminder {

    @XmlElement(required = true)
    private String reminder;
    @XmlElement(required = true)
    private String dateAndTime;

    /**
     * Constructs an XmlAdaptedReminder.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedReminder() {}

    /**
     * Converts a given Reminder into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedReminder
     */
    public XmlAdaptedReminder(Reminder source) {
        this.reminder = source.getReminder();
        this.dateAndTime = source.getDueDate().getLocalDateTime().toString();
    }
    /**
     * Converts this jaxb-friendly adapted reminder object into the model's Reminder object.
     *
     * @throws DateTimeParseException if there were any data constraints violated in the adapted reminder
     */
    public Reminder toModelType() {
        return new Reminder(this.reminder, new DueDate(dateAndTime));
    }


}
