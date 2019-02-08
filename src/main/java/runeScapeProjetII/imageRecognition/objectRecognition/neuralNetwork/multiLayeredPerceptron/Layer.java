package runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.multiLayeredPerceptron;

import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils.Matrix;
import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.teachingPolicy.TeachingPolicy;
import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils.Vector;

import java.util.Random;
import java.util.function.Function;

class Layer {

    Layer(final int inputSize, final int outputSize, final CellType cellType) {
        Build(inputSize, outputSize);
        switch (cellType) {
            case LINEAR:
                ActivationFunction = (final Vector vector) -> ActivationFunctionLinear(vector);
                DeactivationFunction = (final Vector vector) -> DeactivationFunctionLinear(vector);
                break;

            case ATAN:
                ActivationFunction = (final Vector vector) -> ActivationFunctionTan(vector);
                DeactivationFunction = (final Vector vector) -> DeactivationFunctionTan(vector);
                break;

            default:
                throw new IllegalArgumentException("Layer::Layer()");
        }
    }

    private void Build(final int inputSize, final int outputSize) {
        weights = new Matrix(outputSize, inputSize);
        Momentum = new Matrix(weights.GetHeight(), weights.GetLength());
        Initialize(weights);
    }

    private void Initialize(final Matrix m) {
        Random random = new Random();

        final int HEIGHT = m.GetHeight();
        final int LENGTH = m.GetLength();

        for (int x = 0; x < HEIGHT; x++)
            for (int y = 0; y < LENGTH; y++)
                m.Set(x, y, (random.nextDouble()-0.5)*2);
    }

    Vector Forward(final Vector input) {
        Input = TweakInput(input);
        Net = Matrix.MxV(weights, Input);
        return ActivationFunction.apply(Net);
    }

    Vector Backward(final Vector error) {
        final Vector dOutput = DeactivationFunction.apply(Net);
        final Vector dNet = Vector.MultiplyVVV(dOutput, error);
        dWeights = Vector.MultiplyVVM(dNet, Input);
        // dInput
        return Vector.MultiplyVMV(dNet, weights);
    }

    private Vector TweakInput(final Vector input) {
        final int INPUT_SIZE = input.Size();
        final double[] result = new double[INPUT_SIZE + 1];

        for (int i = 0; i < INPUT_SIZE; i++) {
            result[i] = input.Get(i);
        }
        result[INPUT_SIZE] = 1.0;

        return new Vector(result);
    }

    void ApplyNewWeights(final TeachingPolicy teachingPolicy) {
        weights = teachingPolicy.Execute(weights, dWeights, Momentum);
    }

    private Vector ActivationFunctionTan(final Vector values) {
        final int VECTOR_SIZE = values.Size();
        final double[] result = new double[VECTOR_SIZE];

        for (int i = 0; i < VECTOR_SIZE; i++)
            result[i] = Math.atan(values.Get(i));

        return new Vector(result);
    }

    private Vector DeactivationFunctionTan(final Vector values) {
        final int VECTOR_SIZE = values.Size();
        final double[] result = new double[VECTOR_SIZE];

        for (int i = 0; i < VECTOR_SIZE; i++)
            result[i] = 1.0 / (1.0 + values.Get(i) * values.Get(i));

        return new Vector(result);
    }

    private Vector ActivationFunctionLinear(final Vector values) {
        final int VECTOR_SIZE = values.Size();
        final double[] result = new double[VECTOR_SIZE];

        for (int i = 0; i < VECTOR_SIZE; i++)
            result[i] = values.Get(i);

        return new Vector(result);
    }

    private Vector DeactivationFunctionLinear(final Vector values) {
        final int VECTOR_SIZE = values.Size();
        final double[] result = new double[VECTOR_SIZE];

        for (int i = 0; i < VECTOR_SIZE; i++)
            result[i] = 1.0;

        return new Vector(result);
    }

    private Function<Vector, Vector> ActivationFunction;
    private Function<Vector, Vector> DeactivationFunction;
    private Matrix Momentum;
    private Matrix weights;
    private Matrix dWeights;
    private Vector Input;
    private Vector Net;
}
