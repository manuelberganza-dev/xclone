package com.manuelberganza.xclone.security.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = IsExistsDbValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsExistsDb {
	String message() default "Ya existe en la Base de datos!";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}

