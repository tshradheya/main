package seedu.address.commons.events.storage;

import static java.util.UUID.randomUUID;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * To read image from specified path and store in @file resources/pictures
 */
public class ReadAndStoreImage {

    /**
     * @param path
     * @return uniquePath new path in directory
     */
    public String execute(String path) throws IOException {

        File fileToRead = null;
        BufferedImage image = null;

        File fileToWrite = null;

        String uniquePath = null;

        try {
            fileToRead = new File(path);
            image = new BufferedImage(963, 640, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(fileToRead);

            uniquePath = randomUUID().toString();

            fileToWrite = new File("src\\main\\resources\\pictures\\" + uniquePath + ".jpg");
            ImageIO.write(image, "jpg", fileToWrite);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return uniquePath;

    }


}
