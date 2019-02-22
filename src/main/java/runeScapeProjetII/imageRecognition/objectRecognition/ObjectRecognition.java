package runeScapeProjetII.imageRecognition.objectRecognition;

import lombok.var;
import runeScapeProjetII.imageRecognition.RegionOfInterest;
import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.multiLayeredPerceptron.CellType;
import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.multiLayeredPerceptron.MLPerceptron;
import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.multiLayeredPerceptron.MLPerceptronImpl;
import runeScapeProjetII.utils.Types;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.multiLayeredPerceptron.CellType.ATAN;
import static runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.multiLayeredPerceptron.CellType.LINEAR;

public class ObjectRecognition {

    ArrayList<MLPerceptron> perceptronsArray;
    double acceptanceLevel;

    public ObjectRecognition(int pixelCount, double acceptanceLevel) {

        this.acceptanceLevel = acceptanceLevel;

        // Prepare the neural network
        int[] perceptronSize = new int[] {pixelCount, 20, 20, 1};
        CellType[] cellTypes = new CellType[] {ATAN, ATAN, LINEAR};

        perceptronsArray = new ArrayList<>();
        for (var ignored : Types.values()) {
            perceptronsArray.add(new MLPerceptronImpl(perceptronSize, cellTypes));
        }

        // Pretraining
        throw new NotImplementedException();

    }

//    public boolean contains(RegionOfInterest regionOfInterest) {
//        return perceptron.Approximate(regionOfInterest).Get(0) >= acceptanceLevel;
//    }

    public void recognize(List<RegionOfInterest> regionsOfInterest) {

        Iterator<RegionOfInterest> iter = regionsOfInterest.iterator();

        while (iter.hasNext()) {
            boolean toDeletion = true;

            RegionOfInterest current = iter.next();
            for (Types type : Types.values()) {
                if (current.iAmA.get(type) > acceptanceLevel) {
                    toDeletion = false;
                    current.iAmA.replace(type, perceptronsArray
                            .get(type.ordinal()).Approximate(current.imageContainer.getVector()).Get(0));
                }

            }

            if (toDeletion)
                iter.remove();
            else
                current.decideType();
        }
    }
}
