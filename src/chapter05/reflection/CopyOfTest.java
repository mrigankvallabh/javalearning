package chapter05.reflection;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;

final class CopyOfTest {
    static void main() {
        int[] a = { 11, 13, 17, 19 };
        a = (int[]) goodCopyOf(a, 10);
        System.out.println(Arrays.toString(a));

        String[] s = { "This", "is", "the", "to", "Hell" };
        s = (String[]) goodCopyOf(s, 10);
        System.out.println(Arrays.toString(s));

        LocalDate[] d = {
                LocalDate.of(2024, 11, 3),
                LocalDate.of(2025, 9, 24) };
        d = (LocalDate[]) goodCopyOf(d, 3);
        System.out.println(Arrays.toString(d));
    }

    static Object goodCopyOf(Object o, int newLength) {
        var clazz = o.getClass();
        if (!clazz.isArray()) {
            return null;
        }
        var componentType = clazz.getComponentType();
        var length = Array.getLength(o);
        var newArray = Array.newInstance(componentType, newLength);
        System.arraycopy(o, 0, newArray, 0, Math.min(length, newLength));
        return newArray;
    }
}
