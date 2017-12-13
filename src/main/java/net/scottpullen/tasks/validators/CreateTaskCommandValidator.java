package net.scottpullen.tasks.validators;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import net.scottpullen.tasks.commands.CreateTaskCommand;

import java.time.LocalDateTime;

public class CreateTaskCommandValidator extends ValidatorHandler<CreateTaskCommand> implements Validator<CreateTaskCommand> {

    private final static String PRIORITY = "priority";
    private final static String PRIORITY_REQUIRED_MSG = "task.priority.required";

    private final static String DUE_DATE = "dueDate";
    private final static String DUE_DATE_IN_PAST_MSG = "task.dueDate.past";

    /**
     * Validates CreateTaskCommand
     *
     * Verifies priority is present
     * Verifies dueDate is in the future if present
     *
     * Content, Notes, and DueDate are all optional or can be added later.
     * However, content should ideally be present
     *
     * @param context
     * @param command
     * @return
     */
    @Override
    public boolean validate(ValidatorContext context, CreateTaskCommand command) {
        boolean valid = true;

        if(command.getPriority() == null) {
            context.addError(new ValidationError().setErrorMsg(PRIORITY_REQUIRED_MSG).setField(PRIORITY).setInvalidValue(command.getPriority()));
            valid = false;
        }

        if(command.getDueDate() != null) {
            if(command.getDueDate().isBefore(LocalDateTime.now())) {
                context.addError(new ValidationError().setErrorMsg(DUE_DATE_IN_PAST_MSG).setField(DUE_DATE).setInvalidValue(command.getDueDate()));
                valid = false;
            }
        }

        return valid;
    }
}
