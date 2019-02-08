package runeScapeProjetII.imageRecognition.objectRecognition.neuralNetwork.utils;


public class Vector {

    public Vector(final double[] values) {
        this.values = values;
    }

    public double Get(final int index) {
        return values[index];
    }

    public void Set(final int index, final double value) { values[index] = value; }

    public int Size() { return values.length; }

    public static Vector MultiplyVVV(final Vector a, final Vector b) {
        final int VECTOR_SIZE = a.Size();
        if (b.Size() != VECTOR_SIZE)
            throw new IllegalArgumentException("MultiplyVVV");
        final double[] result = new double[VECTOR_SIZE];

        for (int i = 0; i < VECTOR_SIZE; i++)
            result[i] = a.Get(i) * b.Get(i);

        return new Vector(result);
    }

    public static Matrix MultiplyVVM(final Vector dNet, final Vector input) {
        final int HEIGHT = dNet.Size(), WIDTH = input.Size();
        final Matrix result = new Matrix(HEIGHT, WIDTH);

        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
                result.Set(i,j, dNet.Get(i) * input.Get(j));

        return result;
    }

    public static Vector MultiplyVMV(final Vector v, final Matrix m) {
        final int WIDTH = m.GetLength()-1;
        final int HEIGHT = m.GetHeight();
        if (v.Size() != HEIGHT)
            throw new IllegalArgumentException("MultiplyVMV");

        final double[] result = new double[WIDTH];
        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < HEIGHT; j++)
                result[i] += m.Get(j, i) * v.Get(j);

        return new Vector(result);
    }

//    public static Vector MultiplyMVV(final Matrix m, final Vector v) {
//        final int NEW_VECTOR_SIZE = m.GetLength();
//        final int VECTOR_SIZE = v.Size();
//        if (m.GetHeight() != VECTOR_SIZE)
//            throw new IllegalArgumentException("MultiplyMVV");
//
//        final double[] result = new double[NEW_VECTOR_SIZE];
//        for (int i = 0; i < NEW_VECTOR_SIZE; i++)
//            for (int j = 0; j < VECTOR_SIZE; j++)
//                result[i] += m.Get(j, i) * v.Get(j);
//
//        return new Vector(result);
//    }

    private double[] values;
}
