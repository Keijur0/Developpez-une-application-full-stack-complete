package com.openclassrooms.mddapi.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator for the {@link ValidPassword} annotation.
 * This validator checks that a given password meets the specified security
 * requirements: at least one uppercase letter, one lowercase letter,
 * one digit, and one special character.
 */
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    /**
     * Initializes the validator. This method is a no-op for this validator.
     * 
     * @param constraintAnnotation the annotation instance for a given constraint
     */
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    /**
     * Checks if the given password is valid.
     * 
     * @param password the password to validate
     * @param context  the context in which the constraint is evaluated
     * @return {@code true} if the password is valid, {@code false} otherwise
     */
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLowerCase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecialChar = password.chars().anyMatch(ch -> "!@#$%^&*()_+[]{}|;:,.<>?".indexOf(ch) >= 0);

        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }
}
