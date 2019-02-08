package runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork;

import runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils.Vector;

abstract class ErrorApproximator {

    static Vector GetError(final Vector nnOutput, final Vector dOutput) {
        Vector result;
        result = new Vector(new double[] {nnOutput.Get(0) - dOutput.Get(0) });

        return result; //(new Vector(new double[] {(dOutput.Get(0) - nnOutput.Get(0))*(dOutput.Get(0) - nnOutput.Get(0))/2}));
    }
}