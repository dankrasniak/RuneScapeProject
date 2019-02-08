package runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.multiLayeredPerceptron;

import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.teachingPolicy.TeachingPolicy;
import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils.Vector;

public interface MLPerceptron {

    void BackPropagate(final Vector error);

    Vector Approximate(final Vector input);

    void ApplyNewWeights(final TeachingPolicy teachingPolicy);
}
