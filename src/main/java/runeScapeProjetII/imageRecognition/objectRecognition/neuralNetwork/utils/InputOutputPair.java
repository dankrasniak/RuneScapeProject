package runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils;

public class InputOutputPair {

    public InputOutputPair(final Vector input, final Vector output) {
        this.input = input;
        this.output = output;
    }

    public Vector GetInput() {
        return input;
    }

    public Vector GetOutput() {
        return output;
    }

    private Vector input;
    private Vector output;
}
