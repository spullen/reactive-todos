package net.scottpullen.common.scratchvalidations;

public class Validation<T> {
    private final T target;
    private final String targetName;
    private final ValidationContext context;

    public Validation(final T target, final String targetName) {
        this.target = target;
        this.targetName = targetName;
        this.context = new ValidationContext<T>(target, targetName);
    }


}
