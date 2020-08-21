package com.home.pratice.bootstrap.http.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VerifyClassField {
    //TODO:自訂義屬性(minLength,maxLength,regular)
    String regular() default "";

    int minLength() default 0;

    int maxLength() default 0;
}
