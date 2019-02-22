package runeScapeProjetII.imageRecognition;

import runeScapeProjetII.utils.ImageContainer;
import runeScapeProjetII.utils.Types;

import java.util.HashMap;

public class RegionOfInterest {

    public HashMap<Types,Double> iAmA;

    public Types type;

    public ImageContainer imageContainer;

    public void decideType() {
        type = Types.values()[0];
        double biggest = iAmA.get(type);

        for (int i = 1; i < Types.values().length; i++) {
            if (iAmA.get(i) > biggest) {
                biggest = iAmA.get(i);
                type = Types.values()[i];
            }
        }
    }
}
