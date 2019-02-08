package runeScapeProjetII.imageRecognition.objectDetection;


import runeScapeProjetII.imageRecognition.RegionOfInterest;
import runeScapeProjetII.utils.ImageContainer;

import java.util.List;

public interface Detector {

    List<RegionOfInterest> detectRegionsOfInterest(ImageContainer imageContainer);
}
