import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";


export function passwordValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
        const password = control.value;
        if(!password) {
            return null;
    }

        const hasUpperCase = /[A-Z]/.test(password);
        const hasLowerCase = /[a-z]/.test(password);
        const hasDigit = /\d/.test(password);
        const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);
        const hasMinLength = password.length >= 8;
    
        const isPasswordValid = hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar && hasMinLength;

        return !isPasswordValid ? { passwordStrength: true } : null;
    };
}