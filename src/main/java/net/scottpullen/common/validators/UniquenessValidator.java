package net.scottpullen.common.validators;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import io.reactivex.Single;

public class UniquenessValidator extends ValidatorHandler<Single<Boolean>> implements Validator<Single<Boolean>> {

    private final String field;
    private final Object value;
    private final String messageKey;

    public UniquenessValidator(final Object value, final String field, final String messageKey) {
        this.value = value;
        this.field = field;
        this.messageKey = messageKey;
    }

    @Override
    public boolean validate(ValidatorContext context, Single<Boolean> existenceCheck) {
        boolean valid = true;

        if(existenceCheck.blockingGet()) {
            context.addError(new ValidationError().setField(field).setErrorMsg(messageKey));
            valid = false;
        }

        return valid;
    }
}
