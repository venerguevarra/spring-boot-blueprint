package com.starter.service.http;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class RequestValidator {
    private final static String MESSAGE_ATTRIBUTE = "message";
    private final static String MAX_ATTRIBUTE = "max";
    private final static String MIN_ATTRIBUTE = "min";

    private final ReloadableResourceBundleMessageSource messageSource;
    private Set<ValidationMessage> errorMessages;

    public RequestValidator(ReloadableResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public <T> void validate(final T objectToValidate) {
        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        final Validator validator = validatorFactory.getValidator();

        final Set<ConstraintViolation<T>> violations = validator.validate(objectToValidate);
        errorMessages = new HashSet<>();

        for (ConstraintViolation<T> violation : violations) {
            final String messageCode = violation.getMessage();
            final String property = violation.getPropertyPath().toString();
            final String propertyValue = String.valueOf(violation.getInvalidValue());

            if (getConstraintAttribute(violation, MESSAGE_ATTRIBUTE).equals(ValidationKey.LENGTH)) {
                errorMessages.add(new ValidationMessage(messageSource.getMessage(messageCode,
                                                                                 new Object[] { property,
                                                                                                getConstraintAttribute(violation, MIN_ATTRIBUTE),
                                                                                                getConstraintAttribute(violation, MAX_ATTRIBUTE) },
                                                                                 Locale.US),
                                                        property,
                                                        propertyValue));
            } else {
                errorMessages.add(new ValidationMessage(messageSource.getMessage(messageCode, new Object[] { property }, Locale.US),
                                                        property,
                                                        propertyValue));
            }
        }
    }

    private Object getConstraintAttribute(ConstraintViolation<?> violation, String key) {
        return violation.getConstraintDescriptor().getAttributes().get(key);
    }

    public Set<ValidationMessage> getErrorMessages() {
        return errorMessages;
    }

    public boolean hasErrors() {
        if(errorMessages == null || errorMessages.size() == 0) {
            return false;
        }
        return true;
    }
}
