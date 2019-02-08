package runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils.data;

import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils.InputOutputPair;
import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils.Vector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DataSNBImpl implements DataSNB {
    public DataSNBImpl(String path) {
        ReadData(path);
    }

    public InputOutputPair[] GetTeachingData() {
        return trainData;
    }

    public InputOutputPair[] GetTestData() {
        return testData;
    }

    public void Divide(final double trainToTestRatio, double testToValidationRatio) {
        final int SIZE = data.length;
        if (SIZE == 0) throw new ExceptionInInitializerError("No data to divide.");

        List<InputOutputPair> collection = Arrays.asList(data);
        Collections.shuffle(collection);

        trainData = collection.subList(0, (int)(SIZE * trainToTestRatio)).toArray(new InputOutputPair[]{});
        testData = collection.subList((int)(SIZE * trainToTestRatio), (int) (SIZE*testToValidationRatio)).toArray(new InputOutputPair[]{});
    }

    private void ReadData(String path) {
        File file = new File(path);
        String line;
        List<InputOutputPair> collection = new LinkedList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            line = bufferedReader.readLine();
            while (line != null) {
                collection.add(ConvertToIODataPair(line));
                line = bufferedReader.readLine();
            }
            data = collection.toArray(new InputOutputPair[]{});
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public void ShuffleTrainingData() {
        List<InputOutputPair> tmp = Arrays.asList(trainData);
        Collections.shuffle(tmp);
        trainData = tmp.toArray(new InputOutputPair[]{});
    }

    private InputOutputPair ConvertToIODataPair(String line) {
        currentLine = line.split(";");
        final int INPUT_ARGS_NR = currentLine.length;

        final double[] input = new double[INPUT_ARGS_NR-1];
        for (int i = 0; i < INPUT_ARGS_NR - 1; ++i)
            input[i] = Double.parseDouble(currentLine[i]);


        return new InputOutputPair(new Vector(input),
                new Vector(
                        new double[]{Double.parseDouble(currentLine[INPUT_ARGS_NR - 1])}
                ));
    }

    private static String[] currentLine;
    private InputOutputPair[] data;
    private InputOutputPair[] trainData;
    private InputOutputPair[] testData;
}
