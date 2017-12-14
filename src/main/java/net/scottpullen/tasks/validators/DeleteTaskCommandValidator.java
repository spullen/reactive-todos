package net.scottpullen.tasks.validators;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import net.scottpullen.tasks.commands.DeleteTaskCommand;

public class DeleteTaskCommandValidator extends ValidatorHandler<DeleteTaskCommand> implements Validator<DeleteTaskCommand> {
    private final static String TASK_ID = "id";
    private final static String TASK_ID_REQUIRED_MSG = "task.id.required";

    /**
     * Validates DeleteTaskCommand
     *
     * Verifies taskId is present
     *
     * @param context
     * @param command
     * @return
     */
    @Override
    public boolean validate(ValidatorContext context, DeleteTaskCommand command) {
        boolean valid = true;

        if(command.getId() == null) {
            context.addError(new ValidationError().setErrorMsg(TASK_ID_REQUIRED_MSG).setField(TASK_ID).setInvalidValue(command.getId()));
            valid = false;
        }

        return valid;
    }
}
