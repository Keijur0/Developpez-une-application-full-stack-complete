package com.openclassrooms.mddapi.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation for validating passwords.
 * <p>
 * This annotation can be applied to fields and parameters to ensure that
 * the value meets the password requirements defined in
 * {@link PasswordValidator}.
 * <p>
 * The requirements include:
 * <ul>
 * <li>At least one uppercase letter</li>
 * <li>At least one lowercase letter</li>
 * <li>At least one digit</li>
 * <li>At least one special character</li>
 * </ul>
 * </p>
 * <p>
 * The default error message is "Invalid password", but this can be overridden
 * by specifying a custom message.
 * </p>
 * 
 * Usage example:
 * 
 * <pre>
 * {@literal @}ValidPassword
 * private String password;
 * </pre>
 * 
 * <p>
 * This annotation is validated by the {@link PasswordValidator} class.
 * </p>
 * 
 * @see PasswordValidator
 */
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    /**
     * The error message that is shown when the password is invalid.
     * 
     * @return the error message
     */
    String message() default "Invalid password";

    /**
     * Allows the specification of validation groups to which this constraint
     * belongs.
     * 
     * @return the validation groups
     */
    Class<?>[] groups() default {};

    /**
     * Can be used by clients of the Bean Validation API to assign custom payload
     * objects
     * to a constraint.
     * 
     * @return the custom payload
     */
    Class<? extends Payload>[] payload() default {};
}
