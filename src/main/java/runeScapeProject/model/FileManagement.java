package runeScapeProject.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileManagement {

    // region IMAGE COUNTERS
    private static int positiveCount = 0;
    private static int negativeCount = 0;
    // endregion

    public static void save(BufferedImage bufferedImage, boolean positiveImage) {
        // TODO Analyze the existing number of files and update the counters
        File outputFile = new File(
                "src/main/resources/" + (positiveImage ?
                        "positive/image" + positiveCount++ + ".bmp" :
                        "negative/image" + negativeCount++ + ".bmp"));
        try {
            ImageIO.write(bufferedImage, "bmp", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
