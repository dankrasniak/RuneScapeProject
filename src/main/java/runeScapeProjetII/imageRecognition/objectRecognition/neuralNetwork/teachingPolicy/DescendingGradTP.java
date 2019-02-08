package runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.teachingPolicy;

import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils.Matrix;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class DescendingGradTP implements TeachingPolicy {
    private static Logger logger = Logger.getLogger("MyLogWeights");
    int counter = 0;

    public DescendingGradTP(final double BetaV) {
        this.BetaV = BetaV;
        try {
            FileHandler fh = new FileHandler("MyLogFileWeightsCM.log");
            logger.addHandler(fh);
            fh.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return record.getMessage() + "\n";
                }
            });
            logger.setUseParentHandlers(false);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public Matrix Execute(final Matrix weights, final Matrix newWeights, final Matrix oldWeights) {
        final int HEIGHT = weights.GetHeight();
        final int LENGTH = weights.GetLength();
        double w;

        Matrix result = new Matrix(HEIGHT, LENGTH);

        for (int h = 0; h < HEIGHT; h++) {
            for (int l = 0; l < LENGTH; l++) {
                w = weights.Get(h, l);
                result.Set(h, l, w + newWeights.Get(h, l) * BetaV);
            }
        }
        if (HEIGHT == 7 && (counter++% 50000) == 0)
            logger.info(result.Get(0, 1) + ";" + result.Get(0, 2));

        return result;
    }

    private final double BetaV;
}
