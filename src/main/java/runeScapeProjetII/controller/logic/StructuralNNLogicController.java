package runeScapeProjetII.controller.logic;

import runeScapeProjetII.imageRecognition.RegionOfInterest;
import runeScapeProjetII.imageRecognition.imageNormalization.Normalizator;
import runeScapeProjetII.imageRecognition.objectClassification.Classificator;
import runeScapeProjetII.imageRecognition.objectDetection.Detector;
import runeScapeProjetII.imageRecognition.objectDetection.SlidingWindow;
import runeScapeProjetII.imageRecognition.objectRecognition.ObjectRecognition;
import runeScapeProjetII.utils.ImageContainer;

import java.util.List;

public class StructuralNNLogicController implements LogicController {

    private Detector detector = new SlidingWindow();
    private Normalizator normalizator = new Normalizator();
    private Classificator classificator = new Classificator();
    private ObjectRecognition recognizer = new ObjectRecognition(30*30, 0.30);

    @Override
    public void update(Object arg) {

        // Get Regions Of Interest
        List<RegionOfInterest> regionsOfInterest = extractRegionsOfInterest((ImageContainer) arg);

        // Act upon POIs
        gameLogic(regionsOfInterest);
    }

    private void gameLogic(List<RegionOfInterest> regionsOfInterest) {
        // TODO
    }

    private List<RegionOfInterest> extractRegionsOfInterest(ImageContainer image) {
        List<RegionOfInterest> regionsOfInterest;

        // Detect Objects
        regionsOfInterest = detector.detectRegionsOfInterest(image);

        // Normalize Objects
        normalizator.normalize(regionsOfInterest);

        // Classify Objects
        classificator.classify(regionsOfInterest);

        // Recognize Objects
        recognizer.recognize(regionsOfInterest);

        return regionsOfInterest;
    }
}
