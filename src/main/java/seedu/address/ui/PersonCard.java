package seedu.address.ui;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static String[] colors = {"red", "blue", "green", "yellow", "pink"};
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyPerson person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label nickname;
    @FXML
    private Label birthday;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView displayPicture;


    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initTags(person);
        bindListeners(person);
    }

    /**
     * Assigns a random color to a tag if it does not exist in the HashMap
     * returns a String containing the color
     */

    private String getTagColor(String tag) {
        if (!tagColors.containsKey(tag)) {
            tagColors.put(tag, colors[random.nextInt(colors.length)]);
        }
        return tagColors.get(tag);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        nickname.textProperty().bind(Bindings.convert(person.nicknameProperty()));
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
        assignImage(person);
    }

    /**
     *  Assigns URL to the image depending on the path
     *
     */
    private void assignImage(ReadOnlyPerson person) {

        String url = "src\\main\\resources\\pictures\\" + person.getDisplayPicture().getPath() + ".jpg";

        File fileImageStored = new File(url);
        Image image = new Image(fileImageStored.toURI().toString(), 100, 100,
                false, false);
        centerImage();

        displayPicture.setImage(image);

    }

    /**
     * Centre the image in ImageView
     */
    public void centerImage() {
        Image img = displayPicture.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = displayPicture.getFitWidth() / img.getWidth();
            double ratioY = displayPicture.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            displayPicture.setX((displayPicture.getFitWidth() - w) / 2);
            displayPicture.setY((displayPicture.getFitHeight() - h) / 2);

        }
    }

    /**
     * Initialize tags for the respective person
     *
     * @param person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getTagColor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
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
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
