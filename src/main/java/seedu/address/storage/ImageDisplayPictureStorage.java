//@@author tshradheya
package seedu.address.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_IMAGE;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.DisplayPictureCommand;
import seedu.address.logic.parser.exceptions.ImageException;

/**
 * To read and store image
 */
public class ImageDisplayPictureStorage implements DisplayPictureStorage {

    private static final Logger logger = LogsCenter.getLogger(ImageDisplayPictureStorage.class);


    public ImageDisplayPictureStorage() {
        logger.info("Constructor used to create instance of DisplayPictureStorage.class");
    }

    /**
     * Reads image from local device
     * @throws IOException to display imagePath is wrong
     */
    public void readImageFromDevice(String imagePath, int newPath) throws IOException {
        File fileToRead = null;
        BufferedImage image = null;
        String uniquePath = null;

        try {
            fileToRead = new File(imagePath);
            image = new BufferedImage(963, 640, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(fileToRead);

            uniquePath = Integer.toString(newPath);

            saveImageInDirectory(image, uniquePath);
        } catch (IOException ioe) {
            throw new ImageException(String.format(MESSAGE_INVALID_IMAGE,
                    DisplayPictureCommand.MESSAGE_IMAGE_PATH_FAIL));

        }
    }

    /**
     * To store image in directory
     * @throws IOException to display error message
     */
    public void saveImageInDirectory(BufferedImage image, String uniquePath) throws IOException {
        File fileToWrite = null;
        try {
            fileToWrite = new File("pictures/" + uniquePath + ".png");
            ImageIO.write(image, "png", fileToWrite);
        } catch (IOException ioe) {
            throw  new ImageException(String.format(MESSAGE_INVALID_IMAGE,
                    DisplayPictureCommand.MESSAGE_IMAGE_PATH_FAIL));
        }
    }

    /**
     * Deletes image from /pictures/ directory
     * @param filepath of image to be deleted
     */
    public void deleteImageFromDirectory(String  filepath) {
        if (filepath.equalsIgnoreCase("")) {
            logger.info("image not present so cannot be deleted");
        }
        File file = new File("pictures/" + filepath + ".png");

        logger.info(filepath + "deleted during exit");
        file.deleteOnExit();
    }

}
