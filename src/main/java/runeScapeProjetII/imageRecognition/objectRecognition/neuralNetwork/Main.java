package runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork;

import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.multiLayeredPerceptron.CellType;
import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.multiLayeredPerceptron.MLPerceptron;
import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.multiLayeredPerceptron.MLPerceptronImpl;
import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.teachingPolicy.ClassicalMomentumTP;
import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils.data.DataSNB;
import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils.data.DataSNBImpl;
import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils.InputOutputPair;
import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.teachingPolicy.TeachingPolicy;
import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils.Vector;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.multiLayeredPerceptron.CellType.ATAN;
import static runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.multiLayeredPerceptron.CellType.LINEAR;


public class Main {
    private static Logger logger = Logger.getLogger("MyLog");
    private static Logger loggerError = Logger.getLogger("MyLogError");
    private static List<String> stringBuilder = new LinkedList<>();

    public static void main(String[] args) {

        //region Prepare Logger
        try {
            FileHandler fh = new FileHandler("MyLogFile.log");
            FileHandler fhInput = new FileHandler("MyLogFileInput.log");
            logger.addHandler(fh);
            loggerError.addHandler(fhInput);
            fh.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return record.getMessage() + "\n";
                }
            });
            fhInput.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return record.getMessage() + "\n";
                }
            });
            logger.setUseParentHandlers(false);
            loggerError.setUseParentHandlers(false);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        //endregion

        //region Parameters
        // Load data
        DataSNB data = new DataSNBImpl("dane.txt");
        double BetaV = 0.0125;//0.00000001;
        TeachingPolicy teachingPolicy = new ClassicalMomentumTP(-BetaV);

        // Prepare the neural network
        int[] perceptronSize = new int[] {2, 20, 20, 1};
        CellType[] cellTypes = new CellType[] {ATAN, ATAN, LINEAR};
        MLPerceptron perceptron = new MLPerceptronImpl(perceptronSize, cellTypes);

        // Divide the data on learning and test data
        data.Divide(0.5, 0.7);
        //endregion Parameters

        InputOutputPair[] teachingData = data.GetTeachingData();
        Vector error;


        // Train the neural network
        for (InputOutputPair e : teachingData) {
            error = ErrorApproximator
                    .GetError(perceptron.Approximate(e.GetInput()), e.GetOutput());
            perceptron.BackPropagate(error);
            perceptron.ApplyNewWeights(teachingPolicy);
        }

        // Test the neural network
        final InputOutputPair[] testData = data.GetTestData();
        TestNN(perceptron, testData);


        // New Tests
        while (TestNN(perceptron, testData) < 0.98) {
            TrainNN(data, teachingPolicy, perceptron);
        }

        // Log last testing session
        for (String line : stringBuilder) logger.info(line);

        System.out.println("Finished!");
    }

    private static void TrainNN(DataSNB data, TeachingPolicy teachingPolicy, MLPerceptron perceptron) {
        InputOutputPair[] teachingData;
        Vector error;
        data.ShuffleTrainingData();
        teachingData = data.GetTeachingData();
        for (InputOutputPair e : teachingData) {
            error = ErrorApproximator
                    .GetError(perceptron.Approximate(e.GetInput()), e.GetOutput());
            perceptron.BackPropagate(error);
            perceptron.ApplyNewWeights(teachingPolicy);
        }
    }

    private static double TestNN(MLPerceptron perceptron, InputOutputPair[] testData) {
        double counter = 0.0;
        Vector approximatedValue;
        double v0;
        int whichOne;

        stringBuilder.clear();

        // Test the neural network
        for (InputOutputPair e : testData) {
            approximatedValue = perceptron.Approximate(e.GetInput());

            v0 = approximatedValue.Get(0);

            if (v0 > 1.5)
                whichOne = 2;
            else if (v0 < 0.5)
                whichOne = 0;
            else
                whichOne = 1;

            if (whichOne == e.GetOutput().Get(0))
                ++counter;

            stringBuilder.add(e.GetInput().Get(0) + ";" + e.GetInput().Get(1) + ";" + whichOne);
        }
        final double errorPercentage = (counter / testData.length);
        final double tmp = calculateCurrentAverageError(errorPercentage);
        System.out.println("Error percentage = " + tmp);
        loggerError.info(Double.toString(tmp));

        return errorPercentage;
    }

    private static double calculateCurrentAverageError(final Double e) {
        double result = 0.0;

        if (sth.size() == 5)
            sth.remove(0);

        sth.add(e);
        for (Double el : sth)
            result += el;
        result /= sth.size();

        return result;
    }

    private static List<Double> sth = new LinkedList<>();
}
