package runeScapeProjetII.imageRecognition.objectDetection;


import runeScapeProjetII.utils.ImageContainer;

public interface Detector {


    public RegionOfInterest detectRegionsOfInterest(ImageContainer imageContainer);
}
