package runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.teachingPolicy;

import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils.Matrix;

public class ClassicalMomentumTP implements TeachingPolicy {
//    private static Logger logger = Logger.getLogger("MyLogWeights");
//    int counter = 0;

    public ClassicalMomentumTP(final double BetaV) {
        this.BetaV = BetaV;
        // Prepare Logger
//        try {
//            FileHandler fh = new FileHandler("MyLogFileWeightsCM.log");
//            logger.addHandler(fh);
//            fh.setFormatter(new Formatter() {
//                @Override
//                public String format(LogRecord record) {
//                    return record.getMessage() + "\n";
//                }
//            });
//            logger.setUseParentHandlers(false);
//        } catch (SecurityException | IOException e) {
//            e.printStackTrace();
//        }
    }

    public Matrix Execute(final Matrix weights, final Matrix newWeights, final Matrix Momentum) {
        final int HEIGHT = weights.GetHeight();
        final int LENGTH = weights.GetLength();
        double w;

        Matrix result = new Matrix(HEIGHT, LENGTH);

        for (int h = 0; h < HEIGHT; h++) {
            for (int l = 0; l < LENGTH; l++) {
                w = weights.Get(h, l);
                Momentum.Set(h, l, Momentum.Get(h, l) * 0.9 + 0.1 * newWeights.Get(h, l) * BetaV);
                result.Set(h, l, w + Momentum.Get(h, l) );
            }
        }

        return result;
    }

    private final double BetaV;
}
/*
for (int h = 0; h < HEIGHT; h++) {
            for (int l = 0; l < LENGTH; l++) {
                w = weights.Get(h, l);
                result.Set(h, l, w + 0.1 * newWeights.Get(h, l) * BetaV +
                        0.9 * (w - oldWeights.Get(h, l)) );
            }
        }
*/
