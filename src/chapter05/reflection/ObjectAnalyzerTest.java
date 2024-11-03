package chapter05.reflection;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

final class ObjectAnalyzerTest {
    static void main() throws IllegalArgumentException, IllegalAccessException {
        var squares = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++) {
            squares.add(i * i);
        }
        System.out.println(ObjectAnalyzer.toStringGeneral(squares));
    }
}

final class ObjectAnalyzer {
    private static ArrayList<Object> visited = new ArrayList<>();

    static String toStringGeneral(Object obj) throws IllegalArgumentException, IllegalAccessException {
        if (obj == null) {
            return "null";
        }
        if (visited.contains(obj)) {
            return "...";
        }
        visited.add(obj);
        var clazz = obj.getClass();
        if (clazz == String.class) {
            return (String) obj;
        }
        if (clazz.isArray()) {
            var r = clazz.getComponentType() + "[] {";
            for (int i = 0; i < Array.getLength(obj); i++) {
                if (i > 0) {
                    r += ",";
                }
                var val = Array.get(obj, i);
                if (clazz.getComponentType().isPrimitive()) {
                    r += val;
                } else {
                    r += toStringGeneral(val);
                }
            }
            return r + "}";
        }

        var r = clazz.getName();
        do {
            r += "[";
            var fields = clazz.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            for (var field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    if (!r.endsWith("[")) {
                        r += ",";
                    }
                    r += field.getName() + " = ";
                    var t = field.getType();
                    var v = field.get(obj);
                    if (t.isPrimitive()) {
                        r += v;
                    } else {
                        r += toStringGeneral(v);
                    }
                }
            }
            r += "]";
            clazz = clazz.getSuperclass();
        } while (clazz != null);
        return r;
    }
}