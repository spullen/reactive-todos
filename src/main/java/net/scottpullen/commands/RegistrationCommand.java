package net.scottpullen.commands;

import java.io.Serializable;

public class RegistrationCommand implements Serializable {
    private String email;
    private String fullName;
    private String password;
    private String passwordConfirmation;

    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return this.email; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getFullName() { return this.fullName; }

    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return this.password; }

    public void setPasswordConfirmation(String passwordConfirmation) { this.passwordConfirmation = passwordConfirmation; }
    public String getPasswordConfirmation() { return this.passwordConfirmation; }

    public RegistrationCommand() {
        super();
    }

    public RegistrationCommand(String email, String fullName, String password, String passwordConfirmation) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }
}
