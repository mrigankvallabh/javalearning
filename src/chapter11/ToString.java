package chapter11;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.RECORD_COMPONENT, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@interface ToString {
    boolean includeName() default true;
}