package net.scottpullen.tasks.validators;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import net.scottpullen.tasks.commands.UpdateTaskCommand;

public class UpdateTaskCommandValidator extends ValidatorHandler<UpdateTaskCommand> implements Validator<UpdateTaskCommand> {

    private final static String TASK_ID = "id";
    private final static String TASK_ID_REQUIRED_MSG = "task.id.required";

    private final static String PRIORITY = "priority";
    private final static String PRIORITY_REQUIRED_MSG = "task.priority.required";

    /**
     * Validates CreateTaskCommand
     *
     * Verifies taskId is present
     * Verifies priority is present
     *
     * @param context
     * @param command
     * @return
     */
    @Override
    public boolean validate(ValidatorContext context, UpdateTaskCommand command) {
        boolean valid = true;

        if(command.getId() == null) {
            context.addError(new ValidationError().setErrorMsg(TASK_ID_REQUIRED_MSG).setField(TASK_ID).setInvalidValue(command.getId()));
            valid = false;
        }

        if(command.getPriority() == null) {
            context.addError(new ValidationError().setErrorMsg(PRIORITY_REQUIRED_MSG).setField(PRIORITY).setInvalidValue(command.getPriority()));
            valid = false;
        }

        return valid;
    }
}
