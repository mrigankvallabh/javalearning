package chapter11;

final class RuntimeAnnotations {
    static void main(String[] args) {
        var rectangle = new Rectangle(new Point(13, -5), 12, 9);
        System.out.println(ToStringInner.toString(rectangle));
    }
}

@ToString(includeName = false)
final record Point(@ToString(includeName = false) int x, @ToString(includeName = false) int y) {
}

@ToString
final record Rectangle(
        @ToString(includeName = false) Point topLeft,
        @ToString int length,
        @ToString int width) {
}

final class ToStringInner {
    static String toString(Object obj) {
        if (obj == null) {
            return "null";
        }
        var clazz = obj.getClass();
        var ts = clazz.getAnnotation(ToString.class);
        if (ts == null) {
            return obj.toString();
        }
        var result = new StringBuilder();
        if (ts.includeName()) {
            result.append(clazz.getSimpleName());
        }
        result.append("[");
        boolean first = true;
        for (var field : clazz.getDeclaredFields()) {
            ts = field.getAnnotation(ToString.class);
            if (ts != null) {
                if (first) {
                    first = false;
                } else {
                    result.append(",");
                }
                field.setAccessible(true);
                if (ts.includeName()) {
                    result.append(field.getName());
                    result.append("=");
                }
                try {
                    result.append(ToStringInner.toString(field.get(obj)));
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                }
            }
        }
        result.append("]");
        return result.toString();
    }
}
