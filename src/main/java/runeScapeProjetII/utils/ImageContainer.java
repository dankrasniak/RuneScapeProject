package runeScapeProjetII.utils;

import lombok.Getter;
import lombok.Setter;
import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils.Vector;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ImageContainer {

    @Getter
    @Setter
    private BufferedImage image;

    public Vector getVector() {
        byte[] array = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        double[] doubleArray = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            doubleArray[i] = (double)array[i] / 256;
        }
        return new Vector(doubleArray);
    }
}
