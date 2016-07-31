package com.fernandes.google.recaptcha;

/**
 * Used to verify is the data entered by the user is valid or not.
 */
public interface VerificationService {

    boolean isValid(String captchaResponse);
}
