package org.softgreen.sistcoop.ubigeo.restapi.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SWraped {

	String element() default "collection";

	String namespace() default "";

	String prefix() default "";
	
}
