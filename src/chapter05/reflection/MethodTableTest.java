package chapter05.reflection;

import java.lang.reflect.Method;

final class MethodTableTest {
    static void main() throws ReflectiveOperationException, SecurityException {
        
        // * Use getDeclaredMethod if method is not public
        var square = MethodTableTest.class.getDeclaredMethod("square", double.class);
        var sqrt = Math.class.getMethod("sqrt", double.class);

        printTable(1, 10, 10, square);
        printTable(1, 10, 10, sqrt);
    }

    static double square(double x) {
        return x * x;
    }

    static void printTable(double from, double to, int n, Method f) throws ReflectiveOperationException {
        System.out.println(f);
        var dx = (to - from) / (n - 1);
        for (double x = 0; x <= to; x += dx) {
            var y = (Double) f.invoke(null, x);
            System.out.printf("%10.4f | %10.4f%n", x, y);
        }
    }

}
