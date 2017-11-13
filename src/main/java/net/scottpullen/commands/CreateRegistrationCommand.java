package net.scottpullen.commands;

import java.io.Serializable;

public class CreateRegistrationCommand implements Serializable {
    private String email;
    private String fullName;
    private String password;
    private String passwordConfirmation;

    public String getEmail() { return this.email; }
    public String getFullName() { return this.fullName; }
    public String getPassword() { return this.password; }
    public String getPasswordConfirmation() { return this.passwordConfirmation; }

    public CreateRegistrationCommand() {
        super();
    }

    public CreateRegistrationCommand(String email, String fullName, String password, String passwordConfirmation) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }
}
