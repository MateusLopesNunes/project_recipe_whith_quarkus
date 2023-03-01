package org.acme.dto.request;

import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;

public class AuthRequest {
    @NotBlank(message="Email may not be blank")
    private String email;

    @NotBlank(message="Password may not be blank")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
