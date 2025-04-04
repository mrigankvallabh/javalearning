package chapter11;

import java.beans.Introspector;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

@SupportedAnnotationTypes("chapter11.ToString")
@SupportedSourceVersion(SourceVersion.RELEASE_23)
public class ToStringAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment currentRound) {
        if (annotations.size() == 0) {
            return true;
        }
        try {
            var sourceFile = processingEnv.getFiler().createSourceFile("chapter11.ToStrings");
            try (var out = new PrintWriter(sourceFile.openWriter())) {
                out.println("// Automatically generated by " + getClass().getName());
                out.println("package chapter11;");
                out.println("public class ToStrings {");
                for (var element : currentRound.getElementsAnnotatedWith(ToString.class)) {
                    if (element instanceof TypeElement te) {
                        writeToStringMethod(out, te);
                    }
                }
                out.println("    public static String toString(Object obj) {");
                out.println("        return java.util.Objects.toString(obj);");
                out.println("    }");
                out.println("}");
            }
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Kind.ERROR, e.getMessage());
        }
        return true;
    }

    private void writeToStringMethod(PrintWriter out, TypeElement te) {
        var className = te.getQualifiedName().toString();
        out.println("    public static String toString(" + className + " obj) {");
        var ann = te.getAnnotation(ToString.class);
        out.println("        var result = new StringBuilder();");
        if (ann.includeName()) {
            out.println("        result.append(\"" + className + "\");");
        }
        out.println("        result.append(\"[\");");
        boolean first = true;
        for (var c : te.getEnclosedElements()) {
            var methodName = c.getSimpleName().toString();
            ann = c.getAnnotation(ToString.class);
            if (ann != null) {
                if (first) {
                    first = false;
                } else {
                    out.println("        result.append(\",\");");
                }
                if (ann.includeName()) {
                    var fieldName = Introspector.decapitalize(methodName.replaceAll("^(get|is)", ""));
                    out.println("        result.append(\"" + fieldName + "=" + "\");");
                }
                out.println("        result.append(toString(obj." + methodName + "()));");
            }
        }
        out.println("        result.append(\"]\");");
        out.println("        return result.toString();");
        out.println("    }");
    }

}
