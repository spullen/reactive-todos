package net.scottpullen.users.validators;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import net.scottpullen.users.commands.RegistrationCommand;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationCommandValidator extends ValidatorHandler<RegistrationCommand> implements Validator<RegistrationCommand> {

    private final static String EMAIL = "email";
    private final static String EMAIL_REQUIRED_MSG = "registration.email.required";
    private final static String EMAIL_FORMAT_MSG = "registration.email.format";
    private final static String EMAIL_UNIQUENESS_MSG = "registration.email.uniqueness";

    private final static Pattern EMAIL_FORMAT_PATTERN = Pattern.compile("\\A([\\w+\\-].?)+@[a-z\\d\\-]+(\\.[a-z]+)*\\.[a-z]+\\z");

    private final static String FULL_NAME = "fullName";
    private final static String FULL_NAME_REQUIRED_MSG = "registration.fullName.required";

    private final static String PASSWORD = "password";
    private final static String PASSWORD_REQUIRED_MSG = "registration.password.required";
    private final static String PASSWORD_LENGTH_MSG = "registration.password.length";

    private final static Integer PASSWORD_LENGTH = 10;

    private final static String PASSWORD_CONFIRMATION = "passwordConfirmation";
    private final static String PASSWORD_CONFIRMATION_REQUIRED_MSG = "registration.passwordConfirmation.required";
    private final static String PASSWORD_CONFIRMATION_MISMATCH_MSG = "registration.passwordConfirmation.mismatch";

    /**
     * Validates RegistrationCommand
     * - email
     *  - presence
     *  - format
     *  - uniqueness
     * - full name
     *  - presence
     * - password
     *  - presence
     *  - length > 10
     *  - number, special character
     * - passwordConfirmation
     *  - presence
     *  - confirmation with password
     *
     * @param context
     * @param command
     * @return
     */
    @Override
    public boolean validate(ValidatorContext context, RegistrationCommand command) {
        boolean valid = true;

        // Validate Email
        if(StringUtils.isNotBlank(command.getEmail())) {
            Matcher emailFormatMatcher = EMAIL_FORMAT_PATTERN.matcher(command.getEmail());
            if(emailFormatMatcher.matches()) {
                // TODO uniqueness
            } else {
                context.addError(new ValidationError().setErrorMsg(EMAIL_FORMAT_MSG).setField(EMAIL).setInvalidValue(command.getEmail()));
                valid = false;
            }
        } else {
            context.addError(new ValidationError().setErrorMsg(EMAIL_REQUIRED_MSG).setField(EMAIL).setInvalidValue(command.getEmail()));
            valid = false;
        }

        // Validate Full Name
        if(!StringUtils.isNotBlank(command.getFullName())) {
            context.addError(new ValidationError().setErrorMsg(FULL_NAME_REQUIRED_MSG).setField(FULL_NAME).setInvalidValue(command.getFullName()));
            valid = false;
        }

        // Validate Password
        if(StringUtils.isNotBlank(command.getPassword())) {
            if(command.getPassword().length() >= PASSWORD_LENGTH) {
                // TODO validate password constraints (at least one number, at least one special character)

                // Validate Password Confirmation
                if(StringUtils.isNotBlank(command.getPasswordConfirmation())) {
                    if(!StringUtils.equals(command.getPassword(), command.getPasswordConfirmation())) {
                        context.addError(new ValidationError().setErrorMsg(PASSWORD_CONFIRMATION_MISMATCH_MSG).setField(PASSWORD_CONFIRMATION));
                        valid = false;
                    }
                } else {
                    context.addError(new ValidationError().setErrorMsg(PASSWORD_CONFIRMATION_REQUIRED_MSG).setField(PASSWORD_CONFIRMATION));
                    valid = false;
                }
            } else {
                context.addError(new ValidationError().setErrorMsg(PASSWORD_LENGTH_MSG).setField(PASSWORD));
                valid = false;
            }
        } else {
            context.addError(new ValidationError().setErrorMsg(PASSWORD_REQUIRED_MSG).setField(PASSWORD));
            valid = false;
        }

        return valid;
    }
}
