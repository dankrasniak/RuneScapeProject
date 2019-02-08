package runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.teachingPolicy;

import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils.Matrix;

public interface TeachingPolicy {

    Matrix Execute(final Matrix weights, final Matrix newWeights, final Matrix oldWeights);

}
