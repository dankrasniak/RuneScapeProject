package runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.multiLayeredPerceptron;

import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.teachingPolicy.TeachingPolicy;
import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils.Vector;

import java.util.ArrayList;

public class MLPerceptronImpl implements MLPerceptron {

    public MLPerceptronImpl(final int[] perceptronSize, final CellType[] cellType) {
        Build(perceptronSize, cellType);
    }

    private void Build(final int[] perceptronSize, final CellType[] cellType) {
        final int ARRAY_SIZE_M_1 = perceptronSize.length - 1;

        for (int i = 0; i < ARRAY_SIZE_M_1; i++) {
            layers.add(new Layer(perceptronSize[i] + 1, perceptronSize[i+1], cellType[i]));
        }
    }

    public void BackPropagate(final Vector error) {
        Vector tmp = error;

        for (int i = layers.size()-1; i > -1; i--)
            tmp = layers.get(i).Backward(tmp);
    }

    public Vector Approximate(final Vector input) {
        double[] tmp = new double[input.Size()];
        for (int i = 0; i < input.Size(); i++)
            tmp[i] = (input.Get(i) - 0.5)*2.0;

        Vector passedValue = new Vector(tmp);

        for (Layer layer : layers)
            passedValue = layer.Forward(passedValue);

        return passedValue;
    }

    public void ApplyNewWeights(final TeachingPolicy teachingPolicy) {
        layers.forEach(x -> x.ApplyNewWeights(teachingPolicy));
    }

    private ArrayList<Layer> layers = new ArrayList<>();
}
