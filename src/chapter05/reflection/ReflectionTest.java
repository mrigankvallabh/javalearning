package chapter05.reflection;

import java.lang.reflect.Modifier;
import java.util.Scanner;

final class ReflectionTest {
    static void main(String[] args) throws ClassNotFoundException {
        final String className;
        if (args.length > 0) {
            className = args[0];
        } else {
            try (var in = new Scanner(System.in)) {
                System.out.print("Enter a class name (e.g., java.util.Date): ");
                className = in.next();
            }
        }

        var clazz = Class.forName(className);
        var modifiers = Modifier.toString(clazz.getModifiers());
        if (modifiers.length() > 0) {
            System.out.print(modifiers + " ");
        }
        if (clazz.isSealed()) {
            System.out.print("sealed ");
        }
        if (clazz.isEnum()) {
            System.out.print("enum " + className);
        } else if (clazz.isRecord()) {
            System.out.print("record " + className);
        } else if (clazz.isInterface()) {
            System.out.print("interface " + className);
        } else {
            System.out.print("class " + className);
        }

        var superClass = clazz.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            System.out.print("extends " + superClass.getName());
        }

        printInterfaces(clazz);
        printPermittedSubclasses(clazz);
        System.out.println("\n{\n");
        printConstructors(clazz);
        System.out.println();
        printMethods(clazz);
        System.out.println();
        printFields(clazz);
        System.out.println("}");

    }

    private static void printFields(Class<?> clazz) {
        var fields = clazz.getDeclaredFields();
        for (var field : fields) {
            var type = field.getType();
            var name = field.getName();
            var modifiers = Modifier.toString(field.getModifiers());
            if (modifiers.length() > 0) {
                System.out.print(modifiers + " ");
            }
            System.out.println(type.getName() + " " + name + ";");
        }
    }

    private static void printMethods(Class<?> clazz) {
        var methods = clazz.getDeclaredMethods();
        for (var method : methods) {
            var returnType = method.getReturnType();
            var name = method.getName();
            System.out.print(" ");
            var modifiers = Modifier.toString(method.getModifiers());
            if (modifiers.length() > 0) {
                System.out.print(modifiers + " ");
            }
            System.out.print(returnType.getName() + " " + name + "(");
            var paramTypes = method.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print(paramTypes[i].getName());
            }
            System.out.println(");");
        }
    }

    private static void printConstructors(Class<?> clazz) {
        var constructors = clazz.getDeclaredConstructors();
        for (var ctor : constructors) {
            var name = ctor.getName();
            System.out.print(" ");
            var modifiers = Modifier.toString(ctor.getModifiers());
            if (modifiers.length() > 0) {
                System.out.print(modifiers + " ");
            }
            System.out.print(name + "(");
            var paramTypes = clazz.getTypeParameters();
            for (int i = 0; i < paramTypes.length; i++) {
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print(paramTypes[i].getName());
            }
            System.out.println(");");
        }
    }

    private static void printPermittedSubclasses(Class<?> clazz) {
        if (clazz.isSealed()) {
            var permittedSubclasses = clazz.getPermittedSubclasses();
            for (int i = 0; i < permittedSubclasses.length; i++) {
                if (i == 0) {
                    System.out.print(" permits ");
                } else {
                    System.out.print(", ");
                }
                System.out.print(permittedSubclasses[i].getName());
            }
        }
    }

    private static void printInterfaces(Class<?> clazz) {
        var interfaces = clazz.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            if (i == 0) {
                System.out.print(clazz.isInterface() ? " extends " : " implements ");
            } else {
                System.out.print(", ");
            }
            System.out.print(interfaces[i].getName());
        }
    }
}
