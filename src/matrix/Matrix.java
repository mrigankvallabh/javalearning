package matrix;

import java.util.Arrays;
import java.util.Objects;

public final class Matrix {
    private final int nRows;
    private final int nCols;
    private final int[] elements;

    private Matrix(int nRows, int nCols, int[] elements) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.elements = elements;
    }

    /**
     * Creates a matrix from a 1D array (row-major order).
     * 
     * @throws IllegalArgumentException if {@code elements.length != nRows * nCols}.
     */
    static final Matrix from1D(int nRows, int nCols, int[] elements) {

        if (nRows * nCols != elements.length) {
            throw new IllegalArgumentException(
                    "must provide " + nRows * nCols + " elements. Provided only " + elements.length + " elements");
        }
        return new Matrix(nRows, nCols, Arrays.copyOf(elements, elements.length));
    }

    /**
     * Creates a matrix from a 2D array (row-major order).
     * 
     * @throws IllegalArgumentException if rows are jagged or empty.
     * @throws NullPointerException     if {@code elements} or any row is null.
     */
    static final Matrix from2D(int[][] elements) {
        Objects.requireNonNull(elements, "Provide elements as a 2D array.");
        if (elements.length == 0) {
            throw new IllegalArgumentException("Cannot create matrix from empty 2D array");
        }
        int rows = elements.length;
        int cols = elements[0].length;
        for (int[] row : elements) {
            if (row.length != cols) {
                throw new IllegalArgumentException("""
                        Ragged Array Input!
                        Expected: All rows to be length %d.
                        Found: A bad row %s of length %d.""".formatted(cols, Arrays.toString(row), row.length));
            }
        }
        var flattened = Arrays
                .stream(elements)
                .flatMapToInt(Arrays::stream)
                .toArray();
        return new Matrix(rows, cols, flattened);
    }

    @Override
    public String toString() {
        if (nRows == 0 || nCols == 0)
            return "[]";
        var sb = new StringBuilder();
        for (int row = 0; row < nRows; row++) {
            sb.append("[ ");
            for (int col = 0; col < nCols; col++) {
                sb.append(elements[row * nCols + col]).append(" ");
            }
            sb.append("]\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        var m1 = Matrix.from1D(3, 3, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 });
        System.out.println(m1);
        var m2 = Matrix.from2D(new int[][] { { 1, 2, 3 }, { 5, 8, 1 } });
        System.out.println(m2);
    }

}
