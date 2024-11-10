package chapter06;

final class StaticInnerClassTest {
    final static void main(String[] args) {
        var values = new double[20];
        for (int i = 0; i < values.length; i++) {
            values[i] = Math.random() * 100;
        }
        var pair = ArrayAlg.minmax(values);
        System.out.println(pair.first());
        System.out.println(pair.second());
    }
}

final class ArrayAlg {
    // * can replace by static inner class but records are automatically static
    record Pair(double first, double second) { }

    static Pair minmax(double[] values) {
        var min = Double.POSITIVE_INFINITY;
        var max = Double.NEGATIVE_INFINITY;

        for (double d : values) {
            if (min > d) {
                min = d;
            }
            if (max < d) {
                max = d;
            }
        }
        return new Pair(min, max);
    }
}