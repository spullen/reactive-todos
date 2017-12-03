package net.scottpullen.common.scratchvalidations;

import java.util.Optional;

abstract class BaseValidator {
    protected String attributeName;
    protected Object attribute;
    protected Optional<String> messageKey;

    public static class Builder<T extends Builder<T>> {
        protected String attributeName;
        protected Object attribute;
        protected String messageKey;

        public Builder withAttributeName(String attributeName) {
            this.attributeName = attributeName;
            return this;
        }

        public Builder withAttribute(String attribute) {
            this.attribute = attribute;
            return this;
        }

        public Builder withMessageKey(String messageKey) {
            this.messageKey = messageKey;
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    protected BaseValidator(String attributeName, Object attribute, Optional<String> messageKey) {
        this.attributeName = attributeName;
        this.attribute = attribute;
        this.messageKey = messageKey;
    }
}
