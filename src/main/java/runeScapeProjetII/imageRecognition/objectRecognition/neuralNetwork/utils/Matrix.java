package runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils;


public class Matrix {

    public Matrix(final int x, final int y) {
        values = new double[x][y];
    }

    public Matrix(final Matrix m) {
        final int HEIGHT = m.GetHeight(), WIDTH = m.GetLength();
        values = new double[HEIGHT][WIDTH];

        for (int i = 0; i < HEIGHT; i++) {
            System.arraycopy(m.values[i], 0, values[i], 0, WIDTH);
        }
    }

    public static Vector MxV(final Matrix m, final Vector v) {
        final int INPUT_SIZE = v.Size();
        final int OUTPUT_SIZE = m.GetHeight();
        double[] result = new double[OUTPUT_SIZE];

        for (int y = 0; y < OUTPUT_SIZE; y++)
            for (int x = 0; x < INPUT_SIZE; x++)
                result[y] += m.values[y][x] * v.Get(x);

        return new Vector(result);
    }

    public void Set(final int i, final int j, final double v) {
        values[i][j] = v;
    }

    public double Get(final int i, final int j) { return values[i][j]; }

    public int GetHeight() { return values.length; }
    public int GetLength() { return values[0].length; }
    private double[][] values;
}
