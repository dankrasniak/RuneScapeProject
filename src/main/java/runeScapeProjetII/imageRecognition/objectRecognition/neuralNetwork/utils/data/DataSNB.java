package runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils.data;


import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils.InputOutputPair;

public interface DataSNB {
    InputOutputPair[] GetTeachingData();
    InputOutputPair[] GetTestData();

    /**
     * Divides the data randomly into portion of data to train the network and a portion of
     * data to test the network.
     * @param trainToTestRatio
     * @param testToValidationRatio
     */
    void Divide(double trainToTestRatio, double testToValidationRatio);

    void ShuffleTrainingData();
}
